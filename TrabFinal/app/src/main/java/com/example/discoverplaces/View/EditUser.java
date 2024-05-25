package com.example.discoverplaces.View;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.User;
import com.example.discoverplaces.R;

public class EditUser extends AppCompatActivity {

    private AppDataBase db;
    private User selectedUser;
    private EditText editTextNome, editTextEmail;
    private Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = AppDataBase.getInstance(this);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        int userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Erro ao carregar usuário", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        new Thread(() -> {
            selectedUser = db.userDAO().getUserById(userId);
            runOnUiThread(() -> {
                if (selectedUser != null) {
                    editTextNome.setText(selectedUser.getNome());
                    editTextEmail.setText(selectedUser.getEmail());
                } else {
                    Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();

        btnSave.setOnClickListener(this::saveUser);
        btnDelete.setOnClickListener(this::deleteUser);
    }

    public void saveUser(View view) {
        String nome = editTextNome.getText().toString();
        String email = editTextEmail.getText().toString();

        if (nome.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            User existingUser = db.userDAO().getUserByEmail(email);
            if (existingUser != null && existingUser.getUserID() != selectedUser.getUserID()) {
                runOnUiThread(() -> {
                    Toast.makeText(EditUser.this, "Email já cadastrado", Toast.LENGTH_SHORT).show();
                });
                return;
            }

            selectedUser.setNome(nome);
            selectedUser.setEmail(email);

            db.userDAO().update(selectedUser);
            runOnUiThread(() -> {
                Toast.makeText(this, "Usuário atualizado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }

    public void deleteUser(View view) {
        new Thread(() -> {
            db.userDAO().delete(selectedUser);
            runOnUiThread(() -> {
                Toast.makeText(this, "Usuário deletado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
