package com.aim.myapplication.models.pokemon;

import com.aim.myapplication.networking.BaseRemote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class PokemonRemoteRepo  extends BaseRemote implements IPokemonRemoteRepo {
    @Override
    public Observable<Pokemon> getPokemon(int pokemonId) {
        return create(PokemonService.class).getPokemonData((pokemonId));
    }

    @Override
    public Observable<Pokemon> getPokemon(String pokemonName) {
        return create(PokemonService.class).getPokemonData((pokemonName));
    }

    @Override
    public Observable<PokemonListResponse> getPokemonList(int offset, int limit){
        return create(PokemonService.class).getPokemonList(offset,limit);
    }
}
