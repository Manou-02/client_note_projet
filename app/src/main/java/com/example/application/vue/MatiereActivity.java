package com.example.application.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.application.R;
import com.example.application.adapter.MatiereAdapter;
import com.example.application.model.EtudiantModel;
import com.example.application.model.MatiereModel;
import com.example.application.service.MatiereApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatiereActivity extends AppCompatActivity {

    /**
     * Declaration de tous les clickable
     * @param savedInstanceState
     */
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private ListView listeMatiere;
    private FloatingActionButton newMatiere;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matiere);

        /**
         * Initialisation de tous les clickable
         */
        this.drawerLayout = findViewById(R.id.drawer);
        this.navigationView = findViewById(R.id.navigationView);
        this.toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        this.newMatiere = findViewById(R.id.newMatiere);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        this.listeMatiere = findViewById(R.id.liste_matiere);

        /**
         * Attribution d'action sur tout les clickable
         */
        newMatiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatiereActivity.this, NewMatiereActivity.class);
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
                        Intent intentAccueil = new Intent(MatiereActivity.this, MainActivity.class);
                        startActivity(intentAccueil);
                        finish();
                        break;
                    case  R.id.menuEtudiant :
                        Intent intentEtudiant = new Intent(MatiereActivity.this, EtudiantActivity.class);
                        startActivity(intentEtudiant);
                        finish();
                        break;
                    case  R.id.menuMatiere :
                        Intent intentMatiere = new Intent(MatiereActivity.this, MatiereActivity.class);
                        startActivity(intentMatiere);
                        finish();
                        break;
                    case  R.id.menuNote :
                        Intent intentNote = new Intent(MatiereActivity.this, NoteActivity.class);
                        startActivity(intentNote);
                        finish();
                        break;
                    case  R.id.menuBulletin :
                        Intent intentBulletin = new Intent(MatiereActivity.this, BulletinActivity.class);
                        startActivity(intentBulletin);
                        finish();
                    case  R.id.menuClassement :
                        Intent intentClassement = new Intent(MatiereActivity.this, ClassementActivity.class);
                        startActivity(intentClassement);
                        finish();
                }
                return true;
            }
        });

        /**
         * Affichage de tous les matieres
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MatiereApi matiereApi = retrofit.create(MatiereApi.class);
        Call<List<MatiereModel>> call = matiereApi.getMatieres();
        call.enqueue(new Callback<List<MatiereModel>>() {
            @Override
            public void onResponse(Call<List<MatiereModel>> call, Response<List<MatiereModel>> response) {
                if(!response.isSuccessful()){
                    Log.i("BIG", "Code : " + response.code());
                    return;
                }
                List<MatiereModel> list = response.body();
                listeMatiere.setAdapter(new MatiereAdapter(MatiereActivity.this, list));

            }

            @Override
            public void onFailure(Call<List<MatiereModel>> call, Throwable t) {
                Log.i("BUG", ' ' + t.getMessage());
            }
        });

    }
}
