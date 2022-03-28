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
import com.example.application.model.EtudiantModel;
import com.example.application.model.NoteModel;
import com.example.application.service.NoteApi;
import com.example.application.vue.ClassementActivity;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class    ClassementAdapter extends BaseAdapter {
    private Context context;
    private List<EtudiantModel> etudiant;
    private LayoutInflater inflater;

    public ClassementAdapter(Context context, List<EtudiantModel> etudiant){
        this.context = context;
        this.etudiant = etudiant;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return etudiant.size();
    }

    @Override
    public EtudiantModel getItem(int position) {
        return etudiant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_classement, null);

        EtudiantModel currentItem = getItem(position);
        Integer itemNum = currentItem.getId();
        String itemNom = currentItem.getNomEt();
        Double itemMoyenne = currentItem.getMoyenne();

        /**
         * Calcule moyenne
         */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        //Recuperer les noms de TextView pour les afficher

        TextView num = convertView.findViewById(R.id.default_id_merite);
        TextView nom = convertView.findViewById(R.id.default_nom_merite);
        TextView moy= convertView.findViewById(R.id.default_moyenne_merite);



        //Lister Note

        NoteApi noteApi = retrofit.create(NoteApi.class);
        Call<List<NoteModel>> call = noteApi.getBulletin(currentItem.getId());

        call.enqueue(new Callback<List<NoteModel>>() {
            @Override
            public void onResponse(Call<List<NoteModel>> call, Response<List<NoteModel>> response) {
                if(!response.isSuccessful()){
                    Log.i("DEBUG", "Code : " + response.code());
                    return;
                }
                Double notePond = 0.0;
                Integer coefficient =0;
                Double moye=0.0;
                List<NoteModel> list = response.body();
                for (NoteModel note : list){
                    Integer coef = note.getMatiere().getCoefMat();
                    Double n = note.getNote();
                    notePond += coef * n;
                    coefficient += coef;
                }
                moye = notePond/coefficient;
               moy.setText("" +Math.round(moye));

            }

            @Override
            public void onFailure(Call<List<NoteModel>> call, Throwable t) {
                Log.i("BUG", " " + t.getMessage());
            }

        });


        //Affichage
        num.setText("E00"+ itemNum);
        nom.setText(itemNom);
        //moy.setText("" + itemMoyenne);


        return convertView;
    }
}
/**
 *



**/