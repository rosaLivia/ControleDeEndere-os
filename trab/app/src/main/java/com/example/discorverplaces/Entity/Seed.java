package com.example.discorverplaces.Entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"endereçoID"}, foreignKeys = @ForeignKey(entity = City.class,  parentColumns = "enderecoID",
        childColumns = "cidadeID",
        onDelete = ForeignKey.CASCADE))
public class Seed {

    private int enderecoID;
    private String descricao;

    private double latitude;

    private double longitude;

    public int getEnderecoID() {
        return enderecoID;
    }

    public void setEnderecoID(int enderecoID) {
        this.enderecoID = enderecoID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
