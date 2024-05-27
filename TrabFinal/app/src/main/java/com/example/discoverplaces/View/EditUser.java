package com.example.discoverplaces.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.User;
import com.example.discoverplaces.R;

public class EditUser extends AppCompatActivity {

    private AppDataBase db;
    private User selectedUser;
    private EditText editTextNome, editTextEmail;
    private Button btnSave, btnDelete;
    private ConstraintLayout mainLayout;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = AppDataBase.getInstance(this);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        mainLayout = findViewById(R.id.TelEditUser);

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

        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v); // Passa a view que foi clicada para o método hideKeyboard
                return false;
            }
        });



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
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Exclusão")
                .setMessage("Você tem certeza que deseja deletar este Usuario?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    new Thread(() -> {
                        db.userDAO().delete(selectedUser);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Cidade deletada com sucesso", Toast.LENGTH_SHORT).show();
                            finish(); // Optionally, close the activity
                        });
                    }).start();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
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
}
