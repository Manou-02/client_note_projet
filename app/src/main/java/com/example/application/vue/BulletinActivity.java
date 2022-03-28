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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.adapter.BulletinAdapter;
import com.example.application.adapter.NoteAdapter;
import com.example.application.model.EtudiantModel;
import com.example.application.model.NoteModel;
import com.example.application.service.NoteApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BulletinActivity extends AppCompatActivity {

    private EditText numEtSearch;
    private Button getBulletin;
    private NoteApi noteApi;
    private ImageView retour;

    private RecyclerView recyclerView;
    private TextView champNumero;
    private TextView champNom;
    private TextView champMoyenne;
    private TextView champObservation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);

        /**
         * Initialisation de tous les Clickable
         */
        this.numEtSearch = findViewById(R.id.numEtSearch);
        this.getBulletin = findViewById(R.id.getBulletin);
        this.retour = findViewById(R.id.retour);

        /**
         * Initialisation de l'affichage
         */
        this.recyclerView = findViewById(R.id.recycleView);
        this.champNumero = findViewById(R.id.champNumero);
        this.champNom = findViewById(R.id.champNom);
        this.champMoyenne = findViewById(R.id.champMoyenne);
        this.champObservation = findViewById(R.id.champObservation);

        /**
         * Attribution d'action à tous les clickables
         */

        //Boutton retour
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BulletinActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Initialisation de Retrofit
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        noteApi = retrofit.create(NoteApi.class);

        //Rechercher les notes + Bulletin
        getBulletin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerView();
                afficheBulletin();


            }
        });


        /**
         * Appel du fonction pour afficher le bulletin
         */


    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BulletinActivity.this));

    }


    private void afficheBulletin(){
        if (numEtSearch.length() < 4){
            numEtSearch.setError("Le numero n'existe pas");
            numEtSearch.requestFocus();
            return;
        }
        String numEt = numEtSearch.getText().toString().substring(3);
        Integer numEt2 = Integer.parseInt(numEt);


        Call<List<NoteModel >> call = noteApi.getBulletin(numEt2);
        call.enqueue(new Callback<List<NoteModel>>() {
            @Override
            public void onResponse(Call<List<NoteModel>> call, Response<List<NoteModel>> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                    return;
                }

                List<NoteModel> list = response.body();

                recyclerView.setAdapter(new BulletinAdapter(BulletinActivity.this, list));
                String nom ="";
                Integer num = 0;
                Integer coef = 0;
                Double notePond = 0.0;
                Double moyenne = 0.0;
                for (NoteModel n : list){
                    nom = n.getEtudiant().getNomEt().toString();
                    num = n.getEtudiant().getId();
                    notePond += n.getNote()*n.getMatiere().getCoefMat();
                    coef += n.getMatiere().getCoefMat();

                }
                moyenne = notePond/coef;

                champNumero.setText("E00"+num);
                champNom.setText(nom);
                champMoyenne.setText(""+Math.round(moyenne));
                if (moyenne < 10){
                    champObservation.setText("Recalé(e)");
                }else {
                    champObservation.setText("Admis(e)");
                }

            }

            @Override
            public void onFailure(Call<List<NoteModel>> call, Throwable t) {
                Log.i("DEBUG", "" + t.getMessage());
            }
        });
    }
}
