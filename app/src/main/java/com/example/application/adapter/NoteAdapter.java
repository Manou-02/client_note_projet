package com.example.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.application.R;
import com.example.application.model.NoteModel;
import com.example.application.vue.NoteActivity;
import com.example.application.vue.NoteClickActivity;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<NoteModel> listeNote;
    private LayoutInflater inflater;

    public NoteAdapter(Context context, List<NoteModel> listeNote){
        this.context = context;
        this.listeNote = listeNote;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listeNote.size();
    }

    @Override
    public NoteModel getItem(int position) {
        return listeNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_note, null);

        NoteModel currentNote = getItem(position);


        Integer id = currentNote.getId();
        //Etudiant
        Integer numEt = currentNote.getEtudiant().getId();
        String nomEt = currentNote.getEtudiant().getNomEt();
        String prenomEt = currentNote.getEtudiant().getPrenomEt();

        //Matiere
        Integer numMat = currentNote.getMatiere().getId();
        String nomMat = currentNote.getMatiere().getDesignMat();
        Integer coefMat = currentNote.getMatiere().getCoefMat();

        //Note
        Double note = currentNote.getNote();

        TextView numEtText = convertView.findViewById(R.id.default_etudiantNum_note);
        TextView nomEtText = convertView.findViewById(R.id.default_etudiantNom_note);
        TextView nomMatText = convertView.findViewById(R.id.default_etudiantMatiere_note);
        TextView noteText = convertView.findViewById(R.id.default_etudiantNote_note);

        /**
         * Affichage du note
         */
        numEtText.setText("E00" + numEt);
        nomEtText.setText(nomEt);
        nomMatText.setText(nomMat);
        noteText.setText(note + "");


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), NoteClickActivity.class);

                intent.putExtra("idNote", id.toString());
                //Etudiant
                intent.putExtra("numEt", numEt.toString());
                intent.putExtra("nomEt", nomEt);
                intent.putExtra("prenomEt", prenomEt);
                //Matiere
                intent.putExtra("numMat", numMat.toString());
                intent.putExtra("designMat", nomMat);
                intent.putExtra("coefMatiere", coefMat.toString());
                //Note
                intent.putExtra("note", note.toString());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
