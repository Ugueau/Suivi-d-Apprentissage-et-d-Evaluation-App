package com.example.sae.View.Educateur;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.sae.Model.ModelEducateur;
import com.example.sae.Model.Personne;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.adapter.EleveAdapter;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class EleveFragment extends Fragment implements Observer {

    private final ModelEducateur modelEducateur;
    private ListView lv_eleve;
    private ArrayList<Personne> personnes;
    private final Menu_Educ parent;
    private EditText inputRecherche;


    float textSize;
    Typeface typeface ;


    public EleveFragment(ModelEducateur modelEducateur, Menu_Educ parent) {
        this.modelEducateur=modelEducateur;
        this.parent = parent;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_eleve, container, false);

        textSize = getResources().getDimension(R.dimen.TexteNormal) / getResources().getDisplayMetrics().scaledDensity;
        Typeface typeface = ResourcesCompat.getFont(Objects.requireNonNull(getContext()), R.font.noto_sans);

        lv_eleve = rootView.findViewById(R.id.lv_eleve);
        lv_eleve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                parent.replaceFragment(new EleveDetail(personnes.get(i),modelEducateur));
            }
        });


        inputRecherche = rootView.findViewById(R.id.zoneRecherche);
        inputRecherche.addTextChangedListener(new TextWatcher() {
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

        update();
        return rootView;
    }

    @Override
    public void update() {
        if(getActivity()==null) return;
        ArrayList<Personne> tmpPersonne = modelEducateur.getListeEleveRecherche();

        String inputSearch = inputRecherche.getText().toString();
        Log.i("update", inputSearch);

        if(inputSearch.isEmpty()){
            personnes = tmpPersonne;
        }
        else{
            personnes = new ArrayList<>();
            for(Personne p : tmpPersonne){
                Log.i("TAG", p.getNom());
                if(Compare(inputSearch,p.getNom()) || Compare(inputSearch,p.getPrenom())){

                    personnes.add(p);
                }
            }
        }
        Log.i("update", personnes.toString());
        EleveAdapter eleveAdapter = new EleveAdapter(personnes);
        lv_eleve.setAdapter(eleveAdapter);
    }
    private boolean Compare(String val1, String val2) {
        if (val1 == null || val2 == null) {
            return false;
        }
        return val1.toLowerCase(Locale.getDefault()).contains(val2.toLowerCase(Locale.getDefault())) ||
                val2.toLowerCase(Locale.getDefault()).contains(val1.toLowerCase(Locale.getDefault()));
    }

}