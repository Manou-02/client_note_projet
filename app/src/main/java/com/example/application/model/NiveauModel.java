package com.example.application.model;

public class NiveauModel {

    private Integer id;
    private String libelleNiveau;


    public NiveauModel( String libelleNiveau) {
        this.libelleNiveau = libelleNiveau;
    }

    public int getId() {
        return id;
    }

    public String getLibelleNiveau() {
        return libelleNiveau;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelleNiveau(String libelleNiveau) {
        this.libelleNiveau = libelleNiveau;
    }
}
