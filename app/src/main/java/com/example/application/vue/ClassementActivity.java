package com.example.application.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.adapter.ClassementAdapter;
import com.example.application.adapter.MatiereAdapter;
import com.example.application.model.EtudiantModel;
import com.example.application.model.MatiereModel;
import com.example.application.model.NoteModel;
import com.example.application.service.EtudiantApi;
import com.example.application.service.MatiereApi;
import com.example.application.service.NoteApi;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassementActivity extends AppCompatActivity {

    private ListView listeMerite;
    private ImageView retour;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classement);

        this.listeMerite = findViewById(R.id.listeMerite);
        this.retour = findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassementActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /**
         * Affichage de tous les matieres
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Lister Note

        EtudiantApi etudiantApi = retrofit.create(EtudiantApi.class);
        Call<List<EtudiantModel>> call = etudiantApi.getEtudiants();
        call.enqueue(new Callback<List<EtudiantModel>>() {
            @Override
            public void onResponse(Call<List<EtudiantModel>> call, Response<List<EtudiantModel>> response) {
                if(!response.isSuccessful()){
                    Log.i("BUG", "Code : " + response.code());
                    return;
                }

                List<EtudiantModel> etudiant = response.body();
                /**
                 * Trillage
                */
                Collections.sort(etudiant, new Comparator<EtudiantModel>(){

                    @Override
                    public int compare(EtudiantModel o1, EtudiantModel o2) {
                        return o1.getNomEt().compareTo(o2.getNomEt());
                    }
                });

                listeMerite.setAdapter(new ClassementAdapter(ClassementActivity.this, etudiant));
            }

            @Override
            public void onFailure(Call<List<EtudiantModel>> call, Throwable t) {
                Log.i("BUG", "" + t.getMessage());
            }
        });
    }
}
