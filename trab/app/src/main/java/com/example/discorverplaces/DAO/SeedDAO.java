package com.example.discorverplaces.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.discorverplaces.Entity.Seed;

import java.util.List;

@Dao
public interface SeedDAO {
    @Query("SELECT * FROM seed")
    List<Seed> getAll();

    @Query("SELECT * FROM seed WHERE enderecoID = :id")
    Seed getById(int id);

    @Insert
    void insertll(Seed seed);

    @Update
    void update(Seed seed);

    @Delete
    void delete(Seed seed);

    @Query("DELETE FROM seed WHERE enderecoID = :id")
    void deleteById(int id);

    @Query("DELETE FROM seed")
    void deleteAll();
}
