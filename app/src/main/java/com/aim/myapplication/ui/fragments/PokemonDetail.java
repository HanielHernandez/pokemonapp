package com.aim.myapplication.ui.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import timber.log.Timber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aim.myapplication.R;
import com.aim.myapplication.models.pokemon.Pokemon;

public class PokemonDetail extends Fragment {

    private static Pokemon mPokemon;
    private PokemonDetailViewModel mViewModel;
    public static PokemonDetail newInstance(
            Pokemon pokemon
    ) {
        mPokemon=pokemon;
        return new PokemonDetail();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pokemon_detail_fragment, container, false);
        Timber.d(this.mPokemon.getName());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PokemonDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
