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
import com.example.sae.Model.ModelEducateur;
import com.example.sae.R;
import com.example.sae.View.Educateur.AccueilFragment;
import com.example.sae.View.Educateur.Menu_Educ;

import java.util.ArrayList;

public class HistoriqueAdapter extends BaseAdapter {

    ArrayList<ActiviteDuMoment> activiteDuMoments;
    AccueilFragment accueilFragment;
    Loading loading;

    ArrayList<ActiviteDuMoment> test = new ArrayList<>();

    public HistoriqueAdapter(ArrayList<ActiviteDuMoment> activiteDuMoments, Loading loading,AccueilFragment accueilFragment){
        this.activiteDuMoments=activiteDuMoments;
        this.loading=loading;
        this.accueilFragment=accueilFragment;
        Log.e("HistoriqueAdapter", String.valueOf(activiteDuMoments.size() ));
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
            layout = (ConstraintLayout) inflater.inflate(R.layout.adapter_historique,viewGroup,false);
        }else{
            layout= (ConstraintLayout) view;
        }

        TextView tv_activite_histo = layout.findViewById(R.id.tv_activite_histo);
        TextView tv_date_histo = layout.findViewById(R.id.tv_date_histo);
        Button bt_rejouer = layout.findViewById(R.id.bt_rejouer);

        tv_activite_histo.setText(activiteDuMoments.get(i).getActivite().getNom());
        tv_date_histo.setText(activiteDuMoments.get(i).getDate());
        bt_rejouer.setTag(activiteDuMoments.get(i).getId());
        bt_rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", view.getTag().toString());
                int i = Integer.parseInt(view.getTag().toString());
                loading.showLoading();
                API api = new API();
                api.rejouer(Menu_Educ.token,i,accueilFragment);
            }
        });


        return layout;
    }
}
