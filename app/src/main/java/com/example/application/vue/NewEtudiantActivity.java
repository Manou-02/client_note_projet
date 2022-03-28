package com.example.application.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.adapter.NiveauAdapter;
import com.example.application.model.EtudiantModel;
import com.example.application.model.NiveauModel;
import com.example.application.service.EtudiantApi;
import com.example.application.service.NiveauApi;

import java.sql.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewEtudiantActivity extends AppCompatActivity {

    private ImageView retour;
    private Spinner listNiveau;
    private EtudiantApi etudiantApi;
    private EditText numTextfield;
    private EditText nomTextField;
    private EditText prenomTextField;
    private Spinner niveauList;
    private Button ajouter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_etudiant);

        this.retour = findViewById(R.id.retour);
        this.listNiveau = findViewById(R.id.niveauList);
        this.ajouter = findViewById(R.id.ajouter);
        /**
         * Formulaire
         */
        this.nomTextField = findViewById(R.id.nomTextfield);
        this.prenomTextField = findViewById(R.id.preNomTextfield);
        this.niveauList = findViewById(R.id.niveauList);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewEtudiantActivity.this, EtudiantActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /**
         * Affichage de la liste deroulante
         */

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewEtudiantActivity.this,
                R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.niveau));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        listNiveau.setAdapter(myAdapter);

        /**
         * Initialisation de Retrofit
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        etudiantApi = retrofit.create(EtudiantApi.class);

        /**
         * Click sur les bouttons
         */
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nouvelEtudiant();
                Intent intent = new Intent(NewEtudiantActivity.this, EtudiantActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void nouvelEtudiant(){
        String nomEtudiant = nomTextField.getText().toString().trim();
        String prenomEtudiant = prenomTextField.getText().toString().trim();
        String niveauEtudiant = (String) niveauList.getSelectedItem();

        EtudiantModel etudiant = new EtudiantModel( nomEtudiant, prenomEtudiant, niveauEtudiant);
        Call<EtudiantModel> call = etudiantApi.creerEtudiant(etudiant);

        call.enqueue(new Callback<EtudiantModel>() {
            @Override
            public void onResponse(Call<EtudiantModel> call, Response<EtudiantModel> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EtudiantModel> call, Throwable t) {
                Log.i("DEBUG", " " + t.getMessage());
            }
        });
    }
}
