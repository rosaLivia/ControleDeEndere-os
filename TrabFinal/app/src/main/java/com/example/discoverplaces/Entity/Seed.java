package com.example.discoverplaces.Entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = City.class, parentColumns = "cidadeID", childColumns = "CityIDFK", onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE ))
public class Seed {

    @PrimaryKey(autoGenerate = true) private int enderecoID;

    private String descricao;

    private double latitude;

    private double longitude;
    private int CityIDFK;

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


    public int getCityIDFK() {
        return CityIDFK;
    }

    public void setCityIDFK(int cityIDFK) {
        CityIDFK = cityIDFK;
    }
}
