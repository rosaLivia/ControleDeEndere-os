package com.example.discoverplaces.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.User;
import com.example.discoverplaces.R;

public class Login extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnCadastro;
    private AppDataBase db;

    private ConstraintLayout mainLayout;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCadastro);
        mainLayout = findViewById(R.id.telEndCads);

        db = AppDataBase.getInstance(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(() -> {
                        User user = db.userDAO().login(email, password);
                        runOnUiThread(() -> {
                            if (user != null) {
                                Intent intent = new Intent(Login.this, TelaCidadeEndereco.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).start();
                }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Cadastro.class);
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

    protected void onResume() {
        super.onResume();
        editTextEmail.setText(""); // Limpa o texto do campo de email ao voltar para a tela
        editTextPassword.setText(""); // Limpa o texto do campo de senha ao voltar para a tela
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
