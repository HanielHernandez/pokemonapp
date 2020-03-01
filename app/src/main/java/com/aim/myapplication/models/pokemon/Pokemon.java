package com.aim.myapplication.models.pokemon;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Data;

@Data
@Entity
public class Pokemon {
    @PrimaryKey
    private int id;
    private String name;
    private Float height;
    private Float weight;
}
