package com.example.application.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.application.R;
import com.example.application.adapter.EtudiantAdapter;
import com.example.application.model.EtudiantModel;
import com.example.application.service.EtudiantApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EtudiantActivity extends AppCompatActivity {

    /**
     * Declaration de tous les clickable
     * @param savedInstanceState
     */
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private FloatingActionButton newEtudiant;
    private ListView listEtudiant;
    private EtudiantApi etudiantApi;
    private EtudiantAdapter etudiantAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);

        /**
         * Initialisation de tous les clickable
         */
        this.drawerLayout = findViewById(R.id.drawer);
        this.navigationView = findViewById(R.id.navigationView);
        this.toolbar = findViewById(R.id.toolbar);
        this.newEtudiant = findViewById(R.id.newEtudiant);
        this.listEtudiant = findViewById(R.id.list_etudiant);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /**
         * Attribution d'action sur tout les clickable
         */

        //Menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.menuAccueil :
                        Intent intentAccueil = new Intent(EtudiantActivity.this, MainActivity.class);
                        startActivity(intentAccueil);
                        finish();
                        break;
                    case  R.id.menuEtudiant :
                        Intent intentEtudiant = new Intent(EtudiantActivity.this, EtudiantActivity.class);
                        startActivity(intentEtudiant);
                        finish();
                        break;
                    case  R.id.menuMatiere :
                        Intent intentMatiere = new Intent(EtudiantActivity.this, MatiereActivity.class);
                        startActivity(intentMatiere);
                        finish();
                        break;
                    case  R.id.menuNote :
                        Intent intentNote = new Intent(EtudiantActivity.this, NoteActivity.class);
                        startActivity(intentNote);
                        finish();
                        break;
                    case  R.id.menuBulletin :
                        Intent intentBulletin = new Intent(EtudiantActivity.this, BulletinActivity.class);
                        startActivity(intentBulletin);
                        finish();
                        break;
                    case  R.id.menuClassement :
                        Intent intentClassement = new Intent(EtudiantActivity.this, ClassementActivity.class);
                        startActivity(intentClassement);
                        finish();
                        break;
                }
                return true;
            }
        });

        //Button
        newEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newEtudiantIntent = new Intent(EtudiantActivity.this, NewEtudiantActivity.class);
                startActivity(newEtudiantIntent);
                finish();
            }
        });


        /**
         * Lister tous les étudiants
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        etudiantApi = retrofit.create(EtudiantApi.class);

        getEtudiants();

    }

    private void getEtudiants(){
        Call<List<EtudiantModel>> call = etudiantApi.getEtudiants();

        call.enqueue(new Callback<List<EtudiantModel>>() {
            @Override
            public void onResponse(Call<List<EtudiantModel>> call, Response<List<EtudiantModel>> response) {

                if (!response.isSuccessful()){
                    Log.i("BUG", "Code d'erreur" + response.code());
                    return;
                }
                List<EtudiantModel> list = response.body();
                listEtudiant.setAdapter(new EtudiantAdapter(EtudiantActivity.this, list));


            }

            @Override
            public void onFailure(Call<List<EtudiantModel>> call, Throwable t) {
                Log.i("BUG", "Erreur " + t.getMessage());
            }
        });
    }
}
