package com.example.sae.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sae.API.API;
import com.example.sae.API.Loading;
import com.example.sae.Model.ActiviteDuMoment;
import com.example.sae.R;
import com.example.sae.View.Educateur.AccueilFragment;
import com.example.sae.View.Educateur.Menu_Educ;

import java.util.ArrayList;

public class HistoriqueAdapterEleve extends BaseAdapter {

    ArrayList<ActiviteDuMoment> activiteDuMoments;

    ArrayList<ActiviteDuMoment> test = new ArrayList<>();

    public HistoriqueAdapterEleve(ArrayList<ActiviteDuMoment> activiteDuMoments){
        this.activiteDuMoments=activiteDuMoments;
    }

    @Override
    public int getCount() {
        return activiteDuMoments.size();
    }

    @Override
    public Object getItem(int i) {
        return activiteDuMoments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ConstraintLayout layout;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if(view==null){
            layout = (ConstraintLayout) inflater.inflate(R.layout.adapter_historique_eleve,viewGroup,false);
        }else{
            layout= (ConstraintLayout) view;
        }

        TextView tv_activite_histo = layout.findViewById(R.id.tv_activite_histo);
        TextView tv_date_histo = layout.findViewById(R.id.tv_date_histo);
        Button bt_rejouer = layout.findViewById(R.id.bt_rejouer);

        tv_activite_histo.setText(activiteDuMoments.get(i).getActivite().getNom());
        tv_date_histo.setText(activiteDuMoments.get(i).getDate());


        return layout;
    }
}
