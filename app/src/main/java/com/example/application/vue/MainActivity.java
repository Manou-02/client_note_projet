package com.example.application.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.application.R;

public class MainActivity extends AppCompatActivity {

    /**
     * Creation de tous les clickables
     */
    private CardView cardEtudiant;
    private CardView cardBulletin;
    private CardView cardMatiere;
    private CardView cardNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Initialisation de tous les clickable
         */
        this.cardEtudiant = findViewById(R.id.cardEtudiant);
        this.cardBulletin = findViewById(R.id.cardBulletin);
        this.cardMatiere = findViewById(R.id.cardMatiere);
        this.cardNote = findViewById(R.id.cardNote);

        /**
         * Callback pour les clicks
         */
        cardEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent etudiantIntent = new Intent(MainActivity.this, EtudiantActivity.class);
                startActivity(etudiantIntent);
                finish();
            }
        });
        cardBulletin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bulletinIntent = new Intent(MainActivity.this, BulletinActivity.class);
                startActivity(bulletinIntent);
                finish();
            }
        });
        cardMatiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matiereIntent = new Intent(MainActivity.this, MatiereActivity.class);
                startActivity(matiereIntent);
                finish();
            }
        });
        cardNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(noteIntent);
                finish();
            }
        });

    }
}