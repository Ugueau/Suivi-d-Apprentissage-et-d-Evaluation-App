package com.example.sae.View.Educateur;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.sae.Model.Activite;
import com.example.sae.Model.Categorie;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.adapter.ActiviteAdapter;
import com.example.sae.adapter.CategorieAdapter;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class categorieFragment extends Fragment implements Observer {


    private final ModelEducateur modeleCategorieCompetence;

    private EditText CategorieRechercheInput;
    private DetailCategorieFragment detailCategorieFragment;
    private final Menu_Educ parent;


    private ListView lv_categorie;


    float textSize ;
    Typeface typeface ;


    public categorieFragment(ModelEducateur m, Menu_Educ parent) {
        this.modeleCategorieCompetence=m;
        ArrayList<Observer> observersUpdate = new ArrayList<>();
        observersUpdate.add(this);
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

        View rootView = inflater.inflate(R.layout.fragment_categorie, container, false);

        textSize = getResources().getDimension(R.dimen.TexteNormal) / getResources().getDisplayMetrics().scaledDensity;
        Typeface typeface = ResourcesCompat.getFont(Objects.requireNonNull(getContext()), R.font.noto_sans);


        CategorieRechercheInput = rootView.findViewById(R.id.CategorieRechercheInput);
        lv_categorie = rootView.findViewById(R.id.lv_categorie);

        lv_categorie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                parent.replaceFragment(new DetailCategorieFragment(modeleCategorieCompetence.getListCategorie().get(i),modeleCategorieCompetence));
            }
        });
        CategorieRechercheInput.addTextChangedListener(new TextWatcher() {
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
        ArrayList<Categorie> categories = modeleCategorieCompetence.getListCategorie();

        String inputSearch = CategorieRechercheInput.getText().toString();

        if(!inputSearch.isEmpty()){
            ArrayList<Categorie> tmpcategories = new ArrayList<>();
            for(Categorie c : categories){

                if(Compare(inputSearch,c.getNom())){

                    tmpcategories.add(c);
                }
            }
            categories=tmpcategories;
        }

        CategorieAdapter categorieAdapter = new CategorieAdapter(categories);
        lv_categorie.setAdapter(categorieAdapter);

    }

    private boolean Compare(String val1, String val2) {
        if (val1 == null || val2 == null) {
            return false;
        }
        return val1.toLowerCase(Locale.getDefault()).contains(val2.toLowerCase(Locale.getDefault())) ||
                val2.toLowerCase(Locale.getDefault()).contains(val1.toLowerCase(Locale.getDefault()));
    }
}