package com.example.sae.View.Educateur;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sae.API.API;
import com.example.sae.API.Loading;
import com.example.sae.Model.ActiviteDuMoment;
import com.example.sae.Model.Competence;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Model.MultiMap;
import com.example.sae.Model.Personne;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.Static;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


public class DuMoment extends Fragment implements Observer, View.OnClickListener,SeekBar.OnSeekBarChangeListener {
    ActiviteDuMoment activiteDuMoment;

    private final ModelEducateur modelEducateur;

    private InputMethodManager imm;

    private TextView txtDuMomentActiviteName;
    private LinearLayout layoutVerticalDuMomentEleve;

    private ConstraintLayout PopEleveDetail;
    private TextView txtPopNomEleve;
    private LinearLayout popLinearLayoutDetailEleve;
    private final ArrayList<TextView> resultat = new ArrayList<>();
    private final ArrayList<CheckBox> nonNoters = new ArrayList<>();
    private final ArrayList<SeekBar> seekBars = new ArrayList<>();

    private TextInputEditText popEleveInput;
    private ConstraintLayout PopEleveRecherche;
    private LinearLayout popEducateurLinearLayoutEleve;

    private ConstraintLayout PopCompRecherche;
    private LinearLayout popEducateurLinearLayoutComp;
    private TextInputEditText popCompInput;


    private final AccueilFragment accueilFragment;


    private final Loading loading;



    public DuMoment(ActiviteDuMoment activiteDuMoment, ModelEducateur modelEducateur, AccueilFragment accueilFragment,Context context) {
        this.activiteDuMoment =activiteDuMoment;
        activiteDuMoment.addObserver(this);
        this.modelEducateur = modelEducateur;
        this.accueilFragment=accueilFragment;
        loading = new Loading(context);

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

        activiteDuMoment.fillActiviteDuMoment(getContext());


        View rootView = inflater.inflate(R.layout.fragment_du_moment, container, false);

        txtDuMomentActiviteName = rootView.findViewById(R.id.txtDuMomentActiviteName);
        layoutVerticalDuMomentEleve = rootView.findViewById(R.id.layoutVerticalDuMomentEleve);
        Button buttonDuMomentEnregistrer = rootView.findViewById(R.id.buttonDuMomentEnregistrer);

        PopEleveDetail = rootView.findViewById(R.id.PopEleveDetail);
        txtPopNomEleve= rootView.findViewById(R.id.txtPopNomEleve);
        ImageButton popDetailEleveButtonFermer = rootView.findViewById(R.id.popDetailEleveButtonFermer);
        Button popDetailEleveButtonValider = rootView.findViewById(R.id.popDetailEleveButtonValider);
        popLinearLayoutDetailEleve= rootView.findViewById(R.id.popLinearLayoutDetailEleve);
        ConstraintLayout backgpopDetailEleve = rootView.findViewById(R.id.backgpopDetailEleve);

        ImageButton imageButtonDuMomentAddEleve = rootView.findViewById(R.id.imageButtonDuMomentAddEleve);
        ImageButton popEleveButtonFermer = rootView.findViewById(R.id.popEleveButtonFermer);
        popEleveInput = rootView.findViewById(R.id.popEleveInput);
        PopEleveRecherche= rootView.findViewById(R.id.PopEleveRecherche);
        popEducateurLinearLayoutEleve = rootView.findViewById(R.id.popEducateurLinearLayoutEleve);
        Button popEleveButtonValider = rootView.findViewById(R.id.popEleveButtonValider);
        ConstraintLayout backgpopRechercherEleve = rootView.findViewById(R.id.backgpopRechercherEleve);

        ImageButton imageButtonDuMomentAddComp = rootView.findViewById(R.id.imageButtonDuMomentAddComp);
        PopCompRecherche= rootView.findViewById(R.id.PopCompRecherche);
        popEducateurLinearLayoutComp= rootView.findViewById(R.id.popEducateurLinearLayoutComp);
        popCompInput= rootView.findViewById(R.id.popCompInput);
        ImageButton popCompButtonFermer = rootView.findViewById(R.id.popCompButtonFermer);
        ConstraintLayout backgpopRechercherCompetence = rootView.findViewById(R.id.backgpopRechercherCompetence);
        Button popCompButtonNewComp = rootView.findViewById(R.id.popCompButtonNewComp);
        Button popCompButtonValider = rootView.findViewById(R.id.popCompButtonValider);

        popCompButtonNewComp.setVisibility(View.GONE);

        imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);

        buttonDuMomentEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //api de modif des resultat;
                API api = new API();

                loading.showLoading();

                api.setResultat(Menu_Educ.token,activiteDuMoment.getId(),DuMoment.this,activiteDuMoment);

            }
        });
        popDetailEleveButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveDetail, View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popDetailEleveButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveDetail, View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        imageButtonDuMomentAddEleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Static.setVisibilite(PopEleveRecherche, View.VISIBLE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popEleveButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveRecherche, View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popEleveButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveRecherche, View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
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
        backgpopRechercherEleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveRecherche, View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        backgpopDetailEleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopEleveDetail, View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });


        imageButtonDuMomentAddComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopCompRecherche,View.VISIBLE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popCompInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateCompRechercher(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        popCompButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.showLoading();

                ArrayList<Competence> oldCompetencesChecked = activiteDuMoment.getCompetences();
                ArrayList<Integer> competencesChecked = new ArrayList<>();

                boolean change =false;

                for (int i = 0; i < popEducateurLinearLayoutComp.getChildCount(); i++) {
                    if(popEducateurLinearLayoutComp.getChildAt(i) instanceof CheckBox){

                        CheckBox checkBox = (CheckBox) popEducateurLinearLayoutComp.getChildAt(i);
                        if(checkBox.isChecked()){

                            for(Competence competence: modelEducateur.getListeCompetenceIME()){
                                    String comp="checkBoxCompetence"+competence.getId();
                                    String button=checkBox.getTag().toString();
                                    if(comp.equals(button)){
                                        competencesChecked.add(competence.getId());
                                        boolean existe =false;
                                        for (Competence Test : oldCompetencesChecked){

                                            if (Test.getId() == competence.getId()) {
                                                existe = true;
                                                break;
                                            }
                                        }
                                        if(!existe){
                                            activiteDuMoment.addComp(competence);
                                            change=true;
                                        }

                                    }

                            }
                        }
                        else{
                            Iterator<Competence> iter = oldCompetencesChecked.iterator();
                            while (iter.hasNext()) {
                                Competence competence2 = iter.next();
                                String comp = "checkBoxCompetence" + competence2.getId();
                                String button = checkBox.getTag().toString();
                                if (comp.equals(button)) {
                                    if(!competencesChecked.contains(competence2.getId())){
                                        iter.remove();
                                        activiteDuMoment.removeComp(competence2);
                                        change=true;
                                    }
                                }
                            }
                        }
                    }
                }

                if(change){
                    API api = new API();
                    api.modifyCompActiviteDuMoment(Menu_Educ.token, activiteDuMoment.getId(), competencesChecked,DuMoment.this);
                }
                else{
                    endResultCompApi();
                }



                Static.setVisibilite(PopCompRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        popCompButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopCompRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        backgpopRechercherCompetence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopCompRecherche,View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        update();
        return rootView;
    }

    public void endResultCompApi(){
        loading.hideLoading();
    }

    public void endSetResultatApi(){
        loading.hideLoading();
        replaceFragment(accueilFragment);
    }

    @Override
    public void update() {

        if(getActivity()==null)return;

        txtDuMomentActiviteName.setText(activiteDuMoment.getActivite().getNom());



        //liste des eleve
        layoutVerticalDuMomentEleve.removeAllViews();
        for(Personne personne:activiteDuMoment.getListeEleve()){
            LinearLayout tableau = new LinearLayout(getActivity());
            tableau.setOrientation(LinearLayout.HORIZONTAL);
            tableau.setGravity(Gravity.CENTER);

            Button test = new Button(getActivity());
            test.setTextSize(20);
            test.setText(personne.getPrenomNomNum());
            test.setTag("buttonVoirDetailEleve"+personne.getId());
            test.setOnClickListener(this);

            ImageButton butIm = new ImageButton(getActivity());
            butIm.setImageResource(R.drawable.ic_baseline_closeblack_24);
            butIm.setBackgroundColor(Color.TRANSPARENT);
            butIm.setTag("buttonRemoveEleve"+personne.getId());
            butIm.setOnClickListener(this);

            tableau.addView(butIm);
            tableau.addView(test);

            layoutVerticalDuMomentEleve.addView(tableau);
        }



        //recherche eleve
        updateEleveRechercher(popEleveInput.getText().toString());
        updateCompRechercher(popCompInput.getText().toString());

    }
    private MultiMap<Integer,Personne> rechercherEleve(String recherches){
        ArrayList<Personne> all = modelEducateur.getListeEleveRecherche();

        MultiMap<Integer,Personne> map = new MultiMap();
        for(Personne p: all) {
            map.put(Static.recherche(recherches,p.getRechercher()),p);
        }
        return map;

    }
    private MultiMap<Integer,Competence> rechercherComp(String recherches){
        ArrayList<Competence> all = modelEducateur.getListeCompetenceIME();

        MultiMap<Integer,Competence> map = new MultiMap();
        for(Competence p: all) {
            map.put(Static.recherche(recherches,p.nom),p);
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
                checkbox.setTag("checkBoxEleve"+p.getId());
                checkbox.setText(p.getPrenomNomNum());
                checkbox.setTextSize(20);
                checkbox.setPadding(10,10,10,10);
                checkbox.setGravity(Gravity.CENTER);

                for(Personne select : activiteDuMoment.getListeEleve()){
                    if(p.getId()==select.getId()){
                        checkbox.setChecked(true);
                    }
                }
                checkbox.setOnClickListener(this);
                popEducateurLinearLayoutEleve.addView(checkbox);
            }
        }
    }

    private void updateCompRechercher(String recherches){
        popEducateurLinearLayoutComp.removeAllViews();

        MultiMap<Integer,Competence> map = rechercherComp(recherches);
        for (Integer i: map.keySet())
        {
            for(Competence p : map.get(i)){
                CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setTag("checkBoxCompetence"+p.getId());
                checkbox.setText(p.nom);
                checkbox.setTextSize(20);
                checkbox.setPadding(10,10,10,10);
                checkbox.setGravity(Gravity.CENTER);

                for(Competence select : activiteDuMoment.getCompetences()){
                    if(p.getId()==select.getId()){
                        checkbox.setChecked(true);
                    }
                }
                checkbox.setOnClickListener(this);
                popEducateurLinearLayoutComp.addView(checkbox);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().toString().contains("buttonRemoveEleve")){
            for(Personne personne: activiteDuMoment.getListeEleve()){
                String comp="buttonRemoveEleve"+personne.getId();
                String button=view.getTag().toString();
                if(comp.equals(button)){
                    activiteDuMoment.removeEleve(personne);
                    return;
                }
            }
        }
        else if(view.getTag().toString().contains("checkBoxEleve")){

            //ArrayList<Personne> EleveTemp= new ArrayList<>();
            for(int i=0; i<popEducateurLinearLayoutEleve.getChildCount();i++){
                CheckBox test = (CheckBox)popEducateurLinearLayoutEleve.getChildAt(i);
                if(test.isChecked()){
                    for(Personne personne: modelEducateur.getListeEleveRecherche()){
                        if (!activiteDuMoment.getListeEleve().contains(personne)){
                            String comp="checkBoxEleve"+personne.getId();
                            String button=test.getTag().toString();
                            if(comp.equals(button)){
                                activiteDuMoment.addEleve(personne);
                            }
                        }
                    }

                }
            }
        }
        else if(view.getTag().toString().contains("buttonVoirDetailEleve")){
            resultat.clear();
            for(Personne personne: activiteDuMoment.getListeEleve()){
                String comp="buttonVoirDetailEleve"+personne.getId();
                String button=view.getTag().toString();
                if(comp.equals(button)){
                    popLinearLayoutDetailEleve.removeAllViews();
                    txtPopNomEleve.setText(personne.getPrenomNomNum());
                    for(Competence competence:activiteDuMoment.getCompetences()){
                        LinearLayout layout = new LinearLayout(getActivity());
                        layout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout Border = new LinearLayout(getActivity());
                        layout.setOrientation(LinearLayout.VERTICAL);

                        TextView nameComp = new TextView(getActivity());
                        nameComp.setText(competence.nom);
                        nameComp.setGravity(Gravity.CENTER);

                        layout.addView(nameComp);

                        LinearLayout seekBarLayout = new LinearLayout(getActivity());
                        seekBarLayout.setOrientation(LinearLayout.HORIZONTAL);




                        SeekBar seekBar = new SeekBar(getActivity());
                        seekBar.setTag("SeekBar/"+personne.getId()+"/"+competence.id);
                        seekBar.setPadding(10,10,10,25);
                        seekBar.setMax(100);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            seekBar.setMin(0);
                        }
                        int resultat = activiteDuMoment.getResultat(personne.getId(),competence.id);
                        if(resultat==-1){
                            seekBar.setProgress(0);
                        }
                        else{
                            seekBar.setProgress(resultat);
                        }
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        seekBar.setLayoutParams(lp);
                        seekBar.setOnSeekBarChangeListener(this);
                        seekBars.add(seekBar);

                        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT);
                        TextView note = new TextView(getActivity());
                        note.setText(String.valueOf(seekBar.getProgress()));
                        note.setGravity(Gravity.CENTER);
                        note.setLayoutParams(lp2);
                        note.setTag("SeekBar/"+personne.getId()+"/"+competence.id);
                        this.resultat.add(note);

                        seekBarLayout.setPadding(10,25,10,25);
                        seekBarLayout.addView(note);
                        seekBarLayout.addView(seekBar);
                        layout.addView(seekBarLayout);

                        CheckBox nonNoter = new CheckBox(getActivity());
                        nonNoter.setText(getString(R.string.nonNoté));
                        nonNoter.setTag("nonNoter/"+personne.getId()+"/"+competence.id);
                        if(resultat==-1){
                            nonNoter.setChecked(true);
                        }
                        nonNoter.setOnClickListener(this);
                        nonNoters.add(nonNoter);
                        layout.addView(nonNoter);

                        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layout.setLayoutParams(lp4);
                        layout.setBackgroundColor(Color.WHITE);

                        Border.addView(layout);
                        Border.setPadding(5,5,5,5);
                        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp3.setMargins(0,10,0,10);
                        Border.setLayoutParams(lp3);
                        Border.setBackgroundColor(Color.BLACK);
                        popLinearLayoutDetailEleve.addView(Border);


                    }
                    Static.setVisibilite(PopEleveDetail, View.VISIBLE);
                    return;
                }
            }
        }
        else if(view.getTag().toString().contains("nonNoter")){
            CheckBox check = (CheckBox) view;

            String s[] = check.getTag().toString().split("/");
            int idPersonne=Integer.parseInt(s[1]);
            int idCompetence = Integer.parseInt(s[2]);
            activiteDuMoment.setNoteResultat(idPersonne,idCompetence,-1);
            for(SeekBar sk:seekBars){

                String TAGrechercher="SeekBar/"+idPersonne+"/"+idCompetence;
                String TAGCheck=sk.getTag().toString();
                if(TAGCheck.equals(TAGrechercher)){
                    if(check.isChecked()){
                        sk.setEnabled(false);
                    }
                    else {
                        sk.setEnabled(true);
                    }
                }
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        i=i/10;
        i=i*10;
        seekBar.setProgress(i);
        for(TextView txt : resultat){
            String TAGSeekBar=seekBar.getTag().toString();
            String TAGTextView=txt.getTag().toString();
            if(TAGSeekBar.equals(TAGTextView)){
                txt.setText(String.valueOf(i));
                Log.e("TAG", TAGSeekBar );
                String s[] = TAGSeekBar.split("/");
                int idPersonne=Integer.parseInt(s[1]);
                int idCompetence = Integer.parseInt(s[2]);
                activiteDuMoment.changeResultat(idPersonne,idCompetence,i);
                //descheck quand on bouge le seekbar
                for(CheckBox ch : nonNoters){
                    String TAGrechercher="nonNoter/"+idPersonne+"/"+idCompetence;
                    String TAGCheck=ch.getTag().toString();
                    if(TAGCheck.equals(TAGrechercher)){
                        ch.setChecked(false);
                    }
                }

            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}