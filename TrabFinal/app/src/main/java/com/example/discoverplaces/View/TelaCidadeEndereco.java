package com.example.discoverplaces.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.discoverplaces.R;

public class TelaCidadeEndereco extends AppCompatActivity {
    private Button buttonCidade;
    private Button buttonEndereco;
    private Button btnVoltarSeedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cidade_endereco);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telEndCads), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonCidade = findViewById(R.id.buttonCidade);
        buttonEndereco = findViewById(R.id.buttonEndereco);
        btnVoltarSeedCity = findViewById(R.id.btnVoltarSeedCity);

        buttonCidade.setOnClickListener(v -> {
            Intent intent = new Intent(TelaCidadeEndereco.this, TelaCidade.class);
            startActivity(intent);
        });

        buttonEndereco.setOnClickListener(v -> {
            Intent intent = new Intent(TelaCidadeEndereco.this, TelaEndereco.class);
            startActivity(intent);
        });

        btnVoltarSeedCity.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmação de Logout")
                .setMessage("Você será deslogado. Deseja continuar?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    // Navegar para a tela de login
                    Intent intent = new Intent(TelaCidadeEndereco.this, Login.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
