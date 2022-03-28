package com.example.application.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoteModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("numEt")
    @Expose
    private int numEt;
    @SerializedName("idMat")
    @Expose
    private int idMat;
    @SerializedName("note")
    @Expose
    private double note;


    private EtudiantModel etudiant;
    private MatiereModel matiere;

    public NoteModel(int numEt, int idMat, double note) {
        this.numEt = numEt;
        this.idMat = idMat;
        this.note = note;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumEt() {
        return numEt;
    }

    public void setNumEt(int numEt) {
        this.numEt = numEt;
    }

    public int getIdMat() {
        return idMat;
    }

    public void setIdMat(int idMat) {
        this.idMat = idMat;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public EtudiantModel getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(EtudiantModel etudiant) {
        this.etudiant = etudiant;
    }

    public MatiereModel getMatiere() {
        return matiere;
    }

    public void setMatiere(MatiereModel matiere) {
        this.matiere = matiere;
    }
}
