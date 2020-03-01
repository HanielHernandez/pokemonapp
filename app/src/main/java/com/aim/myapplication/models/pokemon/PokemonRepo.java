package com.aim.myapplication.models.pokemon;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PokemonRepo implements IPokemonRepo {
    private IPokemonRemoteRepo remoteRepo;
    private IPokemonLocalRepo localRepo;

    public  PokemonRepo(PokemonRemoteRepo remoteRepo,PokemonLocalRepo localRepo){
        this.localRepo=localRepo;
        this.remoteRepo=remoteRepo;
    }

    @Override
    public Observable<Pokemon> getPokemon(int pokemonId) {
        return Observable.mergeDelayError(
                localRepo.getPokemon(pokemonId),
                remoteRepo.getPokemon(pokemonId).doOnNext(pokemon -> {
                    localRepo.savePokemon(pokemon);
                    Timber.d("Pokemon obtained from remote!");
                })
        );
    }
    public Observable<Pokemon> getPokemon(String pokemonName) {
        return Observable.mergeDelayError(
                localRepo.getPokemon(pokemonName),
                remoteRepo.getPokemon(pokemonName).doOnNext(pokemon -> {
                    localRepo.savePokemon(pokemon);
                    Timber.d("Pokemon obtained from remote!");
                })
        );
    }

    @Override
    public  Observable<PokemonListResponse> getPokemonList(int limit,int offset){
        return  remoteRepo.getPokemonList(limit,offset).subscribeOn(Schedulers.io());
    }


    @Override
    public void savePokemon(ArrayList<Pokemon> pokemonList) {
        for (Pokemon pkmn:
                pokemonList
             ) {
            localRepo.savePokemon(pkmn);
        }
    }

}
