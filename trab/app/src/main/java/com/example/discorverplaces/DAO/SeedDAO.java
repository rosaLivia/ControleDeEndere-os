package com.example.discorverplaces.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.discorverplaces.Entity.Seed;


@Dao
public enum SeedDAO {

    @Query("SELECT * FROM SE")
    List<Sedd> getAll();
    @Insert
    void insert(Seed seed);

    @Delete
    void delete(Seed seed);


}
