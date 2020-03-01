package com.aim.myapplication.models.pokemon;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public interface IPokemonRepo {
    Observable<Pokemon> getPokemon(int pokemonId);
    Observable<Pokemon> getPokemon(String pokemonName);

    Observable<PokemonListResponse> getPokemonList(int limit, int offset);
    void savePokemon(ArrayList<Pokemon> pokemonList);

}
