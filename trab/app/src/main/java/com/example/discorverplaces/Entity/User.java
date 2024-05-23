package com.example.discorverplaces.Entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"userID"})
public class User {
    private int userID;

    private String nome;

    private String Email;

    private String senha;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
