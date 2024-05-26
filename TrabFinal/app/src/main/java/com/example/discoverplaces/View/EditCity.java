package com.example.discoverplaces.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_city);

        editTextCidade = findViewById(R.id.editTextCidade);
        editTextEstado = findViewById(R.id.editTextEstado);
        btnSalvarEdicao = findViewById(R.id.btnSalvarEdicao);
        btnDeletarCidade = findViewById(R.id.btnDeletarCidade);

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
    }

    private void salvarEdicao() {
        String cidade = editTextCidade.getText().toString().trim();
        String estado = editTextEstado.getText().toString().trim();

        if (!cidade.isEmpty() && !estado.isEmpty()) {
            city.setCidade(cidade);
            city.setEstado(estado);
            db.cityDAO().update(city);
            Toast.makeText(this, "Cidade atualizada com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletarCidade() {
        new AlertDialog.Builder(this)
                .setTitle("Deletar Cidade")
                .setMessage("Deseja deletar a cidade " + city.getCidade() + "?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    new Thread(() -> {
                        db.cityDAO().delete(city);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Cidade deletada com sucesso", Toast.LENGTH_SHORT).show();
                            finish();  // Fecha a atividade atual e volta para a anterior
                        });
                    }).start();
                })
                .setNegativeButton("Não", null)
                .show();
    }

}
