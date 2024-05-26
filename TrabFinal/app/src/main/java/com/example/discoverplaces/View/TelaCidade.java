package com.example.discoverplaces.View;

import android.os.Bundle;
import android.util.Log;
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
    private Button btnSalvarCidade;
    private Button bntDeletar;

    private Button bntAtualizar;
    private ArrayAdapter<String> adapter;
    private List<City> cidadesList;
    private AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cidade);

        editTextCidade = findViewById(R.id.editTextCidade);
        editTextEstado = findViewById(R.id.editTextEstado);
        btnSalvarCidade = findViewById(R.id.btnSalvarCidade);
        bntDeletar = findViewById(R.id.bntDeletarCidade);
        bntAtualizar = findViewById(R.id.bntAtualizar);
        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "discoverplaces-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ListView listViewCidades = findViewById(R.id.listViewCidades);
        listViewCidades.setAdapter(adapter);

        loadCities();

        btnSalvarCidade.setOnClickListener(v -> salvarCidade());
        bntDeletar.setOnClickListener(v -> deletarCidade());
        bntAtualizar.setOnClickListener(v -> UpdateCidade());
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
                // Criar uma nova instância de City e configurar seus atributos
                City newCity = new City();
                newCity.setCidade(cidade);
                newCity.setEstado(estado);

                // Inserir a nova cidade no banco de dados usando o método insert da CityDAO
                db.cityDAO().insert(newCity);

                // Atualizar a lista de cidades na UI
                loadCities();

                // Limpar os campos de entrada
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


    public void deletarCidade(){

        String cidade = editTextCidade.getText().toString().trim();
        String estado = editTextEstado.getText().toString().trim();





        City CidadeDelete = db.cityDAO().findByCityAndState(cidade,estado);

        db.cityDAO().delete(CidadeDelete);

        loadCities();

    }

    public void LimparCampos(){
        editTextCidade.setText("");
        editTextEstado.setText("");


    }




    public void UpdateCidade(){ //REVISAR//PROBLEA
        String cidade = editTextCidade.getText().toString().trim();
        String estado = editTextEstado.getText().toString().trim();


        City CidadeUpdate = db.cityDAO().findByCityAndState(cidade, estado);
        LimparCampos();
        Toast.makeText(this, "Atualize os campos e clique novamente no botão atualizar", Toast.LENGTH_SHORT).show();
        ////////novos campos para atualização do elemento encontrado
        String updateCidade = editTextCidade.getText().toString().trim();
        String updadeEstado = editTextEstado.getText().toString().trim();
        bntAtualizar.setOnClickListener(v -> UpdateCidade());
        db.cityDAO().update(CidadeUpdate);


        loadCities();




    }


}
