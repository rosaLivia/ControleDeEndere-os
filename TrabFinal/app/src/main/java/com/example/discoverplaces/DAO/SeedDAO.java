package com.example.discoverplaces.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.discoverplaces.Entity.CitySeed;
import com.example.discoverplaces.Entity.Seed;

import java.util.List;

@Dao
public interface SeedDAO {
    @Query("SELECT * FROM seed")
    List<Seed> getAll();
    @Query("SELECT e.descricao, e.latitude, e.longitude, c.cidadeID FROM City c INNER JOIN seed e ON c.cidadeId = e.CityIDFK")
    List<CitySeed> getCidadeEndereco(); /// ponto de atenção


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
