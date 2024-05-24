package com.example.discoverplaces.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.discoverplaces.DAO.CityDAO;
import com.example.discoverplaces.DAO.SeedDAO;
import com.example.discoverplaces.DAO.UserDAO;
import com.example.discoverplaces.Entity.City;
import com.example.discoverplaces.Entity.Seed;
import com.example.discoverplaces.Entity.User;

@Database(entities = {User.class, City.class, Seed.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Se necessário, adicione o código SQL para a migração aqui
            // Por exemplo, para adicionar uma nova coluna à tabela User:
            // database.execSQL("ALTER TABLE User ADD COLUMN new_column_name TEXT");
        }
    };
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
                            .addMigrations(MIGRATION_1_2)
                            .fallbackToDestructiveMigration() // Isso permite a migração destrutiva se necessário
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
