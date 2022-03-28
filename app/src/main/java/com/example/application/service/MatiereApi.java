package com.example.application.service;

import com.example.application.model.EtudiantModel;
import com.example.application.model.MatiereModel;
import com.example.application.vue.MatiereActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MatiereApi {

    @GET("matiere")
    Call<List<MatiereModel>> getMatieres();

    @GET("matiere/{id}")
    Call<MatiereModel> getUnMatiere(@Path("id") Integer id);

    @POST("matiere")
    Call<MatiereModel> creerMatiere(@Body MatiereModel matiereModel);

    @PATCH("matiere/{id}")
    Call<MatiereModel> modifierMatiere(@Path("id") Integer id, @Body MatiereModel matiereModel);

    @DELETE("matiere/{id}")
    Call<Void> supprimerMatiere(@Path("id") Integer id);

}
