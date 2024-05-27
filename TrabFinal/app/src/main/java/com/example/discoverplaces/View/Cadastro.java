package com.example.discoverplaces.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.discoverplaces.DB.AppDataBase;
import com.example.discoverplaces.Entity.User;
import com.example.discoverplaces.R;

import java.util.List;

public class Cadastro extends AppCompatActivity {

    private AppDataBase db;
    private ListView listViewUsers;
    private ConstraintLayout mainLayout;

    private Button Voltar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        db = AppDataBase.getInstance(this);
        listViewUsers = findViewById(R.id.listViewUsers);
        mainLayout = findViewById(R.id.llCadastro);

        Voltar = findViewById(R.id.btnVoltarCad);

        loadUsers();

        listViewUsers.setOnItemClickListener((parent, view, position, id) -> {
            User selectedUser = (User) parent.getItemAtPosition(position);
            Intent intent = new Intent(Cadastro.this, EditUser.class);
            intent.putExtra("userId", selectedUser.getUserID());
            startActivity(intent);
        });


        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v); // Passa a view que foi clicada para o método hideKeyboard
                return false;
            }
        });


        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cadastro.this, Login.class);
                startActivity(intent); // Corrigido para iniciar a tela de Login
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }

    private void loadUsers() {
        new Thread(() -> {
            List<User> users = db.userDAO().getAll();
            runOnUiThread(() -> {
                ArrayAdapter<User> adapter = new ArrayAdapter<User>(Cadastro.this, android.R.layout.simple_list_item_2, android.R.id.text1, users) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = view.findViewById(android.R.id.text1);
                        TextView text2 = view.findViewById(android.R.id.text2);

                        User user = users.get(position);
                        text1.setText(user.getNome());
                        text2.setText(user.getEmail());

                        return view;
                    }
                };
                listViewUsers.setAdapter(adapter);
            });
        }).start();
    }

    public void salvar(View view) {
        EditText editxtNome = findViewById(R.id.editxtNome);
        EditText editxtEmail = findViewById(R.id.editxtEmail);
        EditText edtPassword = findViewById(R.id.edtPassword);

        String nome = editxtNome.getText().toString();
        String email = editxtEmail.getText().toString();
        String senha = edtPassword.getText().toString();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            User existingUser = db.userDAO().getUserByEmail(email);
            if (existingUser != null) {
                runOnUiThread(() -> {
                    Toast.makeText(Cadastro.this, "Email já cadastrado", Toast.LENGTH_SHORT).show();
                });
                return;
            }

            User user = new User();
            user.setNome(nome);
            user.setEmail(email);
            user.setSenha(senha);

            try {
                db.userDAO().insert(user);
                runOnUiThread(() -> {
                    Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    loadUsers();

                    // Limpar caixas de texto apenas se tudo estiver correto
                    editxtNome.setText("");
                    editxtEmail.setText("");
                    edtPassword.setText("");
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
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
