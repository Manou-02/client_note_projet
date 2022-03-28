package com.example.application.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.application.R;
import com.example.application.model.NiveauModel;

import java.util.List;

public class NiveauAdapter extends BaseAdapter {
    private Context context;
    private List<NiveauModel> niveauList;
    private LayoutInflater inflater;

    public NiveauAdapter(Context context, List<NiveauModel> niveauList){
        this.context = context;
        this.niveauList = niveauList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return niveauList.size();
    }

    @Override
    public NiveauModel getItem(int position) {
        return niveauList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_niveau, null);

        /**
         * Recuperer tous les informations de l'item
         */
        NiveauModel currentItem = getItem(position);
        Integer itemId = currentItem.getId();
        String itemName = currentItem.getLibelleNiveau();

        TextView idNiveau = convertView.findViewById(R.id.default_id_niveau);
        idNiveau.setText(itemId + " ");
        TextView nameNiveau = convertView.findViewById(R.id.default_name_niveau);
        nameNiveau.setText(itemName);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "" + itemId + " " + itemName);
            }
        });

        return convertView;
    }
}
