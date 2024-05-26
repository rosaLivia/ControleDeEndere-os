package com.example.discoverplaces.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.discoverplaces.Entity.City;

import java.util.List;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM City")
    List<City> getAll();

    @Insert
    void insert(City city);


    @Update
    void update(City city);

    @Delete
    void delete(City city);
    @Query("SELECT * FROM City WHERE cidadeID = :cityId")
    City getCityById(int cityId);




}
