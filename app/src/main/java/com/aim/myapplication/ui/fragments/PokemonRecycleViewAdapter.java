package com.aim.myapplication.ui.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aim.myapplication.models.pokemon.Pokemon;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;
import com.aim.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class PokemonRecycleViewAdapter extends RecyclerView.Adapter<PokemonRecycleViewAdapter.ViewHolder> {
    private final List<Pokemon> mValues;

    PokemonRecycleViewAdapter(List<Pokemon> items){
        this.mValues=items;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView pokemonName;
        Pokemon mPokemon;
       // final MaterialRadioButton mRadio;

        ViewHolder(View view) {
            super(view);
            mView = view;
            pokemonName = view.findViewById(R.id.pokemon_name_text_view);
        }
        void bind(Pokemon pokemon) {
            mPokemon=pokemon;
            pokemonName.setText(pokemon.getName());

        }
    }
}
