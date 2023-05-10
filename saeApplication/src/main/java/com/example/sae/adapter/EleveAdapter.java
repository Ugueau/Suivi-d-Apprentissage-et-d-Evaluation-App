package com.example.sae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sae.Model.Personne;
import com.example.sae.R;

import java.util.ArrayList;

public class EleveAdapter extends BaseAdapter {

    ArrayList<Personne> personnes;


    public EleveAdapter(ArrayList<Personne> personnes){
        this.personnes=personnes;
    }

    @Override
    public int getCount() {
        return personnes.size();
    }

    @Override
    public Object getItem(int i) {
        return personnes.get(i);
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
            layout = (ConstraintLayout) inflater.inflate(R.layout.only_one_tv_adapter,viewGroup,false);
        }else{
            layout= (ConstraintLayout) view;
        }

        TextView tv_eleve_all = layout.findViewById(R.id.tv_adapter_only_one);
        if(personnes.get(i).getNumeroHomonyme()==0){
            tv_eleve_all.setText(personnes.get(i).getNom()+" "+personnes.get(i).getPrenom());
        }
        else{
            tv_eleve_all.setText(personnes.get(i).getNom()+" "+personnes.get(i).getPrenom()+" "+personnes.get(i).getNumeroHomonyme());
        }



        return layout;
    }
}
