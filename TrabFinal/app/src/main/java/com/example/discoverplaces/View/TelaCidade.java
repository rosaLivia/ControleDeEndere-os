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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;
import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.City;
import com.example.discoverplaces.R;
import java.util.List;

public class TelaCidade extends AppCompatActivity {

    private EditText editTextCidade;
    private EditText editTextEstado;
    private Button btnSalvarCidade;
    private ArrayAdapter<String> adapter;
    private List<City> cidadesList;
    private AppDataBase db;
    private ConstraintLayout mainLayout;
    private Button btnVoltarCity;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cidade);

        editTextCidade = findViewById(R.id.editTextCidade);
        editTextEstado = findViewById(R.id.editTextEstado);
        btnSalvarCidade = findViewById(R.id.btnSalvarCidade);

        mainLayout = findViewById(R.id.telEndCads);
        btnVoltarCity = findViewById(R.id.btnVoltarCity);
        ListView listViewCidades = findViewById(R.id.listViewCidades);

        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "discoverplaces-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewCidades.setAdapter(adapter);

        loadCities();

        btnSalvarCidade.setOnClickListener(v -> salvarCidade());

        listViewCidades.setOnItemClickListener((parent, view, position, id) -> {
            City selectedCity = cidadesList.get(position);
            Intent intent = new Intent(TelaCidade.this, EditCity.class);
            intent.putExtra("cityId", selectedCity.getCidadeID());
            startActivity(intent);
        });


        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v); // Passa a view que foi clicada para o método hideKeyboard
                return false;
            }
        });

        btnVoltarCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Volta para a atividade anterior
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCities();
    }

    private void loadCities() {
        cidadesList = db.cityDAO().getAll();
        adapter.clear();
        for (City city : cidadesList) {
            adapter.add(city.getCidade() + " - " + city.getEstado());
        }
        adapter.notifyDataSetChanged();
    }

    private void salvarCidade() {
        String cidade = editTextCidade.getText().toString().trim();
        String estado = editTextEstado.getText().toString().trim();

        if (!cidade.isEmpty() && !estado.isEmpty()) {
            try {
                City newCity = new City();
                newCity.setCidade(cidade);
                newCity.setEstado(estado);

                db.cityDAO().insert(newCity);

                loadCities();

                editTextCidade.setText("");
                editTextEstado.setText("");

                Toast.makeText(this, "Cidade salva com sucesso", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao salvar cidade", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
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
