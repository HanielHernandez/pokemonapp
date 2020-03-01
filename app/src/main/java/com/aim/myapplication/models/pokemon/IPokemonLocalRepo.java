package com.aim.myapplication.models.pokemon;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public interface IPokemonLocalRepo {

    Observable<Pokemon> getPokemon(int pokemonId);
    Observable<Pokemon> getPokemon(String pokemonName);

    Observable<List<Pokemon>> getPokemonList(int offset, int limit);
    void savePokemon(Pokemon pokemon);
}
