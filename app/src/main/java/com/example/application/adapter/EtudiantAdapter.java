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
import com.example.application.vue.EtudiantActivity;
import com.example.application.vue.EtudiantClickActivity;

import java.util.List;

public class EtudiantAdapter extends BaseAdapter {
    private Context context;
    private List<EtudiantModel> etudiantList;
    private LayoutInflater inflater;

    public EtudiantAdapter(Context context, List<EtudiantModel> etudiantList){
        this.context = context;
        this.etudiantList = etudiantList;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return etudiantList.size();
    }

    @Override
    public EtudiantModel getItem(int position) {
        return etudiantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_etudiant, null);

        EtudiantModel currentEtudiant = getItem(position);
        Integer itemNum = currentEtudiant.getId();
        String itemNom = currentEtudiant.getNomEt();
        String itemPrenom = currentEtudiant.getPrenomEt();
        String itemNiveau = currentEtudiant.getNiveauEt();
        TextView idEt = convertView.findViewById(R.id.default_numero_etudiant);
        idEt.setText("E00" + itemNum);
        TextView nomEt = convertView.findViewById(R.id.default_nom_etudiant);
        nomEt.setText(itemNom);
        TextView prenomEt = convertView.findViewById(R.id.default_prenom_etudiant);
        prenomEt.setText(itemPrenom);
        TextView niveauEt = convertView.findViewById(R.id.default_niveau_etudiant);
        niveauEt.setText(itemNiveau + "");


        String numero = itemNum.toString().trim();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context.getApplicationContext(),EtudiantClickActivity.class);
                intent.putExtra("n", itemNum.toString());
                intent.putExtra("numEt", numero);
                intent.putExtra("nomEt", itemNom);
                intent.putExtra("prenomEt", itemPrenom);
                intent.putExtra("niveauEt", itemNiveau);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
