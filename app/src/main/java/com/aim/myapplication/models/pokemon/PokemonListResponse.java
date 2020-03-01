package com.aim.myapplication.models.pokemon;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PokemonListResponse {
    private  int count;
    private String next;
    private  String preview;
    private ArrayList<Pokemon> results;
}
