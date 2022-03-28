package com.example.application.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.adapter.NiveauAdapter;
import com.example.application.model.NiveauModel;
import com.example.application.service.NiveauApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NiveauActivity extends AppCompatActivity {
    /**
     * Declaration de tous les clickable
     * @param savedInstanceState
     */
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private ListView listeNiveau;
    private FloatingActionButton newNiveau;
    private NiveauApi niveauApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau);

        /**
         * Initialisation de tous les clickable
         */
        this.drawerLayout = findViewById(R.id.drawer);
        this.navigationView = findViewById(R.id.navigationView);
        this.toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        this.listeNiveau = findViewById(R.id.list_niveau);
        this.newNiveau = findViewById(R.id.newNiveau);


        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /**
         * Attribution d'action sur tout les clickable
         */

        newNiveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NiveauActivity.this, NewNiveauActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.menuAccueil :
                        Intent intentAccueil = new Intent(NiveauActivity.this, MainActivity.class);
                        startActivity(intentAccueil);
                        finish();
                        break;
                    case  R.id.menuEtudiant :
                        Intent intentEtudiant = new Intent(NiveauActivity.this, EtudiantActivity.class);
                        startActivity(intentEtudiant);
                        finish();
                        break;
                    case  R.id.menuMatiere :
                        Intent intentMatiere = new Intent(NiveauActivity.this, MatiereActivity.class);
                        startActivity(intentMatiere);
                        finish();
                        break;
                    case  R.id.menuNote :
                        Intent intentNote = new Intent(NiveauActivity.this, NoteActivity.class);
                        startActivity(intentNote);
                        finish();
                        break;
                    case  R.id.menuBulletin :
                        Log.i("DEBUG", "Bulletin");
                        break;
                }
                return true;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         niveauApi = retrofit.create(NiveauApi.class);


        getNiveaux();
    }


    /**
     * Recuperer tous les niveaux
     */
    public void getNiveaux(){
        Call<List<NiveauModel>> call = niveauApi.getNiveaux();


        call.enqueue(new Callback<List<NiveauModel>>() {
            @Override
            public void onResponse(Call<List<NiveauModel>> call, Response<List<NiveauModel>> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                    return;
                }
                List<NiveauModel> lists = response.body();
                listeNiveau.setAdapter(new NiveauAdapter(NiveauActivity.this, lists));
            }

            @Override
            public void onFailure(Call<List<NiveauModel>> call, Throwable t) {
                Log.i("Bug", t.getMessage());
            }
        });
    }

}
