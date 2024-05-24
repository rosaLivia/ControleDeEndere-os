package com.example.discoverplaces.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.User;
import com.example.discoverplaces.R;

public class DeleteUser extends AppCompatActivity {

    private AppDataBase db;
    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        db = AppDataBase.getInstance(this);

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
                    TextView textViewUserDetails = findViewById(R.id.textViewUserDetails);
                    textViewUserDetails.setText("Nome: " + selectedUser.getNome() + "\nEmail: " + selectedUser.getEmail());
                } else {
                    Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this::deleteUser);
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
