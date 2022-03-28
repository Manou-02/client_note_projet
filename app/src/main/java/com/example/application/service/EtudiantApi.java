package com.example.application.service;

import com.example.application.model.EtudiantModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EtudiantApi {

    @GET("etudiant")
    Call<List<EtudiantModel>> getEtudiants();

    @GET("etudiant/{id}")
    Call<EtudiantModel> getUnEtudiant(@Path("id") Integer id);

    @POST("etudiant")
    Call<EtudiantModel> creerEtudiant(@Body EtudiantModel etudiantModel);

    @GET("moyenne/{id}")
    Call<EtudiantModel> getMoyenne(@Path("id") Integer id);

    @PATCH("etudiant/{id}")
    Call<EtudiantModel> modifierEtudiant(@Path("id") Integer id, @Body EtudiantModel etudiantModel);

    @DELETE("etudiant/{id}")
    Call<Void> supprimerEtudiant(@Path("id") Integer id);
}
