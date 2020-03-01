package com.aim.myapplication.models.pokemon;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface PokemonService {
    @GET("pokemon/{id}")
    Observable<Pokemon> getPokemonData(@Path("id")int pokemonId);

    @GET("pokemon/{name}")
    Observable<Pokemon> getPokemonData(@Path("name")String pokemonName);

    @GET("pokemon/")
    Observable<PokemonListResponse> getPokemonList(@Query("limit")int limit, @Query("offset")int offset);
}
