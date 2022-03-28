package com.example.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.model.NoteModel;

import java.util.List;

public class BulletinAdapter extends RecyclerView.Adapter<BulletinAdapter.ViewHolder> {

    Context context;
    List<NoteModel> listeNote;

    public BulletinAdapter(Context context, List<NoteModel> listeNote) {
        this.context = context;
        this.listeNote = listeNote;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_bulletin, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(listeNote != null && listeNote.size() > 0){
            NoteModel model = listeNote.get(position);
            holder.design_note.setText(model.getMatiere().getDesignMat());
            holder.coef_note.setText(""+model.getMatiere().getCoefMat());
            holder.note_note.setText(""+model.getNote());
            holder.note_pond.setText(""+model.getMatiere().getCoefMat()*model.getNote());
        }else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return listeNote.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView design_note, coef_note, note_note, note_pond;


        public ViewHolder(View itemView){
            super(itemView);
            design_note = itemView.findViewById(R.id.bulletin_design);
            coef_note = itemView.findViewById(R.id.bulletin_coef);
            note_note = itemView.findViewById(R.id.bulletin_note);
            note_pond = itemView.findViewById(R.id.bulletin_note_pondere);
        }
    }
}
