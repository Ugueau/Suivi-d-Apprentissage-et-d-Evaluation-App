package com.example.sae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sae.Model.Activite;
import com.example.sae.R;

import java.util.ArrayList;

public class ActiviteAdapter extends BaseAdapter {

    ArrayList<Activite> activites;

    public ActiviteAdapter(ArrayList<Activite> activites){
        this.activites=activites;
    }


    @Override
    public int getCount() {
        return activites.size();
    }

    @Override
    public Object getItem(int i) {
        return activites.get(i);
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

        TextView tv_adapter_only_one = layout.findViewById(R.id.tv_adapter_only_one);
        tv_adapter_only_one.setText(activites.get(i).getNom());

        return layout;
    }
}
