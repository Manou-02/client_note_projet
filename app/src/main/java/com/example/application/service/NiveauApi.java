package com.example.application.service;

import com.example.application.model.NiveauModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NiveauApi {

    @GET("niveau")
    Call<List<NiveauModel>> getNiveaux();


    @POST("niveau")
    Call<NiveauModel> creerNiveau(@Body NiveauModel niveauModel);


}
