package com.example.discoverplaces.Entity;


import androidx.room.Entity;

@Entity(primaryKeys = {"cidadeID"} )
public class City {
    private int cidadeID;
    private String cidade;
    private String Estado;

    public int getCidadeID() {
        return cidadeID;
    }

    public void setCidadeID(int cidadeID) {
        this.cidadeID = cidadeID;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    @Override
    public String toString() {
        return cidade + " - " + Estado;
    }
}
