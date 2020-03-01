package com.aim.myapplication.persitence;

import android.content.Context;

import com.aim.myapplication.models.pokemon.Pokemon;
import com.aim.myapplication.models.pokemon.PokemonDao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database( entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class      PokemonDatabase extends RoomDatabase {
    private static volatile PokemonDatabase INSTANCE;

    public  static  PokemonDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PokemonDatabase.class, "pokemon.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public  abstract PokemonDao pokemonDao();

}
