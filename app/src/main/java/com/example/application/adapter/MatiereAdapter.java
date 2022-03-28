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
import com.example.application.model.MatiereModel;
import com.example.application.vue.MatiereClickActivity;

import java.util.List;

public class MatiereAdapter extends BaseAdapter {

    private Context context;
    private List<MatiereModel> listeMatiere;
    private LayoutInflater inflater;

    public MatiereAdapter(Context context, List<MatiereModel> listeMatiere){
        this.context = context;
        this.listeMatiere = listeMatiere;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listeMatiere.size();
    }

    @Override
    public MatiereModel getItem(int position) {
        return listeMatiere.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_matiere, null);

        MatiereModel currrentItem = getItem(position);

        Integer itemId = currrentItem.getId();
        String itemNom = currrentItem.getDesignMat();
        Integer itemCoef = currrentItem.getCoefMat();

        TextView idMat = convertView.findViewById(R.id.default_id_matiere);
        idMat.setText(""+itemId);
        TextView nomMat = convertView.findViewById(R.id.default_nom_matiere);
        nomMat.setText(itemNom);
        TextView coefMat = convertView.findViewById(R.id.default_coef_matiere);
        coefMat.setText("" + itemCoef);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), MatiereClickActivity.class);
                intent.putExtra("numMat", itemId.toString());
                intent.putExtra("nomMat", itemNom);
                intent.putExtra("coefMat", itemCoef.toString());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
