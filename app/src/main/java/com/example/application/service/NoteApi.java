package com.example.application.service;

import com.example.application.model.NoteModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoteApi {

    @GET("note")
    Call<List<NoteModel>> getNotes();

    @GET("note/{id}")
    Call<NoteModel> getUnNote(@Path("id") Integer id);

    /**
     * Recutperer note
     * @param
     * @return
     */
    @GET("bulletin/{id}")
    Call<List<NoteModel>> getBulletin(@Path("id") Integer id);


    @POST("note")
    Call<NoteModel> creerNote(@Body NoteModel noteModel);

    @PATCH("note/{id}")
    Call<NoteModel> modifierNote(@Path("id") Integer id, @Body NoteModel noteModel);

    @DELETE("note/{id}")
    Call<Void> supprimerEtudiant(@Path("id") Integer id);

}
