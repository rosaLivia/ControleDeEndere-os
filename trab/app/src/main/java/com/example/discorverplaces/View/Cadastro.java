package com.example.discorverplaces.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.discorverplaces.Entity.User;
import com.example.discorverplaces.R;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void salvar(View view){
        EditText editxtNome   = findViewById(R.id.editxtNome);
        EditText editxtEmail  = findViewById(R.id.editxtEmail);
        EditText editxtSenha  = findViewById(R.id.editxtSenha;
        Button   bntSalvar    = findViewById(R.id.bntSalvar);


        User U =  new User();
        U.nome = editxtNome.getText().toString();
        U.Email






    }






}