package com.example.sae.View.Educateur;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sae.API.API;
import com.example.sae.API.Loading;
import com.example.sae.Model.ActiviteDuMoment;
import com.example.sae.Model.Competence;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Model.Personne;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.Static;
import com.example.sae.adapter.HistoriqueAdapter;
import com.example.sae.adapter.HistoriqueAdapterEleve;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class EleveDetail extends Fragment implements Observer {

    float textSize ;
    Typeface typeface ;

    private ArrayList<ActiviteDuMoment> activiteDuMoments = new ArrayList<>();
    private ActiviteDuMoment activiteDuMomentSelect =null;

    private Loading loading;

    private final Personne personne;
    private final ModelEducateur modelEducateur;

    private String dateDebut= null;
    private String dateFin= null;

    private Button editTextDateDebut;
    private Button editTextDateFin;
    private TextView textViewNomPrenomEleve;


    private ListView lv_historique_eleve;


    private ConstraintLayout PopDetailADM;
    private TextView TexteViewPopDetailADMNomComp;
    private TextView TexteViewPopDetailADMDescComp;
    private LinearLayout popDetailADMLinearLayout;


    public EleveDetail(Personne personne, ModelEducateur modelEducateur) {
        this.personne=personne;
        this.modelEducateur=modelEducateur;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eleve_detail, container, false);

        loading = new Loading(getContext());

        textSize = getResources().getDimension(R.dimen.TexteNormal) / getResources().getDisplayMetrics().scaledDensity;
        Typeface typeface = ResourcesCompat.getFont(Objects.requireNonNull(getContext()), R.font.noto_sans);

        editTextDateDebut = rootView.findViewById(R.id.editTextDateDebut);
        editTextDateFin = rootView.findViewById(R.id.editTextDateFin);
        textViewNomPrenomEleve = rootView.findViewById(R.id.textViewNomPrenomEleve);
        lv_historique_eleve = rootView.findViewById(R.id.lv_historique_eleve);




        PopDetailADM = rootView.findViewById(R.id.PopDetailADM);
        Button popDetailADMButtonValider = rootView.findViewById(R.id.popDetailADMButtonValider);
        ImageButton popDetailADMButtonFermer = rootView.findViewById(R.id.popDetailADMButtonFermer);
        ConstraintLayout backgpopDetailADM = rootView.findViewById(R.id.backgpopDetailADM);
        TexteViewPopDetailADMNomComp= rootView.findViewById(R.id.TexteViewPopDetailADMNomComp);
        TexteViewPopDetailADMDescComp= rootView.findViewById(R.id.TexteViewPopDetailADMDescComp);
        popDetailADMLinearLayout= rootView.findViewById(R.id.popDetailADMLinearLayout);




        lv_historique_eleve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popDetailADMLinearLayout.removeAllViews();
                TexteViewPopDetailADMNomComp.setText(activiteDuMoments.get(i).getActivite().getNom());
                TexteViewPopDetailADMDescComp.setText(activiteDuMoments.get(i).getActivite().getDesc());
                activiteDuMomentSelect=activiteDuMoments.get(i);
                activiteDuMomentSelect.addObserver(EleveDetail.this);
                activiteDuMoments.get(i).fillActiviteDuMoment(getContext());
                Static.setVisibilite(PopDetailADM, View.VISIBLE);
            }
        });
        popDetailADMButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopDetailADM,View.GONE);
                popDetailADMLinearLayout.removeAllViews();
            }
        });
        popDetailADMButtonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopDetailADM,View.GONE);
                popDetailADMLinearLayout.removeAllViews();
            }
        });
        backgpopDetailADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.setVisibilite(PopDetailADM,View.GONE);
                popDetailADMLinearLayout.removeAllViews();
            }
        });


        editTextDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        dateDebut = year+"-"+month+"-"+day;
                        editTextDateDebut.setText(String.format("date debut : %s", dateDebut));

                        if(dateFin != null){
                            if(dateDebut.compareTo(dateFin)>0){
                                dateFin=dateDebut;
                                editTextDateFin.setText(String.format("date fin : %s", dateFin));
                            }
                        }


                        update();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        editTextDateFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        dateFin = year+"-"+month+"-"+day;
                        editTextDateFin.setText(String.format("date fin : %s", dateFin));

                        if(dateDebut!=null){
                            if(dateFin.compareTo(dateDebut)<0){
                                dateDebut=dateFin;
                                editTextDateDebut.setText(String.format("date debut : %s", dateDebut));
                            }
                        }


                        update();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        update();
        return rootView;
    }

    public void resultatAPIGetADM(ArrayList<ActiviteDuMoment> activiteDuMoments){
        this.activiteDuMoments=activiteDuMoments;
        HistoriqueAdapterEleve historiqueAdapterEleve = new HistoriqueAdapterEleve(activiteDuMoments);
        lv_historique_eleve.setAdapter(historiqueAdapterEleve);
        loading.hideLoading();

    }

    @Override
    public void update() {
        if(getActivity()==null)return;
        textViewNomPrenomEleve.setText(personne.getPrenomNomNum());
        Log.e("TAG", "update: " );
        API api = new API();
        loading.showLoading();
        api.getActivitesPlayed(Menu_Educ.token,personne.getId(),dateDebut,dateFin,this,modelEducateur);
        if(activiteDuMomentSelect!=null){
            popDetailADMLinearLayout.removeAllViews();
            for (Competence competence: activiteDuMomentSelect.getCompetences()){
                LinearLayout tableau = new LinearLayout(getActivity());
                tableau.setOrientation(LinearLayout.HORIZONTAL);
                tableau.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tableau.setLayoutParams(lp);

                TextView nomA = new TextView(getActivity());
                nomA.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                nomA.setTypeface(typeface);
                nomA.setText(competence.nom);
                LinearLayout.LayoutParams nomAParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                nomA.setLayoutParams(nomAParams);
                nomA.setGravity(Gravity.CENTER);


                TextView note = new TextView(getActivity());
                note.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                note.setTypeface(typeface);
                if(activiteDuMomentSelect.getResultat(personne.getId(),competence.id)==-1){
                    note.setText(getString(R.string.nonNoté));
                }
                else{
                    note.setText(activiteDuMomentSelect.getResultat(personne.getId(),competence.id)+"/100");
                }


                tableau.addView(nomA);
                tableau.addView(note);
                popDetailADMLinearLayout.addView(tableau);
            }

        }

    }

}