package com.example.sae.View.Educateur;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.DimenRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.sae.Model.Activite;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Model.Personne;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.adapter.ActiviteAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ActiviteFragment extends Fragment implements Observer {

    private final ModelEducateur modelEducateur;

    private ListView lv_activite;

    private EditText edt_activite;

    float textSize;
    Typeface typeface ;

    public ActiviteFragment(ModelEducateur modelEducateur) {
        this.modelEducateur=modelEducateur;
        modelEducateur.addObserver(this);
    }


    public void replaceFragment(Fragment fragment){
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
        View rootView = inflater.inflate(R.layout.fragment_activite, container, false);

        textSize = getResources().getDimension(R.dimen.TexteNormal) / getResources().getDisplayMetrics().scaledDensity;
        Typeface typeface = ResourcesCompat.getFont(Objects.requireNonNull(getContext()), R.font.noto_sans);

        ImageButton imageButtonActiviteNew = rootView.findViewById(R.id.imageButtonActiviteNew);

        lv_activite =rootView.findViewById(R.id.lv_activite);

        edt_activite = rootView.findViewById(R.id.edt_activite);

        edt_activite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                update();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lv_activite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                replaceFragment(new ModifyActivite(modelEducateur.getListeActivite().get(i),modelEducateur,ActiviteFragment.this));
            }
        });
        imageButtonActiviteNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ModifyActivite(ActiviteFragment.this,modelEducateur));
            }
        });


        update();
        return rootView;
    }

    @Override
    public void update() {
        if(getActivity()==null) return;
        ArrayList<Activite> activites = modelEducateur.getListeActivite();

        String inputSearch = edt_activite.getText().toString();

        if(!inputSearch.isEmpty()){
            ArrayList<Activite> tmpactivites = new ArrayList<>();
            for(Activite a : activites){

                if(Compare(inputSearch,a.getNom())){

                    tmpactivites.add(a);
                }
            }
            activites=tmpactivites;
        }

        ActiviteAdapter activiteAdapter = new ActiviteAdapter(activites);
        lv_activite.setAdapter(activiteAdapter);
    }

    private boolean Compare(String val1, String val2) {
        if (val1 == null || val2 == null) {
            return false;
        }
        return val1.toLowerCase(Locale.getDefault()).contains(val2.toLowerCase(Locale.getDefault())) ||
                val2.toLowerCase(Locale.getDefault()).contains(val1.toLowerCase(Locale.getDefault()));
    }

}