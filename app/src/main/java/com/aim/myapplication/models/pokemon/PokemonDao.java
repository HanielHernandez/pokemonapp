package com.aim.myapplication.models.pokemon;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;

@Dao
public interface PokemonDao {

    @Query("SELECT * FROM pokemon WHERE id = :pokemonId  LIMIT 1")
    Observable<Pokemon> getPokemon(int pokemonId);

    @Query("SELECT * FROM pokemon WHERE name = :pokemonName  LIMIT 1")
    Observable<Pokemon> getPokemon(String pokemonName);

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset" )
    Observable<List<Pokemon>> getPokemonList(int limit,int offset );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePokemon(Pokemon pokemon);
}
