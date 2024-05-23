package com.example.discorverplaces.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.discorverplaces.DAO.CityDAO;
import com.example.discorverplaces.DAO.SeedDAO;
import com.example.discorverplaces.DAO.UserDAO;
import com.example.discorverplaces.Entity.City;
import com.example.discorverplaces.Entity.Seed;
import com.example.discorverplaces.Entity.User;


@Database(entities = {User.class, City.class, Seed.class}, version = 1)
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
                                    AppDataBase.class, "AppDataBase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
