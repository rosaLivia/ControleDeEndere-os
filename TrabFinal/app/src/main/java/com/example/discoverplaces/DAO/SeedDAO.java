package com.example.discoverplaces.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.discoverplaces.Entity.Seed;
import com.example.discoverplaces.Entity.SeedWithCity;

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


    @Query("SELECT Seed.*, City.cidade, City.estado FROM Seed INNER JOIN City ON Seed.CityIDFK = City.cidadeID")
    List<SeedWithCity> getAllSeedsWithCity();
}
