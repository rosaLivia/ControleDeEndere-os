package com.example.discorverplaces.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.discorverplaces.DAO.UserDAO;
import com.example.discorverplaces.DB.AppDataBase;
import com.example.discorverplaces.Entity.User;
import com.example.discorverplaces.R;

import java.net.PasswordAuthentication;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void salvar(View view){
        EditText editxtNome   = findViewById(R.id.editxtNome);
        EditText editxtEmail  = findViewById(R.id.editxtEmail);
        EditText edtPassword =  findViewById(R.id.edtPassword);

        User U = new User();

        U.setNome(editxtNome.getText().toString());
        U.setEmail(editxtEmail.getText().toString());
        U.setSenha(edtPassword.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "Data-base").build();
                    UserDAO op = db.userDAO();
                    op.insert(U);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void Listagem(View View){





    }


}
