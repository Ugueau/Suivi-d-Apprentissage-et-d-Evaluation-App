package com.example.sae.View.Educateur;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sae.API.Loading;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.adapter.HistoriqueAdapter;

import java.util.Objects;


public class HistoriqueFragment extends Fragment implements Observer {

    private final ModelEducateur modelEducateur;
    private ListView lv_historique_page;

    private final Loading loading;
    private final AccueilFragment accueilFragment;

    public HistoriqueFragment(ModelEducateur modelEducateur,Loading loading,AccueilFragment accueilFragment) {
        this.modelEducateur=modelEducateur;
        this.loading=loading;
        this.accueilFragment=accueilFragment;
        modelEducateur.addObserver(this);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout_Nav,fragment);
        fragmentTransaction.commit();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_historique, container, false);

        lv_historique_page = rootView.findViewById(R.id.lv_historique_page);

        lv_historique_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("DEBUG", "Revoir"+modelEducateur.getListeActiviteDuMoment().get(i).getId());
                replaceFragment(new DuMoment(modelEducateur.getListeActiviteDuMoment().get(i),modelEducateur,accueilFragment,getContext()));
            }
        });


        update();
        return rootView;
    }

    @Override
    public void update() {
        if(getActivity()==null) return;
        HistoriqueAdapter historiqueAdapter = new HistoriqueAdapter(modelEducateur.getListeActiviteDuMoment(), loading, accueilFragment);
        lv_historique_page.setAdapter(historiqueAdapter);

    }
}