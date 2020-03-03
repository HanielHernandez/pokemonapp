package com.aim.myapplication.ui.fragments;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import com.aim.myapplication.utils.EndlessRecyclerViewScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private OnPokemonSelected mListener;

    private MainViewModel mViewModel;
    private PokemonViewModel pokemonViewModel;
    private ProgressBar pb;
    private RecyclerView rvPokemon;
    private LinearLayoutManager mLayoutManager;
    int yPosition;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int visibleThreshold = 5;
    private CompositeDisposable disposables = new CompositeDisposable();
    private EndlessRecyclerViewScrollListener scrollListener;
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
        pb= view.findViewById(R.id.pokemon_pb);
        mLayoutManager= new LinearLayoutManager(requireContext());
        rvPokemon=  view.findViewById(R.id.pokermon_recycler_view);
        rvPokemon.setLayoutManager(mLayoutManager);
        mListener= new OnPokemonSelected() {
            @Override
            public void onDefaultPokemon(Pokemon pokemon) {
                NavController navController =   Navigation.findNavController(view);
                navController.
            }

            @Override
            public void onPokemonSelectionDone() {

            }
        };
        rvPokemon.setAdapter(new PokemonRecycleViewAdapter(new ArrayList<>(),mListener));
        initScrollListener();
        this.pokemonName= view.findViewById(R.id.pokemon_name_text_view);
        yPosition=0;

        //pokemonViewModel.fetchPokemon(21).observeOn(AndroidSchedulers.mainThread()).subscribe(this::setPokemonData);
        return view;


    }

    private void initScrollListener() {
        this.rvPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
                /*if(dy>0){
                    if(!recyclerView.canScrollVertically(1)){
                        Timber.tag("SCROLL ").d("LLEGO AL FONO");
                        pokemonViewModel.loadMore();


                    }
                }*/
                if((totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    Timber.tag("SCROLL ").d("LLEGO AL FONO");
                    // Do something
                }
            }
        });
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
        pokemonViewModel.pokemonList.observe(getViewLifecycleOwner(),this::pokemonObtained);

    }

    private void pokemonObtained(ArrayList<Pokemon> pokemons) {
        this.yPosition=rvPokemon.getScrollY();
        rvPokemon.setAdapter(new PokemonRecycleViewAdapter(pokemons,mListener));
        rvPokemon.scrollToPosition(yPosition);
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

    public  interface OnPokemonSelected{
        void onDefaultPokemon(Pokemon pokemon);

        void onPokemonSelectionDone();
    }

}
