package com.example.discoverplaces.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;
import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.City;
import com.example.discoverplaces.R;

public class EditCity extends AppCompatActivity {

    private EditText editTextCidade;
    private EditText editTextEstado;
    private Button btnSalvarEdicao;
    private Button btnDeletarCidade;
    private AppDataBase db;
    private City city;

    private ConstraintLayout mainLayout;

    private Button VoltarEditCity;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_city);

        editTextCidade = findViewById(R.id.editTextCidade);
        editTextEstado = findViewById(R.id.editTextEstado);
        btnSalvarEdicao = findViewById(R.id.btnSalvarEdicao);
        btnDeletarCidade = findViewById(R.id.btnDeletarCidade);

        mainLayout = findViewById(R.id.telEndCads);

        VoltarEditCity = findViewById(R.id.btnVoltarEditCity);

        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "discoverplaces-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        int cityId = getIntent().getIntExtra("cityId", -1);
        if (cityId != -1) {
            city = db.cityDAO().getCityById(cityId);
            editTextCidade.setText(city.getCidade());
            editTextEstado.setText(city.getEstado());
        }

        btnSalvarEdicao.setOnClickListener(v -> salvarEdicao());
        btnDeletarCidade.setOnClickListener(v -> deletarCidade());


        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v); // Passa a view que foi clicada para o método hideKeyboard
                return false;
            }
        });

        VoltarEditCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Volta para a atividade anterior
            }
        });
    }

    private void salvarEdicao() {
        String cidade = editTextCidade.getText().toString().trim();
        String estado = editTextEstado.getText().toString().trim();

        if (cidade.isEmpty() || estado.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        city.setCidade(cidade);
        city.setEstado(estado);

        new UpdateCityTask().execute(city);
    }

    private void deletarCidade() {
        new AlertDialog.Builder(this)
                .setTitle("Deletar Cidade")
                .setMessage("Deseja deletar a cidade " + city.getCidade() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteCityTask().execute(city);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
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

    private class UpdateCityTask extends AsyncTask<City, Void, Void> {
        @Override
        protected Void doInBackground(City... cities) {
            db.cityDAO().update(cities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(EditCity.this, "Cidade atualizada com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private class DeleteCityTask extends AsyncTask<City, Void, Void> {
        @Override
        protected Void doInBackground(City... cities) {
            db.cityDAO().delete(cities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(EditCity.this, "Cidade deletada com sucesso", Toast.LENGTH_SHORT).show();
            finish();  // Fecha a atividade atual e volta para a anterior
        }
    }
}
