package com.example.application.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.model.EtudiantModel;
import com.example.application.model.MatiereModel;
import com.example.application.model.NoteModel;
import com.example.application.service.EtudiantApi;
import com.example.application.service.MatiereApi;
import com.example.application.service.NoteApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewNoteActivity extends AppCompatActivity {

    private ImageView button;
    private Button ajouter;
    private EditText numEtRecherche;
    private EditText idMatRecherche;
    private ImageView putEtudiant;
    private ImageView putMatiere;
    private TextView nomEtudiant;
    private TextView prenomEtudiant;
    private EditText noteMatEtud;
    private TextView nomMatière;
    private TextView coefMatiere;
    private EtudiantApi etudiantApi;
    private MatiereApi matiereApi;
    private NoteApi noteApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        /**
         * Initialisation
         */
        //Tous les EditText
        this.button = findViewById(R.id.retour);
        this.ajouter = findViewById(R.id.ajouter);
        this.numEtRecherche = findViewById(R.id.numEtRecherche);
        this.idMatRecherche = findViewById(R.id.idMatRecherche);
        this.noteMatEtud = findViewById(R.id.noteMatEtud);

        //Tous les images
        this.putEtudiant = findViewById(R.id.putEtudiant);
        this.putMatiere = findViewById(R.id.putMatiere);

        //Tous les TextView
        this.nomEtudiant = findViewById(R.id.nomEtudiant);
        this.prenomEtudiant = findViewById(R.id.prenomEtudiant);
        this.nomMatière = findViewById(R.id.nomMatiere);
        this.coefMatiere = findViewById(R.id.coefMatiere);

        /**
         * Initialisation de Retrofit
         */
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        etudiantApi = retrofit.create(EtudiantApi.class);
        matiereApi = retrofit.create(MatiereApi.class);
        noteApi = retrofit.create(NoteApi.class);



        /**
         * Attribution d'action
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewNoteActivity.this, NoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        putEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Fonction pour recuperer un étudiant
                 */
                getEtudiant();
            }
        });
        putMatiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMatiere();
            }
        });
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numEtRecherche.getText().length() < 4 ){

                    numEtRecherche.setError("Le numero n'existe pas");
                    numEtRecherche.requestFocus();
                    return;
                }
                newNote();
                Intent intent = new Intent(NewNoteActivity.this, NoteActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }

    private void getEtudiant(){
        if (numEtRecherche.getText().length() < 4 ){

            numEtRecherche.setError("Le numero n'existe pas");
            numEtRecherche.requestFocus();
            return;
        }
        String numEt = numEtRecherche.getText().toString().substring(3);
        Integer numEt2 = Integer.parseInt(numEt);
        Call<EtudiantModel> call = etudiantApi.getUnEtudiant(numEt2);
        call.enqueue(new Callback<EtudiantModel>() {
            @Override
            public void onResponse(Call<EtudiantModel> call, Response<EtudiantModel> response) {
                if(!response.isSuccessful()){
                    Log.i("debug", "Code " + response.code());
                    return;
                }
                EtudiantModel etud = response.body();
                if (etud.getId().toString().length() < 0){
                    numEtRecherche.setError("Le numero n'existe pas");
                    return;
                }
                nomEtudiant.setText(etud.getNomEt());
                prenomEtudiant.setText(etud.getPrenomEt());
                Log.i("DEBUG", ""+ etud.getNomEt());
            }

            @Override
            public void onFailure(Call<EtudiantModel> call, Throwable t) {
                Log.i("debug", "" + t.getMessage());
            }
        });
    }

    private void getMatiere(){

        String numMat = idMatRecherche.getText().toString();
        Integer numMat2 = Integer.parseInt(numMat);
        Call<MatiereModel> call = matiereApi.getUnMatiere(numMat2);
        call.enqueue(new Callback<MatiereModel>() {
            @Override
            public void onResponse(Call<MatiereModel> call, Response<MatiereModel> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                    return;
                }
                MatiereModel matiere = response.body();
                nomMatière.setText(matiere.getDesignMat());
                coefMatiere.setText(""+matiere.getCoefMat());
            }

            @Override
            public void onFailure(Call<MatiereModel> call, Throwable t) {
                Log.i("DEBUG", "" + t.getMessage());
            }
        });

    }

    private void newNote(){

        //Recuperer l'identifiant de l'étudiant
        String numEt = numEtRecherche.getText().toString().substring(3);
        Integer numEt2 = Integer.parseInt(numEt);
        //Recuperer l'identifiant du matiere
        String numMat = idMatRecherche.getText().toString();
        Integer numMat2 = Integer.parseInt(numMat);
        //Recuperer le note dans le champ
        String note = noteMatEtud.getText().toString();
        Double note2 = Double.parseDouble(note);

        NoteModel newNote = new NoteModel(numEt2, numMat2, note2);
        Call<NoteModel> call = noteApi.creerNote(newNote);
        call.enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
                Log.i("DEBUG", "" + t.getMessage());
            }
        });
    }
}
