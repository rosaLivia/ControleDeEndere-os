package com.example.discorverplaces.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.discorverplaces.Entity.City;


import java.util.List;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM City")
    List<City> getAll();
    @Insert
    void insert(City city);

    @Delete
    void delete(City city);




}
