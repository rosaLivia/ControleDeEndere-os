package com.example.discoverplaces.View;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        btnVoltar = findViewById(R.id.btnVoltar);

        loadCities();

        btnSalvarEndereco.setOnClickListener(this::salvarEndereco);
        btnVoltar.setOnClickListener(v -> finish());
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
}
