package com.aim.myapplication.models.pokemon;

import android.app.Application;

import com.aim.myapplication.persitence.PokemonDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class PokemonLocalRepo  implements  IPokemonLocalRepo{
    private final  PokemonDao pokemonDao;

    public  PokemonLocalRepo(Application app){
        PokemonDatabase pDb = PokemonDatabase.getDatabase(app);
        pokemonDao = pDb.pokemonDao();
    }


    @Override
    public Observable<Pokemon> getPokemon(int pokemonId) {
       return pokemonDao.getPokemon(pokemonId);
    }
    @Override
    public Observable<Pokemon> getPokemon(String pokemoName) {
        return pokemonDao.getPokemon(pokemoName);
    }
    @Override
    public Observable<List<Pokemon>> getPokemonList(int offset, int limit) {
        return pokemonDao.getPokemonList(offset,limit);
    }

    @Override
    public void savePokemon(Pokemon pokemon) {
        pokemonDao.savePokemon(pokemon);
    }
}

