package com.example.application.vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

public class MatiereClickActivity extends AppCompatActivity {

    private TextView textNumMat;
    private EditText textDesignMat;
    private EditText textCoefMat;

    private ImageView retour;
    private Button modifierButton;
    private Button supprimerButton;

    protected String numero;
    protected String designation;
    protected String coefficient;
    protected MatiereApi matiereApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matiere_click);

        /**
         * Initialisation
         */
        this.textNumMat = findViewById(R.id.textNumMat);
        this.textDesignMat = findViewById(R.id.textDesignMat);
        this.textCoefMat = findViewById(R.id.textCoefMat);
        this.retour = findViewById(R.id.retour);
        this.modifierButton = findViewById(R.id.modifierButton);
        this.supprimerButton = findViewById(R.id.supprimerButton);



        /**
         * Récuperation de tous les valeurs de l'item à afficher
         */
        numero = getIntent().getStringExtra("numMat");
        designation = getIntent().getStringExtra("nomMat");
        coefficient = getIntent().getStringExtra("coefMat");

        /**
         * Affichage de tous ces valeurs
         */
        textNumMat.setText(numero);
        textDesignMat.setText(designation);
        textCoefMat.setText(coefficient);


        /**
         * Initialisation de Retrofit
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        matiereApi = retrofit.create(MatiereApi.class);

        /**
         * Attribution d'action à tous les boutons
         */
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatiereClickActivity.this, MatiereActivity.class);
                startActivity(intent);
                finish();
            }
        });

        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aaa = textDesignMat.getText().toString();
                updateMatiere();
            }
        });
        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder popUp = new AlertDialog.Builder(MatiereClickActivity.this);
                popUp.setMessage("Voulez-vous vraiment supprimer cette matière??");
                popUp.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMatiere();
                    }
                });
                popUp.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                popUp.show();

                Log.i("DEBUG", "Matière supprimé avec succès");
            }
        });

    }
    public void  updateMatiere(){
        String designMat = textDesignMat.getText().toString().trim();
        String coefMat = textCoefMat.getText().toString().trim();
        String numMat = textNumMat.getText().toString().trim();

        Integer coefMat2 = Integer.parseInt(coefMat);
        Integer numMat2 = Integer.parseInt(numMat);

        MatiereModel modifier = new MatiereModel(designMat,coefMat2);
        Call<MatiereModel> call = matiereApi.modifierMatiere(numMat2, modifier);
        call.enqueue(new Callback<MatiereModel>() {
            @Override
            public void onResponse(Call<MatiereModel> call, Response<MatiereModel> response) {
                if(!response.isSuccessful()){
                    Log.i("BUG", "Code : " + response.code());
                    return;
                }
                MatiereModel  matiereModel = response.body();
                Intent intent = new Intent(MatiereClickActivity.this, MatiereActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<MatiereModel> call, Throwable t) {
                Log.i("BUG", ""+t.getMessage());
            }
        });

    }

    public void deleteMatiere(){
        String numMat = textNumMat.getText().toString().trim();
        Integer numMat2 = Integer.parseInt(numMat);
        Call<Void> call = matiereApi.supprimerMatiere(numMat2);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("DEBUG", response.code() + "");
                Intent intent =  new Intent(MatiereClickActivity.this, MatiereActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("BUG", "" + t.getMessage());
            }
        });
    }
}
