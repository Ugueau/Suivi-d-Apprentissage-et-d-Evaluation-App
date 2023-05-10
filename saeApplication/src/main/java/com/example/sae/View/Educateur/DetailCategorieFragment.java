package com.example.sae.View.Educateur;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.sae.API.API;
import com.example.sae.Model.Categorie;
import com.example.sae.Model.Competence;
import com.example.sae.Model.CompetenceTMP;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.Static;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class DetailCategorieFragment extends Fragment implements Observer, View.OnClickListener {

    private final Categorie categorie;
    private final ModelEducateur ModeleCategorieCompetence;
    private Competence CompSelect;
    private CompetenceTMP newComp;
    private Boolean nouvelleCompetence;
    private Boolean modifCompSelect = false;

    private LinearLayout DetailCategorie;
    private EditText competenceRechercheInput;


    ////// popup de recherche competence
    private EditText popCompInput;
    private LinearLayout popEducateurLinearLayoutComp;
    private ConstraintLayout popupRechercheCompetence;


    ////// popup de recherche de categorie
    private ConstraintLayout popupRechercheCategorie;
    private EditText popCatInput;
    private LinearLayout popEducateurLinearLayoutCat;


    ////// popup de creation de competence
    private ConstraintLayout popupNouvelleCompetence;
    private EditText popNewCompNomInput;
    private EditText popNewCompDescriptionInput;
    private LinearLayout popEducateurLinearLayoutNewComp;
    private TextView popChampIncomplet;

    float textSize ;
    Typeface typeface ;


    public DetailCategorieFragment(Categorie c, ModelEducateur m){
        categorie = c;
        ModeleCategorieCompetence = m;
        CompSelect = new Competence(0,"","");
        ModeleCategorieCompetence.addObserver(this);
        categorie.addObserver(this);
        nouvelleCompetence = false;
        newComp = new CompetenceTMP();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_detail_categorie, container, false);


        textSize = getResources().getDimension(R.dimen.TexteNormal) / getResources().getDisplayMetrics().scaledDensity;
        Typeface typeface = ResourcesCompat.getFont(Objects.requireNonNull(getContext()), R.font.noto_sans);


        ////// fragment detailCategorie
        DetailCategorie = rootView.findViewById(R.id.layoutDetailCategorie);
        competenceRechercheInput = rootView.findViewById(R.id.competenceRechercheInput);
        competenceRechercheInput.addTextChangedListener(new TextWatcher() {
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
        Button ajouterCompetence = rootView.findViewById(R.id.AjouterCompetence);
        ajouterCompetence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(popupRechercheCompetence,View.VISIBLE);
                updateRechercheCompetence();
            }
        });

        ////// popup de recherche de competence
        popupRechercheCompetence = rootView.findViewById(R.id.popupRechercheCompetence);
        popCompInput = rootView.findViewById(R.id.popCompInput);
        popCompInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateRechercheCompetence();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        popEducateurLinearLayoutComp = rootView.findViewById(R.id.popEducateurLinearLayoutComp);
        ImageButton popCompButtonFermer = rootView.findViewById(R.id.popCompButtonFermer);
        popCompButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(popupRechercheCompetence,View.GONE);
            }
        });
        Button popCompButtonNewComp = rootView.findViewById(R.id.popCompButtonNewComp);
        popCompButtonNewComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(popupNouvelleCompetence, View.VISIBLE);
                newComp = new CompetenceTMP();
                nouvelleCompetence = true;
                updateNouvelleCompetence();
            }
        });
        Button popCompButtonValider = rootView.findViewById(R.id.popCompButtonValider);
        popCompButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(popupRechercheCompetence,View.GONE);
                API api = new API();
                ArrayList<Integer> listIdComp = new ArrayList<>();
                for(Competence c : categorie.getListCompetence()){
                    listIdComp.add(c.getId());
                }
                api.modifyCategorieCompetence(Menu_Educ.token, categorie.getId(), listIdComp);
            }
        });
        ConstraintLayout backgpopRechercherCompetence = rootView.findViewById(R.id.backgpopRechercherCompetence);
        backgpopRechercherCompetence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(popupRechercheCompetence,View.GONE);
            }
        });



        ////// popup nouvelle competence
        popupNouvelleCompetence = rootView.findViewById(R.id.popupNouvelleCompetence);
        ImageButton popNewCompButtonFermer = rootView.findViewById(R.id.popNewCompButtonFermer);
        popNewCompButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompSelect = new Competence(0,"","");
                Static.setVisibilite(popChampIncomplet,View.GONE);
                Static.setVisibilite(popupNouvelleCompetence, View.GONE);
                nouvelleCompetence = false;
                modifCompSelect = false;
            }
        });
        Button popCompButtonNewCat = rootView.findViewById(R.id.popCompButtonNewCat);
        popCompButtonNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modifCompSelect){
                    CompSelect.desc = popNewCompDescriptionInput.getText().toString();
                    CompSelect.nom = popNewCompNomInput.getText().toString();
                }
                if(nouvelleCompetence){
                    newComp.desc = popNewCompDescriptionInput.getText().toString();
                    newComp.nom = popNewCompNomInput.getText().toString();
                }
                Static.setVisibilite(popupRechercheCategorie, View.VISIBLE);
                updateRechercheCategorie();
            }
        });
        Button popNewCompButtonValider = rootView.findViewById(R.id.popNewCompButtonValider);
        popNewCompButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nouvelleCompetence){
                    if((!popNewCompNomInput.getText().toString().isEmpty()) && (!popNewCompDescriptionInput.getText().toString().isEmpty()) && (popEducateurLinearLayoutNewComp.getChildCount() != 0)){
                        newComp.desc = popNewCompDescriptionInput.getText().toString();
                        newComp.nom = popNewCompNomInput.getText().toString();
                        for(int i = 0; i<popEducateurLinearLayoutNewComp.getChildCount(); i++){
                            for(Categorie cat : ModeleCategorieCompetence.getListCategorie()){
                                if(cat.getId() == popEducateurLinearLayoutNewComp.getChildAt(i).getId()){
                                    newComp.listeCategorie.add(cat);
                                }
                            }
                        }
                        try {
                            newComp.requeteAPI(Menu_Educ.token);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                        Static.setVisibilite(popupNouvelleCompetence, View.GONE);
                        update();
                        nouvelleCompetence = false;
                    }
                    else{
                        Static.setVisibilite(popChampIncomplet,View.VISIBLE);
                    }
                }
                else{
                    if(modifCompSelect){
                        API api = new API();
                        CompSelect.desc = popNewCompDescriptionInput.getText().toString();
                        CompSelect.nom = popNewCompNomInput.getText().toString();

                        api.modifyCompetence(Menu_Educ.token, CompSelect.id, CompSelect.nom, CompSelect.desc);
                        ArrayList<Integer> listCat = new ArrayList<>();
                        for(Categorie cat : CompSelect.listeCategorie){
                            listCat.add(cat.getId());
                        }
                        Log.e("listCat: ", listCat.toString());
                        Log.e("listComp", Integer.toString(CompSelect.getId()));
                        api.modifyCompetenceCategorie(Menu_Educ.token, CompSelect.getId(), listCat);

                        modifCompSelect = false;
                    }
                    Static.setVisibilite(popupNouvelleCompetence, View.GONE);
                }
            }
        });
        popNewCompNomInput = rootView.findViewById(R.id.popNewCompNomInput);
        popNewCompNomInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!nouvelleCompetence){
                    modifCompSelect = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        popNewCompDescriptionInput = rootView.findViewById(R.id.popNewCompDescriptionInput);
        popNewCompDescriptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!nouvelleCompetence){
                    modifCompSelect = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        popEducateurLinearLayoutNewComp = rootView.findViewById(R.id.popEducateurLinearLayoutNewComp);
        popChampIncomplet = rootView.findViewById(R.id.popChampIncomplet);
        ConstraintLayout backgpopNexComp = rootView.findViewById(R.id.backgpopNexComp);
        backgpopNexComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompSelect = new Competence(0,"","");
                Static.setVisibilite(popChampIncomplet,View.GONE);
                Static.setVisibilite(popupNouvelleCompetence, View.GONE);
                nouvelleCompetence = false;
                modifCompSelect = false;
            }
        });



        /////popup recherche categorie
        popupRechercheCategorie = rootView.findViewById(R.id.popupRechercheCategorie);
        ImageButton popCatButtonFermer = rootView.findViewById(R.id.popCatButtonFermer);
        popCatButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(popupRechercheCategorie, View.GONE);
            }
        });
        popCatInput = rootView.findViewById(R.id.popCatInput);
        popCatInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateRechercheCategorie();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        popEducateurLinearLayoutCat = rootView.findViewById(R.id.popEducateurLinearLayoutCat);
        Button popCatButtonValider = rootView.findViewById(R.id.popCatButtonValider);
        popCatButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(popupRechercheCategorie, View.GONE);
                updateNouvelleCompetence();
            }
        });
        ConstraintLayout backgpopRechercherCat = rootView.findViewById(R.id.backgpopRechercherCat);
        backgpopRechercherCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(popupRechercheCategorie, View.GONE);
            }
        });

        update();
        return rootView;
    }

    @Override
    public void update() {
        if(getActivity()==null) return;
        DetailCategorie.removeAllViews();
        ArrayList<Competence> list = categorie.getListCompetence();

        int cptr = 0;

        if(competenceRechercheInput.getText().toString().isEmpty()){
            for(Competence c : list){
                Button resultatCompetence = new Button(getActivity());
                resultatCompetence.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                resultatCompetence.setTypeface(typeface);

                resultatCompetence.setText(c.nom);

                resultatCompetence.setId(cptr);

                resultatCompetence.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CompSelect = list.get(resultatCompetence.getId());
                        Static.setVisibilite(popupNouvelleCompetence, View.VISIBLE);
                        nouvelleCompetence = false;
                        updateNouvelleCompetence();
                    }
                });

                DetailCategorie.addView(resultatCompetence);
                cptr++;
            }
        }
        else{
            String input = competenceRechercheInput.getText().toString();
            for(Competence c : list){

                String nomSubString;
                if(c.nom.length()<input.length()){
                    nomSubString = c.nom;
                }
                else{
                    nomSubString = c.nom.substring(0,input.length());
                }

                if(Compare(input, nomSubString)){
                    Button resultatCompetence = new Button(getActivity());
                    resultatCompetence.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    resultatCompetence.setTypeface(typeface);

                    resultatCompetence.setText(c.nom);

                    resultatCompetence.setId(cptr);

                    resultatCompetence.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CompSelect = list.get(resultatCompetence.getId());
                            Static.setVisibilite(popupNouvelleCompetence, View.VISIBLE);
                            nouvelleCompetence = false;
                            updateNouvelleCompetence();
                        }
                    });
                    DetailCategorie.addView(resultatCompetence);
                }
                cptr++;
            }
        }
        updateRechercheCompetence();
        updateNouvelleCompetence();
        updateRechercheCategorie();
    }

    private boolean Compare(String val1, String val2){

        boolean retour = true;

        val1 = val1.toLowerCase(Locale.ROOT);
        val2 = val2.toLowerCase(Locale.ROOT);
        if(val1.length() != val2.length()){
            return false;
        }
        else{
            for(int i = 0; i<val1.length(); i++){
                if (val1.charAt(i) != val2.charAt(i)) {
                    retour = false;
                }
            }
            return retour;
        }
    }

    private void updateRechercheCompetence(){
        popEducateurLinearLayoutComp.removeAllViews();
        ArrayList<Competence> liste = ModeleCategorieCompetence.getListeCompetenceIME();

        if(popCompInput.getText().toString().isEmpty()){
            for(Competence comp : liste){
                CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setTag("checkBoxCompetence"+comp.getId());
                checkbox.setText(comp.nom);
                checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                checkbox.setTypeface(typeface);
                checkbox.setPadding(10,10,10,10);
                checkbox.setGravity(Gravity.CENTER);
                if(categorie.isIn(comp)){
                    checkbox.setChecked(true);
                }
                checkbox.setOnClickListener(this);
                popEducateurLinearLayoutComp.addView(checkbox);
            }
        }
        else{
            String input = popCompInput.getText().toString();
            String nomSubString;

            for(Competence comp : liste){
                if( comp.nom.length() < input.length() ) {
                    nomSubString = comp.nom;
                }
                else{
                    nomSubString = comp.nom.substring(0,input.length());
                }
                if(Compare(input, nomSubString)){
                    CheckBox checkbox = new CheckBox(getActivity());
                    checkbox.setTag("checkBoxCompetence"+comp.getId());
                    checkbox.setText(comp.nom);
                    checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    checkbox.setTypeface(typeface);
                    checkbox.setPadding(10,10,10,10);
                    checkbox.setGravity(Gravity.CENTER);
                    if(categorie.isIn(comp)){
                        checkbox.setChecked(true);
                    }
                    checkbox.setOnClickListener(this);
                    popEducateurLinearLayoutComp.addView(checkbox);
                }
            }
        }
    }

    private void updateNouvelleCompetence(){
        if(nouvelleCompetence){
            popNewCompNomInput.setText(newComp.nom);
            popNewCompDescriptionInput.setText(newComp.desc);
            popEducateurLinearLayoutNewComp.removeAllViews();
            ArrayList<Categorie> list = ModeleCategorieCompetence.getListCategorie();
            for(Categorie c : list){
                if(newComp.isIn(c)){
                    LinearLayout tableau = new LinearLayout(getActivity());
                    tableau.setOrientation(LinearLayout.HORIZONTAL);
                    tableau.setGravity(Gravity.CENTER);

                    TextView test = new TextView(getActivity());
                    test.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    test.setTypeface(typeface);

                    test.setText(c.getNom());

                    ImageButton butIm = new ImageButton(getActivity());
                    butIm.setImageResource(R.drawable.ic_baseline_closeblack_24);
                    butIm.setBackgroundColor(Color.TRANSPARENT);
                    butIm.setTag("buttonRemoveCategorie"+c.getId());
                    butIm.setOnClickListener(this);

                    tableau.addView(butIm);
                    tableau.addView(test);

                    popEducateurLinearLayoutNewComp.addView(tableau);
                }
            }
        }
        else{
            popNewCompNomInput.setText(CompSelect.nom);
            popNewCompDescriptionInput.setText(CompSelect.desc);
            popEducateurLinearLayoutNewComp.removeAllViews();
            ArrayList<Categorie> list = ModeleCategorieCompetence.getListCategorie();
            for(Categorie c : list){
                if(CompSelect.isIn(c)){
                    LinearLayout tableau = new LinearLayout(getActivity());
                    tableau.setOrientation(LinearLayout.HORIZONTAL);
                    tableau.setGravity(Gravity.CENTER);

                    TextView test = new TextView(getActivity());
                    test.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    test.setTypeface(typeface);

                    test.setText(c.getNom());

                    ImageButton butIm = new ImageButton(getActivity());
                    butIm.setImageResource(R.drawable.ic_baseline_closeblack_24);
                    butIm.setBackgroundColor(Color.TRANSPARENT);
                    butIm.setTag("buttonRemoveCategorie"+c.getId());
                    butIm.setOnClickListener(this);

                    tableau.addView(butIm);
                    tableau.addView(test);

                    popEducateurLinearLayoutNewComp.addView(tableau);
                }
            }
        }
    }

    private void updateRechercheCategorie(){
        popEducateurLinearLayoutCat.removeAllViews();
        ArrayList<Categorie>list = ModeleCategorieCompetence.getListCategorie();
        if(nouvelleCompetence){
            if(popCatInput.getText().toString().isEmpty()){
                for(Categorie c : list){
                    CheckBox checkbox = new CheckBox(getActivity());
                    checkbox.setTag("checkBoxCategorie"+c.getId());
                    checkbox.setText(c.getNom());
                    checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    checkbox.setTypeface(typeface);
                    checkbox.setPadding(10,10,10,10);
                    checkbox.setGravity(Gravity.CENTER);
                    if(newComp.isIn(c)){
                        checkbox.setChecked(true);
                    }
                    checkbox.setOnClickListener(this);
                    popEducateurLinearLayoutCat.addView(checkbox);
                }
            }
            else{
                for(Categorie c : list){
                    String input = popCatInput.getText().toString();
                    String nomCatSubString;
                    if( c.getNom().length() < input.length() ) {
                        nomCatSubString = c.getNom();
                    }
                    else{
                        nomCatSubString = c.getNom().substring(0,input.length());
                    }
                    if(Compare(input, nomCatSubString)){
                        CheckBox checkbox = new CheckBox(getActivity());
                        checkbox.setTag("checkBoxCategorie"+c.getId());
                        checkbox.setText(c.getNom());
                        checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                        checkbox.setTypeface(typeface);
                        checkbox.setPadding(10,10,10,10);
                        checkbox.setGravity(Gravity.CENTER);
                        if(newComp.isIn(c)){
                            checkbox.setChecked(true);
                        }
                        checkbox.setOnClickListener(this);
                        popEducateurLinearLayoutCat.addView(checkbox);
                    }
                }
            }
        }
        else{
            if(popCatInput.getText().toString().isEmpty()){
                for(Categorie c : list){
                    CheckBox checkbox = new CheckBox(getActivity());
                    checkbox.setTag("checkBoxCategorie"+c.getId());
                    checkbox.setText(c.getNom());
                    checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    checkbox.setTypeface(typeface);
                    checkbox.setPadding(10,10,10,10);
                    checkbox.setGravity(Gravity.CENTER);
                    if(CompSelect.isIn(c)){
                        checkbox.setChecked(true);
                    }
                    checkbox.setOnClickListener(this);
                    popEducateurLinearLayoutCat.addView(checkbox);
                }
            }
            else{
                for(Categorie c : list){
                    String input = popCatInput.getText().toString();
                    String nomCatSubString;
                    if( c.getNom().length() < input.length() ) {
                        nomCatSubString = c.getNom();
                    }
                    else{
                        nomCatSubString = c.getNom().substring(0,input.length());
                    }
                    if(Compare(input, nomCatSubString)){
                        CheckBox checkbox = new CheckBox(getActivity());
                        checkbox.setTag("checkBoxCategorie"+c.getId());
                        checkbox.setText(c.getNom());
                        checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                        checkbox.setTypeface(typeface);
                        checkbox.setPadding(10,10,10,10);
                        checkbox.setGravity(Gravity.CENTER);
                        if(CompSelect.isIn(c)){
                            checkbox.setChecked(true);
                        }
                        checkbox.setOnClickListener(this);
                        popEducateurLinearLayoutCat.addView(checkbox);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().toString().contains("checkBoxCompetence")){
            CheckBox tmp = (CheckBox)view;
            ArrayList<Competence> list = ModeleCategorieCompetence.getListeCompetenceIME();
            if(tmp.isChecked()){
                for(Competence c : list){
                    if(tmp.getTag().toString().substring(18).equals(Integer.toString(c.getId()))){
                        Log.e("onClick: ", "test");
                        categorie.addCompetence(c);
                    }
                }
            }
            else{
                for(Competence c : list){
                    if(tmp.getTag().toString().substring(18).equals(Integer.toString(c.getId()))){
                        if(c.listeCategorie.size() > 1){
                            categorie.removeCompetence(c);
                            Log.e("onClick: ", "test");
                        }
                        else{
                            tmp.setChecked(true);
                            Log.e("onClick: ", "test");
                        }
                    }
                }
            }
        }
        else if(view.getTag().toString().contains("checkBoxCategorie")){
            if(nouvelleCompetence){
                CheckBox tmp = (CheckBox)view;
                ArrayList<Categorie> list = ModeleCategorieCompetence.getListCategorie();
                if(tmp.isChecked()){
                    for(Categorie c : list){
                        if(tmp.getTag().toString().substring(17).equals(Integer.toString(c.getId()))){
                            newComp.listeCategorie.add(c);
                            updateNouvelleCompetence();
                        }
                    }
                }
                else{
                    for(Categorie c : list){
                        if(tmp.getTag().toString().substring(17).equals(Integer.toString(c.getId()))){
                            newComp.listeCategorie.remove(c);
                            updateNouvelleCompetence();
                        }
                    }
                }
            }
            else{
                CheckBox tmp = (CheckBox)view;
                ArrayList<Categorie> list = ModeleCategorieCompetence.getListCategorie();
                if(tmp.isChecked()){
                    for(Categorie c : list){
                        if(tmp.getTag().toString().substring(17).equals(Integer.toString(c.getId()))){
                            c.addCompetence(CompSelect);
                        }
                    }
                }
                else{
                    for(Categorie c : list){
                        if(tmp.getTag().toString().substring(17).equals(Integer.toString(c.getId()))){
                            CompSelect.removeCategorie(c);
                            ModeleCategorieCompetence.notifyObservers();
                        }
                    }
                }
            }
        }
        else if(view.getTag().toString().contains("buttonRemoveCategorie")){
            if(nouvelleCompetence){
                ImageButton tmp = (ImageButton)view;
                ArrayList<Categorie> list = newComp.listeCategorie;
                Categorie toRemove = new Categorie();
                for(Categorie c : list){
                    if(tmp.getTag().toString().substring(21).equals(Integer.toString(c.getId()))){
                        toRemove = c;
                    }
                }
                newComp.listeCategorie.remove(toRemove);
                ModeleCategorieCompetence.notifyObservers();
            }
            else{
                ImageButton tmp = (ImageButton)view;
                ArrayList<Categorie> list = CompSelect.listeCategorie;
                Categorie toRemove = new Categorie();
                for(Categorie c : list){
                    if(tmp.getTag().toString().substring(21).equals(Integer.toString(c.getId()))){
                        toRemove = c;
                    }
                }
                CompSelect.removeCategorie(toRemove);
                ModeleCategorieCompetence.notifyObservers();
            }
        }
    }
}
