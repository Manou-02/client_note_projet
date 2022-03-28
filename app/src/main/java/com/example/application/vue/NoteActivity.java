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
import com.example.application.adapter.NoteAdapter;
import com.example.application.model.NoteModel;
import com.example.application.service.NoteApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoteActivity extends AppCompatActivity {

    /**
     * Declaration de tous les clickable
     * @param savedInstanceState
     */
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private ListView listeNote;
    private FloatingActionButton newNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        /**
         * Initialisation de tous les clickable
         */
        this.drawerLayout = findViewById(R.id.drawer);
        this.navigationView = findViewById(R.id.navigationView);
        this.toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        this.listeNote = findViewById(R.id.listeNote);
        this.newNote = findViewById(R.id.newNote);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /**
         * Attribution d'action sur tout les clickable
         */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.menuAccueil :
                        Intent intentAccueil = new Intent(NoteActivity.this, MainActivity.class);
                        startActivity(intentAccueil);
                        finish();
                        break;
                    case  R.id.menuEtudiant :
                        Intent intentEtudiant = new Intent(NoteActivity.this, EtudiantActivity.class);
                        startActivity(intentEtudiant);
                        finish();
                        break;
                    case  R.id.menuMatiere :
                        Intent intentMatiere = new Intent(NoteActivity.this, MatiereActivity.class);
                        startActivity(intentMatiere);
                        finish();
                        break;
                    case  R.id.menuNote :
                        Intent intentNote = new Intent(NoteActivity.this, NoteActivity.class);
                        startActivity(intentNote);
                        finish();
                        break;
                    case  R.id.menuBulletin :
                        Intent intentBulletin = new Intent(NoteActivity.this, BulletinActivity.class);
                        startActivity(intentBulletin);
                        finish();
                        break;

                    case  R.id.menuClassement :
                        Intent intentClassement = new Intent(NoteActivity.this, ClassementActivity.class);
                        startActivity(intentClassement);
                        finish();
                        break;
                }
                return true;
            }
        });

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this, NewNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Lister Note
        NoteApi noteApi = retrofit.create(NoteApi.class);
        Call<List<NoteModel>> call = noteApi.getNotes();

        call.enqueue(new Callback<List<NoteModel>>() {
            @Override
            public void onResponse(Call<List<NoteModel>> call, Response<List<NoteModel>> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                    return;
                }
                List<NoteModel> list = response.body();
                listeNote.setAdapter(new NoteAdapter(NoteActivity.this, list));
            }

            @Override
            public void onFailure(Call<List<NoteModel>> call, Throwable t) {
                Log.i("BUG", " " + t.getMessage());
            }
        });

    }
}
