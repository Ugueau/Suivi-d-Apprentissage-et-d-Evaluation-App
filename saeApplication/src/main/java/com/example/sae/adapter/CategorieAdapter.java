package com.example.sae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sae.Model.Categorie;
import com.example.sae.R;

import java.util.ArrayList;

public class CategorieAdapter extends BaseAdapter {

    ArrayList<Categorie> categories;

    public  CategorieAdapter(ArrayList<Categorie> categories){
        this.categories=categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
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
        tv_adapter_only_one.setText(categories.get(i).getNom());

        return layout;
    }
}
