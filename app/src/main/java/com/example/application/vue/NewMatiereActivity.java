package com.example.application.vue;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.model.MatiereModel;
import com.example.application.service.EtudiantApi;
import com.example.application.service.MatiereApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewMatiereActivity extends AppCompatActivity {

    private ImageView retour;
    private EditText nomMatiereTextfield;
    private EditText coefMatiereTextfield;
    private Button ajouter;
    private MatiereApi matiereApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_matiere);

        this.retour = findViewById(R.id.retour);
        this.nomMatiereTextfield = findViewById(R.id.nomMatiereTextfield);
        this.coefMatiereTextfield = findViewById(R.id.coefMatiereTextfield);
        this.ajouter = findViewById(R.id.ajouter);

        /**
         * Action sur les bouttons clicker
         */
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMatiereActivity.this, MatiereActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /**
         * Initialisation de Retrofit
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        matiereApi = retrofit.create(MatiereApi.class);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creerMatiere();
                Intent intent = new Intent(NewMatiereActivity.this, MatiereActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void creerMatiere(){
        String nomMatiere = nomMatiereTextfield.getText().toString().trim();
        String coefMatiere = coefMatiereTextfield.getText().toString().trim();

        Integer coef =  Integer.parseInt(coefMatiere);

        MatiereModel matiere = new MatiereModel(nomMatiere, coef);
        Call<MatiereModel> call = matiereApi.creerMatiere(matiere);

        call.enqueue(new Callback<MatiereModel>() {
            @Override
            public void onResponse(Call<MatiereModel> call, Response<MatiereModel> response) {
                if (!response.isSuccessful()){
                    Log.i("BUG", "Code d'erreur" + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<MatiereModel> call, Throwable t) {
                    Log.i("BUG", t.getMessage());
            }
        });
    }
}
