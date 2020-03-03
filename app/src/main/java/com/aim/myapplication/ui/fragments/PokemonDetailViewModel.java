package com.aim.myapplication.ui.fragments;

import android.app.Application;

import com.aim.myapplication.models.pokemon.Pokemon;
import com.aim.myapplication.models.pokemon.PokemonLocalRepo;
import com.aim.myapplication.models.pokemon.PokemonRemoteRepo;
import com.aim.myapplication.models.pokemon.PokemonRepo;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import timber.log.Timber;

public class PokemonDetailViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private PokemonRepo pokemonRepo;
    public MutableLiveData<Pokemon> mPokemon;
    PokemonDetailViewModel(Application application){
        super(application);
        PokemonRemoteRepo remoteRepo = new PokemonRemoteRepo();
        PokemonLocalRepo localRepo = new PokemonLocalRepo(application);
        pokemonRepo = new PokemonRepo(remoteRepo,localRepo);
        mPokemon= new MutableLiveData<>();
    }

    public Observable<Pokemon> getPokemonData(String pokemonName){
        return  this.pokemonRepo.getPokemon(pokemonName).doOnError(throwable -> {
            Timber.e(throwable);
        }).doOnNext((pokemon -> {
            mPokemon.postValue(pokemon);
        }));
    }




}
