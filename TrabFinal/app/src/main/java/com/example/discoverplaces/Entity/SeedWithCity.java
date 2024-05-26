package com.example.discoverplaces.Entity;

public class SeedWithCity {

    private int enderecoID;
    private String descricao;
    private double latitude;
    private double longitude;
    private int CityIDFK;
    private String cidade;
    private String estado;

    // Construtor vazio
    public SeedWithCity() {}

    // Construtor com parâmetros
    public SeedWithCity(int enderecoID, String descricao, double latitude, double longitude, int CityIDFK, String cidade, String estado) {
        this.enderecoID = enderecoID;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.CityIDFK = CityIDFK;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Getters e Setters
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

    public void setCityIDFK(int CityIDFK) {
        this.CityIDFK = CityIDFK;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
