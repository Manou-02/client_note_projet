package com.example.application.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.model.NiveauModel;
import com.example.application.service.NiveauApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewNiveauActivity extends AppCompatActivity {

    private ImageView retour;
    private EditText niveauText;
    private Button ajouter;
    private NiveauApi niveauApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_niveau);

        this.retour = findViewById(R.id.retour);
        this.niveauText = findViewById(R.id.niveauText);
        this.ajouter = findViewById(R.id.ajouter);


        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewNiveauActivity.this, NiveauActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creerNiveau();
                Intent intent = new Intent(NewNiveauActivity.this, NiveauActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        niveauApi = retrofit.create(NiveauApi.class);


    }


    /**
     * Fonction pour ajouter un niveau
     */
    private void creerNiveau(){
        String contenu = niveauText.getText().toString().trim();
        NiveauModel niveau = new NiveauModel(contenu);
        Call<NiveauModel> call = niveauApi.creerNiveau(niveau);

        call.enqueue(new Callback<NiveauModel>() {
            @Override
            public void onResponse(Call<NiveauModel> call, Response<NiveauModel> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                }
                NiveauModel niveauReponse = response.body();
            }

            @Override
            public void onFailure(Call<NiveauModel> call, Throwable t) {
                Log.i("DEBUG", " " + t.getMessage());
            }
        });
    }

}
