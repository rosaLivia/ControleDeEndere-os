package com.example.discoverplaces.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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

    private Button locMapa;

    private ConstraintLayout mainLayout;

    private Button Voltar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enderecos_cadastrados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telEndCads), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = findViewById(R.id.spinner);
        locMapa = findViewById(R.id.LocMapa);
        mainLayout = findViewById(R.id.telEndCads);
        Voltar = findViewById(R.id.btnVoltarEndCad);


        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "discoverplaces-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        loadSeedsWithCities();

        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v); // Passa a view que foi clicada para o método hideKeyboard
                return false;
            }
        });

        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });









        locMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeedWithCity selectedSeed = (SeedWithCity) spinner.getSelectedItem();
                if (selectedSeed != null) {
                    double latitude = selectedSeed.getLatitude();
                    double longitude = selectedSeed.getLongitude();
                    if (latitude != 0 && longitude != 0) {
                        Intent intent = new Intent(EnderecosCadastrados.this, Mapa.class);
                        intent.putExtra("descricao", selectedSeed.getDescricao());
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EnderecosCadastrados.this, "Latitude ou Longitude inválida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EnderecosCadastrados.this, "Selecione um endereço", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void loadSeedsWithCities() {
        new Thread(() -> {
            List<SeedWithCity> seedsWithCity = db.seedDAO().getAllSeedsWithCity();

            runOnUiThread(() -> {
                // Crie um ArrayList de strings contendo apenas as descrições dos objetos SeedWithCity
                List<String> descriptions = new ArrayList<>();
                for (SeedWithCity seed : seedsWithCity) {
                    descriptions.add(seed.getDescricao());
                    descriptions.add(seed.getCidade());
                    descriptions.add(seed.getEstado());
                }

                // Configure o ArrayAdapter para lidar com os objetos SeedWithCity
                ArrayAdapter<SeedWithCity> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, seedsWithCity);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            });
        }).start();
    }


    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && view != null) {
            Log.d("EditCity", "Hiding keyboard from view: " + view.toString());
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            if (imm == null) {
                Log.d("EditCity", "InputMethodManager is null");
            }
            if (view == null) {
                Log.d("EditCity", "View is null");
            }
        }
    }
}
