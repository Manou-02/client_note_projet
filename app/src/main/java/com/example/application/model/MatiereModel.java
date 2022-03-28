package com.example.application.model;

public class MatiereModel {

    private Integer id;
    private String designMat;
    private Integer coefMat;

    public MatiereModel(String designMat, Integer coefMat) {
        this.designMat = designMat;
        this.coefMat = coefMat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesignMat() {
        return designMat;
    }

    public void setDesignMat(String designMat) {
        this.designMat = designMat;
    }

    public Integer getCoefMat() {
        return coefMat;
    }

    public void setCoefMat(Integer coefMat) {
        this.coefMat = coefMat;
    }
}
