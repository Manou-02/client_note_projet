package com.example.application.vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
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
import com.example.application.model.EtudiantModel;
import com.example.application.model.MatiereModel;
import com.example.application.model.NoteModel;
import com.example.application.service.EtudiantApi;
import com.example.application.service.MatiereApi;
import com.example.application.service.NoteApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoteClickActivity  extends AppCompatActivity {

    /**
     * Fields
     */

    private ImageView retour;
    //Etudiant
    private EditText numEtRecherche;
    private ImageView putEtudiant;
    private TextView nomEtudiant;
    private TextView prenomEtudiant;

    //Matiere
    private EditText idMatRecherche;
    private ImageView putMatiere;
    private TextView nomMatiere;
    private TextView coefMatiere;

    //Note
    private EditText noteMatEt;

    //Button
    private Button modifierButton;
    private Button supprimerButton;

    /**
     * Les variables
     */
    //etudiant
    private String numEt;
    private String nomEt;
    private String prenomEt;

    //matiere
    private String numMat;
    private String designMat;
    private String coefMat;

    //note
    private String idNote;
    private String note;

    //api
    private EtudiantApi etudiantApi;
    private MatiereApi matiereApi;
    private NoteApi noteApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_click);

        /**
         * Initialisation
         */
        this.retour = findViewById(R.id.retour);

        //Etudiant
        this.numEtRecherche = findViewById(R.id.numEtRecherche);
        this.putEtudiant = findViewById(R.id.putEtudiant);
        this.nomEtudiant = findViewById(R.id.nomEtudiant);
        this.prenomEtudiant = findViewById(R.id.prenomEtudiant);

        //Matiere
        this.idMatRecherche = findViewById(R.id.idMatRecherche);
        this.putMatiere = findViewById(R.id.putMatiere);
        this.nomMatiere = findViewById(R.id.nomMatiere);
        this.coefMatiere = findViewById(R.id.coefMatiere);

        //Note
        this.noteMatEt = findViewById(R.id.noteMatEtud);

        //Bouton
        this.modifierButton = findViewById(R.id.modifierButton);
        this.supprimerButton = findViewById(R.id.supprimerButton);

        /**
         * Recuperation de tous les valeurs de l'item selectionner
         */
        //Etudiant
        numEt = getIntent().getStringExtra("numEt");
        nomEt = getIntent().getStringExtra("nomEt");
        prenomEt = getIntent().getStringExtra("prenomEt");
        //Matiere
        numMat = getIntent().getStringExtra("numMat");
        designMat = getIntent().getStringExtra("designMat");
        coefMat = getIntent().getStringExtra("coefMatiere");
        //Note
        idNote = getIntent().getStringExtra("idNote");
        note = getIntent().getStringExtra("note");

        /**
         * Initialisation de l'affichage
         */
        //Etudiant
        numEtRecherche.setText("E00"+numEt);
        nomEtudiant.setText(nomEt);
        prenomEtudiant.setText(prenomEt);
        //Matiere
        idMatRecherche.setText(numMat);
        nomMatiere.setText(designMat);
        coefMatiere.setText(coefMat);
        //Note
        noteMatEt.setText(note);

        /**
         * Initialisation de Retrofit
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        etudiantApi = retrofit.create(EtudiantApi.class);
        matiereApi = retrofit.create(MatiereApi.class);
        noteApi = retrofit.create(NoteApi.class);

        /**
         * Attribution d'action à tous les clickables
         */
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteClickActivity.this, NoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Recuperer l'etudiant
        putEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEtudiant();
            }
        });
        //Recuperer la matiere
        putMatiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMatiere();
            }
        });
        //Appui sur le bouton modifier
        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });

        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder popUp = new AlertDialog.Builder(NoteClickActivity.this);
                popUp.setMessage("Voulez-vous vraiment supprimer cette note??");
                popUp.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote();
                    }
                });
                popUp.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                popUp.show();

                Log.i("DEBUG", "Matière supprimé avec succès");
            }
        });

    }

    private void getEtudiant(){
        if(numEtRecherche.getText().length() < 4 ){
            numEtRecherche.setError("Le numero n'existe pas");
            numEtRecherche.requestFocus();
            return;
        }
        String numEtudiant = numEtRecherche.getText().toString().substring(3);
        Integer numEtudiant2 = Integer.parseInt(numEtudiant);
        Call<EtudiantModel> call = etudiantApi.getUnEtudiant(numEtudiant2);
        call.enqueue(new Callback<EtudiantModel>() {
            @Override
            public void onResponse(Call<EtudiantModel> call, Response<EtudiantModel> response) {
                if(!response.isSuccessful()){
                    Log.i("BUG", "Code : " + response.code());
                    return;
                }
                EtudiantModel etud = response.body();
                nomEtudiant.setText(etud.getNomEt());
                prenomEtudiant.setText(etud.getPrenomEt());
            }

            @Override
            public void onFailure(Call<EtudiantModel> call, Throwable t) {
                Log.i("BUG", "" + t.getMessage());
            }
        });
    }

    private  void getMatiere(){
        String numMatiere = idMatRecherche.getText().toString();
        Integer numMatiere2 = Integer.parseInt(numMatiere);
        Call<MatiereModel> call = matiereApi.getUnMatiere(numMatiere2);
        call.enqueue(new Callback<MatiereModel>() {
            @Override
            public void onResponse(Call<MatiereModel> call, Response<MatiereModel> response) {
                if(!response.isSuccessful()){
                    Log.i("BUG", "Code : " + response.code());
                    return;
                }
                MatiereModel mat = response.body();
                nomMatiere.setText(mat.getDesignMat());
                coefMatiere.setText(""+mat.getCoefMat());
            }

            @Override
            public void onFailure(Call<MatiereModel> call, Throwable t) {
                Log.i("BUG", ""+ t.getMessage());
            }
        });
    }

    private  void updateNote(){
        String numEtud = numEtRecherche.getText().toString().substring(3);
        String numMat = idMatRecherche.getText().toString().trim();
        String note = noteMatEt.getText().toString().trim();

        Integer id = Integer.parseInt(idNote);

        Integer numEt2 = Integer.parseInt(numEtud);
        Integer numMat2 = Integer.parseInt(numMat);
        Double note2 = Double.parseDouble(note);

        NoteModel noteModel = new NoteModel(numEt2,numMat2,note2);
        Call<NoteModel> call = noteApi.modifierNote(id, noteModel);
        call.enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if(!response.isSuccessful()){
                    Log.i("BUG", "Code : " + response.code());
                    return;
                }
                NoteModel note = response.body();
                Intent intent = new Intent(NoteClickActivity.this, NoteActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
                Log.i("BUG", "" + t.getMessage());
            }
        });
    }

    private void deleteNote(){
        Integer id = Integer.parseInt(idNote);
        Call<Void> call = noteApi.supprimerEtudiant(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("DEBUG", "Code : " + response.code());
                Intent intent = new Intent(NoteClickActivity.this, NoteActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("BUG", ""+ t.getMessage());
            }
        });
    }
}
