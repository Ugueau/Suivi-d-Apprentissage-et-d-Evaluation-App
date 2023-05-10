package com.example.sae.View.Educateur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sae.API.API;
import com.example.sae.API.Loading;
import com.example.sae.Model.Activite;
import com.example.sae.Model.ActiviteDuMoment;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Model.MultiMap;
import com.example.sae.Model.Personne;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.Static;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;


public class AccueilFragment extends Fragment implements Observer, View.OnClickListener {

    private final ModelEducateur modelEducateur;
    private LinearLayout AccueilSelectedEducateur;
    private LinearLayout AccueilSelectedEleve;
    private LinearLayout AccueilHistorique;


    private TextInputEditText popEducateurInput;
    private ConstraintLayout PopEducateurRecherche;
    private LinearLayout popEducateurLinearLayoutEducateur;

    private TextInputEditText popEleveInput;
    private ConstraintLayout PopEleveRecherche;
    private LinearLayout popEducateurLinearLayoutEleve;

    private Button buttonAccueilActivite;
    private TextInputEditText popActiviteInput;
    private ConstraintLayout PopActiviteRecherche;
    private LinearLayout popEducateurLinearLayoutActivite;

    private InputMethodManager imm;

    private Loading loading ;


    float textSize ;
    Typeface typeface ;




    public AccueilFragment(ModelEducateur modelEducateur) {
        this.modelEducateur=modelEducateur;
        this.modelEducateur.addObserver(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accueil, container, false);

        textSize = getResources().getDimension(R.dimen.TexteNormal) / getResources().getDisplayMetrics().scaledDensity;
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.noto_sans);

        loading = new Loading(getContext());

        AccueilSelectedEducateur = rootView.findViewById(R.id.AccueilSelectedEducateur);
        AccueilSelectedEleve = rootView.findViewById(R.id.AccueilSelectedEleve);
        Button buttonAcceuilDebut = rootView.findViewById(R.id.buttonAcceuilDebut);


        Button bt_historique = rootView.findViewById(R.id.bt_historique);


        Button buttonAccueilAddEduc = rootView.findViewById(R.id.buttonAccueilAddEduc);
        ImageButton popEducateurButtonFermer = rootView.findViewById(R.id.popEducateurButtonFermer);
        popEducateurInput = rootView.findViewById(R.id.popEducateurInput);
        PopEducateurRecherche = rootView.findViewById(R.id.PopEducateurRecherche);
        popEducateurLinearLayoutEducateur = rootView.findViewById(R.id.popEducateurLinearLayoutEducateur);
        Button popEducateurButtonValider = rootView.findViewById(R.id.popEducateurButtonValider);
        ConstraintLayout backgroudpopRechercheEducateur = rootView.findViewById(R.id.backpopRechercheEducateur);

        Button buttonAccueilAddEleve = rootView.findViewById(R.id.buttonAccueilAddEleve);
        ImageButton popEleveButtonFermer = rootView.findViewById(R.id.popEleveButtonFermer);
        popEleveInput = rootView.findViewById(R.id.popEleveInput);
        PopEleveRecherche= rootView.findViewById(R.id.PopEleveRecherche);
        popEducateurLinearLayoutEleve = rootView.findViewById(R.id.popEducateurLinearLayoutEleve);
        Button popEleveButtonValider = rootView.findViewById(R.id.popEleveButtonValider);
        ConstraintLayout backgpopRechercherEleve = rootView.findViewById(R.id.backgpopRechercherEleve);

        buttonAccueilActivite= rootView.findViewById(R.id.buttonAccueilActivite);
        ImageButton popActiviteButtonFermer = rootView.findViewById(R.id.popActiviteButtonFermer);
        popActiviteInput = rootView.findViewById(R.id.popActiviteInput);
        PopActiviteRecherche= rootView.findViewById(R.id.PopActiviteRecherche);
        popEducateurLinearLayoutActivite = rootView.findViewById(R.id.popEducateurLinearLayoutActivite);
        Button popActiviteButtonValider = rootView.findViewById(R.id.popActiviteButtonValider);
        ConstraintLayout backgpopRechercheActivite = rootView.findViewById(R.id.backgpopRechercheActivite);

        imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);

        popEleveInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateEleveRechercher(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        popEducateurInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateEducateurRechercher(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        popActiviteInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateActiviteRechercher(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonAccueilAddEduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEducateurRecherche,View.VISIBLE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popEducateurButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEducateurRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        buttonAccueilAddEleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveRecherche,View.VISIBLE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        });
        popEleveButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        buttonAccueilActivite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopActiviteRecherche,View.VISIBLE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popActiviteButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopActiviteRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popActiviteButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopActiviteRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popEleveButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popEducateurButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Static.setVisibilite(PopEducateurRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        buttonAcceuilDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(modelEducateur.getActiviteSelected().getNom()!=null && modelEducateur.getListeEducateurSelected().size()!=0){
                    API api = new API();
                    api.addADM(Menu_Educ.token, modelEducateur.getListeEleveSelected(),modelEducateur.getListeEducateurSelected(),modelEducateur.getActiviteSelected(),AccueilFragment.this,modelEducateur);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Erreur");
                    builder.setMessage("Les champs ne sont pas remplis, il faut au moins un éducateur et une activité.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            }
        });
        backgroudpopRechercheEducateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEducateurRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        backgpopRechercherEleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        backgpopRechercheActivite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopActiviteRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        bt_historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HistoriqueFragment(modelEducateur,loading,AccueilFragment.this));
            }
        });

        update();
        return rootView;
    }



    public void queryApiCreateADM(int idADM){
        for(ActiviteDuMoment activiteDuMoment: modelEducateur.getListeActiviteDuMoment()) {
            Log.i("TAG", String.valueOf(activiteDuMoment.getId()));
            if(activiteDuMoment.getId()==idADM){
                replaceFragment(new DuMoment(activiteDuMoment, modelEducateur, this,getContext()));
            }

        }
    }

    private MultiMap<Integer,Personne> rechercherEleve(String recherches){
        ArrayList<Personne> all = modelEducateur.getListeEleveRecherche();

        MultiMap<Integer,Personne> map = new MultiMap();
        for(Personne p: all) {
            map.put(Static.recherche(recherches,p.getRechercher()),p);
        }
        return map;

    }
    private MultiMap<Integer,Personne> rechercherEducateur(String recherches){
        ArrayList<Personne> all = modelEducateur.getListeEducateurRecherche();

        MultiMap<Integer,Personne> map = new MultiMap();
        for(Personne p: all) {
            map.put(Static.recherche(recherches,p.getRechercher()),p);
        }
        return map;
    }
    private MultiMap<Integer,Activite> rechercherActivite(String recherches){
        ArrayList<Activite> all = modelEducateur.getListeActivite();

        MultiMap<Integer,Activite> map = new MultiMap();
        for(Activite p: all) {
            map.put(Static.recherche(recherches,p.getRechercher()),p);
        }
        return map;
    }

    private void updateEleveRechercher(String recherches){
        //popUp Eleve
        popEducateurLinearLayoutEleve.removeAllViews();

        MultiMap<Integer,Personne> map = rechercherEleve(recherches);
        for (Integer i: map.keySet())
        {
            for(Personne p : map.get(i)){
                CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setTag("checkBoxEleve/"+p.getId());
                checkbox.setText(p.getPrenomNomNum());
                checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                checkbox.setTypeface(typeface);
                checkbox.setPadding(10,10,10,10);
                checkbox.setGravity(Gravity.CENTER);

                for(Personne select : modelEducateur.getListeEleveSelected()){
                    if(p==select){
                        checkbox.setChecked(true);
                    }
                }
                checkbox.setOnClickListener(this);
                popEducateurLinearLayoutEleve.addView(checkbox);
            }
        }
    }

    private void updateEducateurRechercher(String recherches){
        //popUpEducateur
        popEducateurLinearLayoutEducateur.removeAllViews();
        MultiMap<Integer,Personne> map = rechercherEducateur(recherches);
        for (Integer i: map.keySet())
        {
            for(Personne p : map.get(i)){
                CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setTag("checkBoxEducateur/"+p.getId());
                checkbox.setText(p.getPrenomNomNum());
                checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                checkbox.setTypeface(typeface);
                checkbox.setPadding(10,10,10,10);
                checkbox.setGravity(Gravity.CENTER);

                for(Personne select : modelEducateur.getListeEducateurSelected()){
                    if(p==select){
                        checkbox.setChecked(true);
                    }
                }

                checkbox.setOnClickListener(this);

                popEducateurLinearLayoutEducateur.addView(checkbox);
            }
        }
    }

    private void updateActiviteRechercher(String recherches){
        //popUp activite
        popEducateurLinearLayoutActivite.removeAllViews();

        MultiMap<Integer,Activite> map = rechercherActivite(recherches);
        for (Integer i: map.keySet())
        {
            for(Activite p : map.get(i)){
                RadioButton checkbox = new RadioButton(getActivity());
                checkbox.setTag("checkBoxActivite/"+p.getId());
                checkbox.setText(p.getNom());
                checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                checkbox.setTypeface(typeface);
                checkbox.setPadding(10,10,10,10);
                checkbox.setGravity(Gravity.CENTER);

                if(modelEducateur.getActiviteSelected()==p){
                    checkbox.setChecked(true);
                    buttonAccueilActivite.setText(p.getNom());
                }
                checkbox.setOnClickListener(this);

                popEducateurLinearLayoutActivite.addView(checkbox);
            }
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout_Nav,fragment);
        fragmentTransaction.commit();

    }


    @Override
    public void update() {
        if(getActivity()==null) return;

        AccueilSelectedEleve.removeAllViews();
        AccueilSelectedEducateur.removeAllViews();


        //Remplie les Eleves Selectioné
        for(Personne p: modelEducateur.getListeEleveSelected()){
            LinearLayout tableau = new LinearLayout(getActivity());
            tableau.setOrientation(LinearLayout.HORIZONTAL);
            tableau.setGravity(Gravity.CENTER);

            TextView test = new TextView(getActivity());
            test.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            test.setTypeface(typeface);
            test.setText(p.getPrenomNomNum());

            ImageButton butIm = new ImageButton(getActivity());
            butIm.setImageResource(R.drawable.ic_baseline_closeblack_24);
            butIm.setBackgroundColor(Color.TRANSPARENT);
            butIm.setTag("buttonRemoveEleve/"+p.getId());
            butIm.setOnClickListener(this);

            tableau.addView(butIm);
            tableau.addView(test);


            AccueilSelectedEleve.addView(tableau);

        }
        //Remplie les Educateurs Selectioné
        for(Personne p: modelEducateur.getListeEducateurSelected()){
            LinearLayout tableau = new LinearLayout(getActivity());
            tableau.setOrientation(LinearLayout.HORIZONTAL);
            tableau.setGravity(Gravity.CENTER);

            TextView test = new TextView(getActivity());
            test.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            test.setTypeface(typeface);
            test.setText(p.getPrenomNomNum());

            ImageButton butIm = new ImageButton(getActivity());
            butIm.setImageResource(R.drawable.ic_baseline_closeblack_24);
            butIm.setBackgroundColor(Color.TRANSPARENT);
            butIm.setTag("buttonRemoveEducateur/"+p.getId());
            butIm.setOnClickListener(this);


            tableau.addView(butIm);
            tableau.addView(test);

            AccueilSelectedEducateur.addView(tableau);
        }

        updateEleveRechercher(Objects.requireNonNull(popEleveInput.getText()).toString());

        updateEducateurRechercher(Objects.requireNonNull(popEducateurInput.getText()).toString());

        updateActiviteRechercher(Objects.requireNonNull(popActiviteInput.getText()).toString());
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().toString().contains("checkBoxActivite")){
            for(int i=0; i<popEducateurLinearLayoutActivite.getChildCount();i++){
                RadioButton test = (RadioButton)popEducateurLinearLayoutActivite.getChildAt(i);
                test.setChecked(false);
            }
            for(Activite p: modelEducateur.getListeActivite()){

                String comp="checkBoxActivite/"+p.getId();
                String button=view.getTag().toString();
                if(comp.equals(button)){
                    RadioButton test = (RadioButton)view;
                    test.setChecked(true);
                }
            }
            for(int i=0; i<popEducateurLinearLayoutActivite.getChildCount();i++){
                RadioButton test = (RadioButton)popEducateurLinearLayoutActivite.getChildAt(i);
                if(test.isChecked()){
                    for(Activite p: modelEducateur.getListeActivite()){
                        String comp="checkBoxActivite/"+p.getId();
                        String button=test.getTag().toString();
                        if(comp.equals(button)){
                            modelEducateur.setActiviteSelected(p);
                        }
                    }

                }
            }
        }
        else if(view.getTag().toString().contains("buttonRemoveEleve")){
            for(Personne p: modelEducateur.getListeEleveSelected()){
                String comp="buttonRemoveEleve/"+p.getId();
                String button=view.getTag().toString();
                if(comp.equals(button)){
                    modelEducateur.removeEleveSelected(p);
                    return;
                }
            }
        }
        else if(view.getTag().toString().contains("buttonRemoveEducateur")){
            for(Personne p: modelEducateur.getListeEducateurSelected()){
                String comp="buttonRemoveEducateur/"+p.getId();
                String button=view.getTag().toString();
                if(comp.equals(button)){
                    modelEducateur.removeEducateurSelected(p);
                    return;
                }
            }
        }
        else if(view.getTag().toString().contains("checkBoxEducateur")){
            ArrayList<Personne> EducateurTemp= new ArrayList<>();
            for(int i=0; i<popEducateurLinearLayoutEducateur.getChildCount();i++){
                CheckBox test = (CheckBox)popEducateurLinearLayoutEducateur.getChildAt(i);
                if(test.isChecked()){
                    for(Personne p: modelEducateur.getListeEducateurRecherche()){
                        String comp="checkBoxEducateur/"+p.getId();
                        String button=test.getTag().toString();
                        if(comp.equals(button)){
                            EducateurTemp.add(p);
                        }
                    }

                }
            }
            modelEducateur.setListeEducateurSelected(EducateurTemp);
        }
        else if(view.getTag().toString().contains("checkBoxEleve")){
            ArrayList<Personne> EleveTemp= new ArrayList<>();
            for(int i=0; i<popEducateurLinearLayoutEleve.getChildCount();i++){
                CheckBox test = (CheckBox)popEducateurLinearLayoutEleve.getChildAt(i);
                if(test.isChecked()){
                    for(Personne p: modelEducateur.getListeEleveRecherche()){
                        String comp="checkBoxEleve/"+p.getId();
                        String button=test.getTag().toString();
                        if(comp.equals(button)){
                            EleveTemp.add(p);
                        }
                    }

                }
            }
            modelEducateur.setListeEleveSelected(EleveTemp);
        }
    }

    public void setRejouerAPI(int idADM){
        loading.hideLoading();
        modelEducateur.MettreAJourStockage(new Pair<AccueilFragment,Integer>(this,idADM));

    }
    public void resultApiError(){
        loading.hideLoading();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Erreur");
        builder.setMessage("Erreur requet");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


}