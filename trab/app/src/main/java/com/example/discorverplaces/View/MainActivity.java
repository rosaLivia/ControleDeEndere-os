package com.example.discorverplaces.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.discorverplaces.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirCadastro(View view) {
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }
}
