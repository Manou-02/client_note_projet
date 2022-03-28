package com.example.application.model;

import android.util.Log;
import android.widget.TextView;

import com.example.application.R;
import com.example.application.adapter.NoteAdapter;
import com.example.application.service.EtudiantApi;
import com.example.application.service.NoteApi;
import com.example.application.vue.NoteActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EtudiantModel {
    private Integer id;
    private String nomEt;
    private String  prenomEt;
    private String niveauEt;
    public Double moyenne;

    public EtudiantModel( String nomEt, String prenomEt, String niveauEt ) {
        this.nomEt = nomEt;
        this.prenomEt = prenomEt;
        this.niveauEt = niveauEt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomEt() {
        return nomEt;
    }

    public void setNomEt(String nomEt) {
        this.nomEt = nomEt;
    }

    public String getPrenomEt() {
        return prenomEt;
    }

    public void setPrenomEt(String prenomEt) {
        this.prenomEt = prenomEt;
    }

    public String getNiveauEt() {
        return niveauEt;
    }

    public void setNiveauEt(String niveauEt) {
        this.niveauEt = niveauEt;
    }

    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }

    public Double getMoyenne(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //Lister Note
        NoteApi noteApi = retrofit.create(NoteApi.class);
        Call<List<NoteModel>> call = noteApi.getBulletin(getId());

        call.enqueue(new Callback<List<NoteModel>>() {
            @Override
            public void onResponse(Call<List<NoteModel>> call, Response<List<NoteModel>> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                    return;
                }
                Double notePond = 0.0;
                Integer coefficient =0;
                Double moye=0.0;
                List<NoteModel> list = response.body();
                for (NoteModel note : list){
                    Integer coef = note.getMatiere().getCoefMat();
                    Double n = note.getNote();
                    notePond += coef * n;
                    coefficient += coef;
                }
                moyenne = notePond/coefficient;
                Log.i("DEBUGAAA", "" + moyenne);

                setMoyenne(moyenne);
            }

            @Override
            public void onFailure(Call<List<NoteModel>> call, Throwable t) {
                Log.i("BUG", " " + t.getMessage());
            }

        });


        return moyenne;
    }



}
