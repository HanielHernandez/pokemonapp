package com.aim.myapplication.ui.fragments;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aim.myapplication.R;
import com.aim.myapplication.models.pokemon.Pokemon;
import com.aim.myapplication.models.pokemon.PokemonListResponse;
import com.aim.myapplication.ui.MainViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private PokemonViewModel pokemonViewModel;
    private ProgressBar pb;
    private RecyclerView rvPokemon;
    private CompositeDisposable disposables = new CompositeDisposable();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private TextView pokemonName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_fragment, container, false);
        // TODO: Use the ViewModel
        rvPokemon=  view.findViewById(R.id.pokermon_recycler_view);
        pb= view.findViewById(R.id.pokemon_pb);
        rvPokemon.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPokemon.setAdapter(new PokemonRecycleViewAdapter(new ArrayList<>()));
        this.pokemonName= view.findViewById(R.id.pokemon_name_text_view);

        //pokemonViewModel.fetchPokemon(21).observeOn(AndroidSchedulers.mainThread()).subscribe(this::setPokemonData);
        return view;


    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
        pb.setVisibility(View.VISIBLE);
        pokemonViewModel= new ViewModelProvider(requireActivity()).get(PokemonViewModel.class);
        disposables.add(pokemonViewModel.getPokemonList(20,0).observeOn(AndroidSchedulers.mainThread()).subscribe(this::pokemonObtained));


    }

    private void pokemonObtained(PokemonListResponse pokemonListResponse) {
        Timber.d("TOTAL POKEMONS" +pokemonListResponse.getCount() + "");
        ArrayList<Pokemon> pokemon= pokemonListResponse.getResults();
        String list=  pokemon.toString();
        Timber.d("POKEMONS LIST" +list + "");
        rvPokemon.setAdapter(new PokemonRecycleViewAdapter(pokemon));
        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    void setPokemonData(Pokemon pokemon){
        String name=pokemon.getName();
        this.pokemonName.setText(name);
        Timber.tag("PKMN").d("pokemon name is %s",pokemon.getName());
    }

}
