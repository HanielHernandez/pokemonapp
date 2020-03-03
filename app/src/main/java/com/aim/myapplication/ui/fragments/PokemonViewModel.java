package com.aim.myapplication.ui.fragments;

import android.app.Application;

import com.aim.myapplication.models.pokemon.Pokemon;
import com.aim.myapplication.models.pokemon.PokemonListResponse;
import com.aim.myapplication.models.pokemon.PokemonLocalRepo;
import com.aim.myapplication.models.pokemon.PokemonRemoteRepo;
import com.aim.myapplication.models.pokemon.PokemonRepo;

import java.sql.Time;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PokemonViewModel extends AndroidViewModel {
    int limit= 30;
    public MutableLiveData<ArrayList<Pokemon>> pokemonList;
    public enum ViewState {
        FETCHING_POKEMON("FETCHING_POKEMON"),
        FETCHING_POKEMON_LIST("FETCHING_POKEMON_LIST"),
        ERROR_FETCHING_POKEMON_LIST("ERROR_FETCHING_POKEMON_LIST"),
        POKEMON_LIST_OBTAINED("POKEMON_LIST_OBTAINED");

        ;


        private final String status;

        ViewState(String status) {
            this.status = status;
        }

        @NonNull
        @Override
        public String toString() {
            return this.status;
        }
    }
    private final PokemonRepo pokemonRepo;
    private CompositeDisposable disposables;
    public MutableLiveData<Pokemon> currentPokemon = new MutableLiveData<>();

    private MutableLiveData<ViewState> currentViewStatus = new MutableLiveData<>();

    public PokemonViewModel(Application application) {
        super(application);
        PokemonRemoteRepo remoteRepo = new PokemonRemoteRepo();
        PokemonLocalRepo localRepo = new PokemonLocalRepo(application);
        pokemonRepo = new PokemonRepo(remoteRepo,localRepo);
        disposables = new CompositeDisposable();
        pokemonList = new MutableLiveData<>();
        pokemonList.setValue(new ArrayList<>());
        disposables.add(this.getPokemonList().observeOn(Schedulers.io()).subscribe(this::pokemonUpdated, this::errorObtainingPokemon));
    }

    private void errorObtainingPokemon(Throwable throwable) {
    }
    public void updateCurrentPokemon(Pokemon pokemon){

    }

    private void pokemonUpdated(PokemonListResponse pokemonListResponse) {
        //ArrayList<Pokemon> pokemonList=pokemonListResponse.getResults();
        //this.pokemonList.postValue(pokemonList);
    }

    public Observable<Pokemon> fetchPokemon(int pokemonId){
        currentViewStatus.postValue(ViewState.FETCHING_POKEMON);
        return pokemonRepo.getPokemon(pokemonId).observeOn(Schedulers.io()).doOnNext(pokemon -> currentPokemon.postValue(pokemon));
    }
    public Observable<Pokemon> fetchPokemon(String pokemonName){
        currentViewStatus.postValue(ViewState.FETCHING_POKEMON);
        return pokemonRepo.getPokemon(pokemonName).observeOn(Schedulers.io()).doOnNext(pokemon -> currentPokemon.postValue(pokemon));
    }
    public void loadMore(){
        disposables.add(this.getPokemonList().observeOn(Schedulers.io()).subscribe(this::pokemonUpdated, this::errorObtainingPokemon));
    }
   private Observable<PokemonListResponse> getPokemonList(){
        currentViewStatus.postValue(ViewState.FETCHING_POKEMON_LIST);
        int currentTotal=pokemonList.getValue().size();
        Timber.d("OFFSET: "+currentTotal+" LIMIT: "+limit);
        return pokemonRepo.getPokemonList(limit, pokemonList.getValue().size()).doOnError(throwable -> {
            Timber.e(throwable);
            currentViewStatus.postValue(ViewState.ERROR_FETCHING_POKEMON_LIST);
        }).doOnNext((pokemonListResponse -> {
             ArrayList<Pokemon> newPokemons=  pokemonListResponse.getResults();
            pokemonRepo.savePokemon(newPokemons);
            ArrayList<Pokemon> currentList = pokemonList.getValue();
            currentList.addAll(newPokemons);
            pokemonList.postValue(currentList);
            currentViewStatus.postValue(ViewState.POKEMON_LIST_OBTAINED);

        }));
    }

}
