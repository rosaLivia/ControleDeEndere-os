package com.example.discoverplaces.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.City;
import com.example.discoverplaces.Entity.Seed;
import com.example.discoverplaces.R;

import java.util.List;

public class TelaEndereco extends AppCompatActivity {

    private AppDataBase db;
    private EditText editTextDescricao, editTextLatitude, editTextLongitude;
    private Spinner spinnerCidades;
    private Button btnSalvarEndereco, btnVoltar;

    private Button btnEnderecoCadastrados;

    private Button btnCadCity;
    private ConstraintLayout mainLayout;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_endereco);

        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "discoverplaces-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        spinnerCidades = findViewById(R.id.spinnerCidades);
        btnSalvarEndereco = findViewById(R.id.btnSalvarEndereco);

        mainLayout = findViewById(R.id.telEndCads);


        btnVoltar = findViewById(R.id.btnVoltarSeed);



        btnEnderecoCadastrados = findViewById(R.id.btnSeedCad);

        loadCities();
        btnCadCity = findViewById(R.id.btnCadCity);
        btnSalvarEndereco.setOnClickListener(this::salvarEndereco);
        btnVoltar.setOnClickListener(v -> finish());

        btnEnderecoCadastrados.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEndereco.this, EnderecosCadastrados.class);
                startActivity(intent);
            }
        });

        btnCadCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEndereco.this, TelaCidade.class);
                startActivity(intent);
            }
        });
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v); // Passa a view que foi clicada para o método hideKeyboard
                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCities();
    }

    private void loadCities() {
        List<City> cities = db.cityDAO().getAll();
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, cities) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_spinner_item, parent, false);
                }
                City city = getItem(position);
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(city.getCidade() + " - " + city.getEstado());
                return convertView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                }
                City city = getItem(position);
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(city.getCidade() + " - " + city.getEstado());
                return convertView;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidades.setAdapter(adapter);
    }

    public void salvarEndereco(View view) {
        String descricao = editTextDescricao.getText().toString();
        String latitudeStr = editTextLatitude.getText().toString();
        String longitudeStr = editTextLongitude.getText().toString();

        if (descricao.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude;
        double longitude;

        try {
            latitude = Double.parseDouble(latitudeStr);
            longitude = Double.parseDouble(longitudeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Latitude e Longitude devem ser números válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        City selectedCity = (City) spinnerCidades.getSelectedItem();

        if (selectedCity == null) {
            Toast.makeText(this, "Selecione uma cidade", Toast.LENGTH_SHORT).show();
            return;
        }

        Seed endereco = new Seed();
        endereco.setDescricao(descricao);
        endereco.setLatitude(latitude);
        endereco.setLongitude(longitude);
        endereco.setCityIDFK(selectedCity.getCidadeID());

        db.seedDAO().insertll(endereco);
        Toast.makeText(this, "Endereço salvo com sucesso", Toast.LENGTH_SHORT).show();
        finish();
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
