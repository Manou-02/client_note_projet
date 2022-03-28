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
import com.example.application.adapter.EtudiantAdapter;
import com.example.application.model.EtudiantModel;
import com.example.application.service.EtudiantApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EtudiantClickActivity extends AppCompatActivity {

    private ImageView retour;
    private TextView textNumEt;
    private EditText textNomEt;
    private EditText textPrenomEt;
    private EditText textNiveauEt;
    private Button modifierButton;
    private Button supprimerButton;
    private EtudiantApi etudiantApi;
    protected String numero;
    protected String nom;
    protected String prenom;
    protected String niveau;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_click);

        this.retour =findViewById(R.id.retour);
        this.textNumEt = findViewById(R.id.textNumEt);
        this.textNomEt = findViewById(R.id.textNomEt);
        this.textPrenomEt = findViewById(R.id.textPrenomEt);
        this.textNiveauEt = findViewById(R.id.textNiveauEt);
        this.modifierButton = findViewById(R.id.modifierButton);
        this.supprimerButton = findViewById(R.id.supprimerButton);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EtudiantClickActivity.this, EtudiantActivity.class);
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
        etudiantApi = retrofit.create(EtudiantApi.class);


        /**
         * Affichage
         */
        //Recuperation de tous les valeur de l'item Selectionner
        numero = getIntent().getStringExtra("n");
        nom = getIntent().getStringExtra("nomEt");
        prenom = getIntent().getStringExtra("prenomEt");
        niveau = getIntent().getStringExtra("niveauEt");

        //Affichage de tous les valeurs sur l'EditText
        textNumEt.setText("E00"+numero);
        textNomEt.setText(nom);
        textPrenomEt.setText(prenom);
        textNiveauEt.setText(niveau);

        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEtudiant();
                String aaa = textNomEt.getText().toString().trim();

                Log.i("Debug", ""+aaa);
            }
        });
        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder popUp = new AlertDialog.Builder(EtudiantClickActivity.this);
                popUp.setMessage("Voulez-vous vraiment supprimer cette étudiant??");
                popUp.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteEtudiant();
                    }
                });
                popUp.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                popUp.show();

                //deleteEtudiant();
                Log.i("DEBUG", "Etudiant supprimé avec succès");
            }
        });

    }

    public void  updateEtudiant(){
        String nomEt = textNomEt.getText().toString().trim();
        String prenomEt = textPrenomEt.getText().toString().trim();
        String niveauEt = textNiveauEt.getText().toString().trim();

        String numEt = textNumEt.getText().toString().substring(3);
        Integer numEt2 = Integer.parseInt(numEt);

        EtudiantModel modifer = new EtudiantModel(nomEt,prenomEt,niveauEt);
        Call<EtudiantModel> call = etudiantApi.modifierEtudiant(numEt2,modifer);
        call.enqueue(new Callback<EtudiantModel>() {
            @Override
            public void onResponse(Call<EtudiantModel> call, Response<EtudiantModel> response) {
                if(!response.isSuccessful()){
                    Log.i("BUG", "Code : " + response.code());
                    return;
                }
                EtudiantModel etudiantModel = response.body();
                Log.i("debug", "Modifier avec succès");
                Intent intent = new Intent(EtudiantClickActivity.this, EtudiantActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<EtudiantModel> call, Throwable t) {
                Log.i("BUG", "" + t.getMessage());
            }
        });
    }

    public void deleteEtudiant(){
        String numEt = textNumEt.getText().toString().substring(3);
        Integer numEt2 = Integer.parseInt(numEt);
        Call<Void> call = etudiantApi.supprimerEtudiant(numEt2);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("DEBUG", "Code : " + response.code());
                Intent intent = new Intent(EtudiantClickActivity.this, EtudiantActivity.class);
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
