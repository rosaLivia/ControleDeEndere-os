package com.example.discoverplaces.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.discoverplaces.DAO.CityDAO;
import com.example.discoverplaces.DAO.SeedDAO;
import com.example.discoverplaces.DAO.UserDAO;
import com.example.discoverplaces.Entity.City;
import com.example.discoverplaces.Entity.Seed;
import com.example.discoverplaces.Entity.User;

@Database(entities = {User.class, City.class, Seed.class}, version = 6, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static volatile AppDataBase INSTANCE;

    public abstract UserDAO userDAO();
    public abstract CityDAO cityDAO();
    public abstract SeedDAO seedDAO();

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "ControleDeEnderecos")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
