package com.example.discoverplaces.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.City;
import com.example.discoverplaces.R;

import java.util.List;

public class TelaCidade extends AppCompatActivity {

    private EditText editTextCidade;
    private EditText editTextEstado;
    private Button buttonAtualiza;
    private Button buttonExcluir;
    private ListView listViewCidades;
    private ArrayAdapter<String> adapter;
    private List<City> cidadesList;
    private AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cidade);

        editTextCidade = findViewById(R.id.editTextText);
        editTextEstado = findViewById(R.id.editTextText2);
        buttonAtualiza = findViewById(R.id.btAtualiza);
        buttonExcluir = findViewById(R.id.btExcluir);
        listViewCidades = findViewById(R.id.listCidade);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewCidades.setAdapter(adapter);

        db = AppDataBase.getInstance(this);
        cidadesList = db.cityDAO().getAll();
        for (City city : cidadesList) {
            adapter.add(city.getCidade() + " - " + city.getEstado());
        }
        adapter.notifyDataSetChanged();

        buttonAtualiza.setOnClickListener(v -> {
            String cidade = editTextCidade.getText().toString();
            String estado = editTextEstado.getText().toString();

            if (!cidade.isEmpty() && !estado.isEmpty()) {
                City newCity = new City();
                newCity.setCidade(cidade);
                newCity.setEstado(estado);

                db.cityDAO().insert(newCity);
                cidadesList.add(newCity);
                adapter.add(cidade + " - " + estado);
                adapter.notifyDataSetChanged();

                editTextCidade.setText("");
                editTextEstado.setText("");
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        buttonExcluir.setOnClickListener(v -> {
            int selectedPosition = listViewCidades.getCheckedItemPosition();
            if (selectedPosition != ListView.INVALID_POSITION) {
                City cityToDelete = cidadesList.get(selectedPosition);
                db.cityDAO().delete(cityToDelete);
                cidadesList.remove(selectedPosition);
                adapter.remove(adapter.getItem(selectedPosition));
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Selecione uma cidade para excluir", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
