package com.example.sae.View.Educateur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.TextView;

import com.example.sae.API.API;
import com.example.sae.API.Loading;
import com.example.sae.Model.Activite;
import com.example.sae.Model.Categorie;
import com.example.sae.Model.Competence;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Model.MultiMap;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.Static;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;


public class ModifyActivite extends Fragment implements View.OnClickListener,Observer {

    private InputMethodManager inputMethodManager;

    float textSize ;
    Typeface typeface ;
    Loading loading;

    private Activite activite;
    private ActiviteFragment activiteFragment;
    private ModelEducateur modelEducateur;

    private TextInputEditText modifyActivityInputName;
    private TextInputEditText modifyActivityInputDesc;
    private Button modifyActivityButtonAddComp;
    private ImageButton modifyActivityImageButtonValidate;
    private LinearLayout modifyActivityLayoutVerticalComp;

    //debut PopUp Recherche Comp

    private ConstraintLayout PopCompRecherche;
    private ConstraintLayout backgpopRechercherCompetence;
    private Button popCompButtonValider;
    private ImageButton popCompButtonFermer;
    private Button popCompButtonNewComp;
    private LinearLayout popEducateurLinearLayoutComp;
    private TextInputEditText popCompInput;


    //fin PopUp Recherche Comp
    //debut PopUp new Comp

    private ConstraintLayout PopCompNew;
    private ConstraintLayout backgpopNexComp;
    private ImageButton popNewCompButtonFermer;
    private Button popNewCompButtonValider;
    private Button popCompButtonNewCat;
    private LinearLayout popEducateurLinearLayoutNewComp;
    private TextInputEditText popNewCompNomInput;
    private TextInputEditText popNewCompDescriptionInput;

    //fin PopUp new Comp

    public ModifyActivite(Activite activite,ModelEducateur modelEducateur,ActiviteFragment activiteFragment) {
        this.activite=activite;
        this.modelEducateur=modelEducateur;
        this.activiteFragment=activiteFragment;
        modelEducateur.addObserver(this);
    }
    public ModifyActivite(ActiviteFragment activiteFragment, ModelEducateur modelEducateur) {
        this.activite= new Activite();
        this.activiteFragment=activiteFragment;
        this.modelEducateur=modelEducateur;
        modelEducateur.addObserver(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_modify_activite, container, false);

        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        loading =new Loading(getContext());

        textSize = getResources().getDimension(R.dimen.TexteNormal) / getResources().getDisplayMetrics().scaledDensity;
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.noto_sans);

        modifyActivityInputName = rootView.findViewById(R.id.modifyActivityInputName);
        modifyActivityInputDesc= rootView.findViewById(R.id.modifyActivityInputDesc);
        modifyActivityButtonAddComp= rootView.findViewById(R.id.modifyActivityButtonAddComp);
        modifyActivityImageButtonValidate= rootView.findViewById(R.id.modifyActivityImageButtonValidate);
        modifyActivityLayoutVerticalComp= rootView.findViewById(R.id.modifyActivityLayoutVerticalComp);


        //debut PopUp Recherche Comp

        PopCompRecherche = rootView.findViewById(R.id.PopCompRecherche);
        backgpopRechercherCompetence = rootView.findViewById(R.id.backgpopRechercherCompetence);
        popCompButtonValider = rootView.findViewById(R.id.popCompButtonValider);
        popCompButtonFermer = rootView.findViewById(R.id.popCompButtonFermer);
        popCompButtonNewComp = rootView.findViewById(R.id.popCompButtonNewComp);
        popEducateurLinearLayoutComp = rootView.findViewById(R.id.popEducateurLinearLayoutComp);
        popCompInput = rootView.findViewById(R.id.popCompInput);

        //fin PopUp Recherche Comp
        //debut PopUp new Comp

        PopCompNew = rootView.findViewById(R.id.PopCompNew);
        backgpopNexComp = rootView.findViewById(R.id.backgpopNexComp);
        popNewCompButtonFermer = rootView.findViewById(R.id.popNewCompButtonFermer);
        popNewCompButtonValider = rootView.findViewById(R.id.popNewCompButtonValider);
        popCompButtonNewCat = rootView.findViewById(R.id.popCompButtonNewCat);
        popEducateurLinearLayoutNewComp = rootView.findViewById(R.id.popEducateurLinearLayoutNewComp);
        popNewCompNomInput = rootView.findViewById(R.id.popNewCompNomInput);
        popNewCompDescriptionInput = rootView.findViewById(R.id.popNewCompDescriptionInput);

        popCompButtonNewCat.setVisibility(View.GONE);

        //fin PopUp new Comp

        modifyActivityButtonAddComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopCompRecherche, View.VISIBLE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        backgpopRechercherCompetence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopUp(view, PopCompRecherche);
            }
        });
        popCompButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Competence> competences = new ArrayList<>();
                for (int i = 0; i < popEducateurLinearLayoutComp.getChildCount(); i++) {
                    if(popEducateurLinearLayoutComp.getChildAt(i) instanceof CheckBox){
                        CheckBox checkBox = (CheckBox) popEducateurLinearLayoutComp.getChildAt(i);
                        if(checkBox.isChecked()){
                            for (Competence competence : modelEducateur.getListeCompetenceIME()){
                                String comp = "checkBoxComp"+competence.getId();
                                String check = checkBox.getTag().toString();
                                if(comp.equals(check)){
                                    Log.e("TAG", "ALED");
                                    competences.add(competence);
                                }
                            }
                        }
                    }
                }
                activite.fillCompetence(competences);
                updateCompActivite();

                closePopUp(view, PopCompRecherche);
            }
        });
        popCompButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopUp(view, PopCompRecherche);
            }
        });





        popCompButtonNewComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopCompNew,View.VISIBLE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        backgpopNexComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopUp(view, PopCompNew);
            }
        });
        popNewCompButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopUp(view, PopCompNew);
            }
        });
        popNewCompButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                JSONArray jsonArrayListidCatCheck = new JSONArray();

                for (int i = 0; i < popEducateurLinearLayoutNewComp.getChildCount(); i++) {
                    if(popEducateurLinearLayoutNewComp.getChildAt(i) instanceof CheckBox){
                        CheckBox checkBox = (CheckBox) popEducateurLinearLayoutNewComp.getChildAt(i);
                        if(checkBox.isChecked()){
                            for (Categorie categorie : modelEducateur.getListCategorie()){
                                String cat = "checkBoxCat"+categorie.getId();
                                String check = checkBox.getTag().toString();
                                if(check.equals(cat)){
                                    jsonArrayListidCatCheck.put(categorie.getId());
                                }
                            }
                        }
                    }
                }
                if(popNewCompNomInput.getText().toString()!="" && popNewCompDescriptionInput.getText().toString()!="" && jsonArrayListidCatCheck.length()!=0){
                    loading.showLoading();
                    API api = new API();
                    api.querryNewCompetenceActivite(Menu_Educ.token,popNewCompNomInput.getText().toString(),popNewCompDescriptionInput.getText().toString(),jsonArrayListidCatCheck,ModifyActivite.this);
                    closePopUp(view, PopCompNew);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Erreur");
                    builder.setMessage("Les champs ne sont pas remplis, il faut au moins une catégorie.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            }
        });









        modifyActivityImageButtonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modifyActivityInputDesc.getText().toString()!="" && modifyActivityInputName.getText().toString()!=""&&activite.getCompetences().size()!=0){
                    if(activite.getId()!=-1){
                        activite.setDesc(modifyActivityInputDesc.getText().toString());
                        activite.setNom(modifyActivityInputName.getText().toString());
                        loading.showLoading();
                        API api = new API();
                        api.ModifyActivite(Menu_Educ.token,activite,ModifyActivite.this);
                    }
                    else {
                        loading.showLoading();

                        JSONArray jsonArray = new JSONArray();
                        for(Competence c : activite.getCompetences()){
                            jsonArray.put(c.getId());
                        }

                        API api = new API();
                        api.NewActivite(Menu_Educ.token,modifyActivityInputName.getText().toString(),modifyActivityInputDesc.getText().toString(),modelEducateur.getPersonne().getIdIMESelected(),jsonArray,ModifyActivite.this);
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Erreur");
                    builder.setMessage("Les champs ne sont pas remplis, il faut au moins une compétence, un nom et une description.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            }
        });
        popCompInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateComp(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });






        if(activite.getId()!=-1){
            modifyActivityInputName.setText(activite.getNom());
            modifyActivityInputDesc.setText(activite.getDesc());
            API api = new API();
            loading.showLoading();
            api.getCompetanceActivite(Menu_Educ.token,activite.getId(),activite,this);
        }
        else{
            update();
        }



        return rootView;
    }

    private void closePopUp(View view, ConstraintLayout constraintLayout){
        Static.setVisibilite(constraintLayout,View.GONE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        popNewCompNomInput.setText("");
        popNewCompDescriptionInput.setText("");

        for (int i = 0; i < popEducateurLinearLayoutNewComp.getChildCount(); i++) {
            if(popEducateurLinearLayoutNewComp.getChildAt(i) instanceof CheckBox){
                CheckBox checkBox = (CheckBox) popEducateurLinearLayoutNewComp.getChildAt(i);
                checkBox.setChecked(false);
            }
        }
    }

    @Override
    public void update(){
        if(getActivity()==null) return;

        updateComp(popCompInput.getText().toString());

        popEducateurLinearLayoutNewComp.removeAllViews();
        for (Categorie categorie : modelEducateur.getListCategorie()){
            CheckBox checkbox = new CheckBox(getActivity());
            checkbox.setTag("checkBoxCat"+categorie.getId());
            checkbox.setText(categorie.getNom());
            checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            checkbox.setTypeface(typeface);
            checkbox.setPadding(10,10,10,10);
            checkbox.setGravity(Gravity.CENTER);

            popEducateurLinearLayoutNewComp.addView(checkbox);
        }

    }

    private void updateComp(String recherches){
        popEducateurLinearLayoutComp.removeAllViews();
        MultiMap<Integer,Competence> map = rechercherComp(recherches);
        for (Integer i: map.keySet())
        {
            for(Competence p : map.get(i)){
                CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setTag("checkBoxComp"+p.getId());
                checkbox.setText(p.nom);
                checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                checkbox.setTypeface(typeface);
                checkbox.setPadding(10,10,10,10);
                checkbox.setGravity(Gravity.CENTER);


                for(Competence select : activite.getCompetences()){
                    if(p.getId()==select.getId()){
                        checkbox.setChecked(true);
                    }
                }

                checkbox.setOnClickListener(this);
                popEducateurLinearLayoutComp.addView(checkbox);
            }
        }
    }

    private MultiMap<Integer,Competence> rechercherComp(String recherches){
        ArrayList<Competence> all = modelEducateur.getListeCompetenceIME();

        MultiMap<Integer,Competence> map = new MultiMap();
        for(Competence p: all) {
            map.put(Static.recherche(recherches,p.nom),p);
        }
        return map;

    }
    private void updateCompActivite(){
        modifyActivityLayoutVerticalComp.removeAllViews();
        for (Competence competence : activite.getCompetences()){
            LinearLayout tableau = new LinearLayout(getActivity());
            tableau.setOrientation(LinearLayout.HORIZONTAL);
            tableau.setGravity(Gravity.CENTER);

            TextView test = new TextView(getActivity());
            test.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            test.setTypeface(typeface);
            test.setText(competence.nom);

            ImageButton butIm = new ImageButton(getActivity());
            butIm.setImageResource(R.drawable.ic_baseline_closeblack_24);
            butIm.setBackgroundColor(Color.TRANSPARENT);
            butIm.setTag("buttonRemoveComp"+competence.id);
            butIm.setOnClickListener(this);

            tableau.addView(butIm);
            tableau.addView(test);

            modifyActivityLayoutVerticalComp.addView(tableau);
        }
        update();
    }

    public void resultApiCompt(){
        updateCompActivite();
        loading.hideLoading();
    }

    public void resultatApi(int idNewComp){
        loading.hideLoading();
        Pair<AccueilFragment,Integer> pair = new Pair<>(null,null);
        modelEducateur.MettreAJourStockage(pair);
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

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout_Nav,fragment);
        fragmentTransaction.commit();
    }

    public void resultatModifyActivityAPI(){
        loading.hideLoading();
        replaceFragment(activiteFragment);
    }
    public void resultatNewActivityAPI(){
        loading.hideLoading();
        Pair<AccueilFragment,Integer> pair = new Pair<>(null,null);
        modelEducateur.MettreAJourStockage(pair);
        replaceFragment(activiteFragment);
    }

    @Override
    public void onClick(View view) {
        Log.e("TAG", "onClick: " );
        if(view.getTag().toString().indexOf("buttonRemoveComp")!=-1){
            Log.e("TAG", "onClick: " );
            Iterator<Competence> iterator = activite.getCompetences().iterator();
            while (iterator.hasNext()) {
                Competence competence = iterator.next();
                String comp = "buttonRemoveComp"+competence.getId();
                String croix = view.getTag().toString();
                if(croix.equals(comp)){
                    iterator.remove();
                }
            }
            resultApiCompt();
        }
    }
}