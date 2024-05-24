package com.example.discoverplaces.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.discoverplaces.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User WHERE userID = :id LIMIT 1")
    User getUsuarioPorID(int id);

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE email = :email AND senha = :senha LIMIT 1")
    User login(String email, String senha);

    @Update
    void update(User user);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
