package com.example.discoverplaces.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.CitySeed;
import com.example.discoverplaces.Entity.Seed;
import com.example.discoverplaces.R;

import java.util.List;

public class EnderecosCadastrados extends AppCompatActivity {

    private AppDataBase db;

    private Spinner SpinnerEnderecos;

    private Button editarEndereco, locMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enderecos_cadastrados);
        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "discoverplaces-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        SpinnerEnderecos = findViewById(R.id.SpinnerEndereco);
        loadSeed();


    }

    public void loadSeed(){

        List<CitySeed> seeds = db.seedDAO().getCidadeEndereco();
        ArrayAdapter<CitySeed> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, seeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        SpinnerEnderecos.setAdapter(adapter);


    }








}