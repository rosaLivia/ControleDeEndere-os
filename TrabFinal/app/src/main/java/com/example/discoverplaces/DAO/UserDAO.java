package com.example.discoverplaces.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.discoverplaces.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getAll();
    @Insert
    void insert(User user);

    @Delete
    void delete(User user);



}
