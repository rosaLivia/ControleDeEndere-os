package com.example.discoverplaces.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.SeedWithCity;
import com.example.discoverplaces.R;

import java.util.ArrayList;
import java.util.List;

public class EnderecosCadastrados extends AppCompatActivity {

    private AppDataBase db;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enderecos_cadastrados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = findViewById(R.id.spinner);

        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "discoverplaces-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        loadSeedsWithCities();
    }

    private void loadSeedsWithCities() {
        new Thread(() -> {
            List<SeedWithCity> seedsWithCity = db.seedDAO().getAllSeedsWithCity();
            List<String> seedCityList = new ArrayList<>();

            for (SeedWithCity seedWithCity : seedsWithCity) {
                seedCityList.add(seedWithCity.getDescricao() + " - " + seedWithCity.getCidade() + " (" + seedWithCity.getEstado() + ")");
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, seedCityList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            });
        }).start();
    }
}
