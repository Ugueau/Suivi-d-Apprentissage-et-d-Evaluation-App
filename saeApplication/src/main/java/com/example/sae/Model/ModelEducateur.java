package com.example.sae.Model;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.sae.API.API;
import com.example.sae.API.DataBase;
import com.example.sae.API.Loading;
import com.example.sae.Oberveur.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.sae.Oberveur.Observer;
import com.example.sae.View.Educateur.AccueilFragment;
import com.example.sae.View.Educateur.Menu_Educ;
import com.example.sae.View.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.Semaphore;


public class ModelEducateur implements Subject {

    private Educateur personne = new Educateur();
    private final Context context;

    private final ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<Personne> ListeEducateurRecherche = new ArrayList<>();
    private ArrayList<Personne> ListeEducateurSelected = new ArrayList<>();

    private ArrayList<Personne> ListeEleveRecherche = new ArrayList<>();
    private ArrayList<Personne> ListeEleveSelected = new ArrayList<>();

    private Activite ActiviteSelected = new Activite();
    private ArrayList<Activite> ListeActivite = new ArrayList<>();

    private ArrayList<ActiviteDuMoment> ListeActiviteDuMoment = new ArrayList<>();

    private final Loading loading;
    private int requestCounter = 0;

    private int conteurFillHistorique = 0;

    private final ArrayList<Categorie> listCategorie= new ArrayList<>();
    private final ArrayList<Competence> listeCompetenceIME= new ArrayList<>();



    public ModelEducateur(Context context){
        this.context=context;
        loading=new Loading(context);
        try {
            UpdateModel();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Categorie> getListCategorie() {
        return listCategorie;
    }
    public ArrayList<Competence> getListeCompetenceIME() {
        return listeCompetenceIME;
    }

    /**
     * decremente un compteur pour savoir quand enlever le loading car on fait plusieur requet en simultané
     */
    public void decreaseRequestCounter() {
        requestCounter--;
        Log.e("requestCounter", String.valueOf(requestCounter));
        if (requestCounter == 0) {
            loading.hideLoading();
        }
    }
    /**
     * decremente un compteur pour savoir quand enlever le loading car on fait plusieur requet en simultané
     */
    public void decreaseConteurFillHistorique(int idIME,String token,ModelEducateur modelEducateur ,Pair<AccueilFragment,Integer> pair) {
        conteurFillHistorique--;
        Log.e("conteurFillHistorique", String.valueOf(conteurFillHistorique));
        if (conteurFillHistorique == 0) {
            API api = new API();
            api.fillHistorique(idIME,token,modelEducateur,pair);
        }
    }

    private void UpdateModel() throws JSONException {

        loading.showLoading();
        API api = new API();
        api.queryId(Menu_Educ.token,this);
        Log.i("TAG", Menu_Educ.token);

    }

    public  void queryApiPersonne(JSONObject JSONpersonne){
        try {
            Map<Integer,String> test = new HashMap<>();
            JSONObject ListeIME = null;


            ListeIME = JSONpersonne.getJSONObject("ListeIME");


            Iterator<String> keys = ListeIME.keys();

            while (keys.hasNext()){
                String key = keys.next();
                if (ListeIME.get(key) instanceof JSONObject) {
                    test.put(((JSONObject) ListeIME.get(key)).getInt("id"),((JSONObject) ListeIME.get(key)).getString("nom"));
                }
            }

            personne = new Educateur(JSONpersonne.getInt("id"),JSONpersonne.getString("nom"),JSONpersonne.getString("prenom"),
                    JSONpersonne.getInt("numeroHomonyme"),JSONpersonne.getString("type"),JSONpersonne.getInt("idIMESelected"),test);

            loading.hideLoading();

            Pair<AccueilFragment,Integer> pair = new Pair<>(null,null);
            MettreAJourStockage(pair);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void queryApiID(int id){
        API api = new API();
        api.queryPersonne(id,Menu_Educ.token,this);
    }

    public void finish(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Erreur");
        builder.setMessage("Erreur requet");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

        DataBase dataBase = new DataBase(context);
        dataBase.reset();


        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Menu_Educ)context).finish();
    }

    public void MettreAJourStockage(Pair<AccueilFragment,Integer> pair){
        requestCounter = 5; // A corrigé pas normal de metrte 4 mais 5 normalement
        conteurFillHistorique=2;
        loading.showLoading();
        ActiviteSelected = new Activite();
        ListeEducateurSelected.clear();
        ListeEleveSelected.clear();
        API api = new API();
        api.fillEduacateur(personne.getIdIMESelected(),Menu_Educ.token,this);
        API api2 = new API();
        api2.fillEleves(personne.getIdIMESelected(),Menu_Educ.token,this,pair);
        API api3 = new API();
        api3.fillActivite(personne.getIdIMESelected(), Menu_Educ.token,this,pair);
        API api5 = new API();
        api5.fillCategorieCompetence(Menu_Educ.token, String.valueOf(personne.getIdIMESelected()), this);

    }

    public ArrayList<Activite> getListeActivite() {
        return ListeActivite;
    }

    public Activite getActiviteSelected() {
        return ActiviteSelected;
    }

    public void setListeActivite(ArrayList<Activite> listeActivite,Pair<AccueilFragment,Integer> pair) {
        ListeActivite = listeActivite;
        notifyObservers();
        decreaseRequestCounter();
        decreaseConteurFillHistorique(personne.getIdIMESelected(), Menu_Educ.token,this,pair);
    }
    public void clearEducateurSelected(){
        ListeEducateurSelected.clear();
    }
    public void clearEleveSelected(){
        ListeEleveSelected.clear();
    }
    public void setListeEducateurRecherche(ArrayList<Personne> listeEducateurRecherche) {
        ListeEducateurRecherche = listeEducateurRecherche;
        notifyObservers();
        decreaseRequestCounter();
    }

    public void setListeActiviteDuMoment(ArrayList<ActiviteDuMoment> listeActiviteDuMoment,Pair<AccueilFragment,Integer> pair){
        ListeActiviteDuMoment = listeActiviteDuMoment;
        notifyObservers();
        decreaseRequestCounter();
        if(pair.first!=null){
            Log.e("TAG", String.valueOf(pair.second) );
            pair.first.queryApiCreateADM(pair.second);
        }

    }


    public void setListeEleveRecherche(ArrayList<Personne> listeEleveRecherche,Pair<AccueilFragment,Integer> pair) {
        ListeEleveRecherche = listeEleveRecherche;
        notifyObservers();
        decreaseRequestCounter();
        decreaseConteurFillHistorique(personne.getIdIMESelected(), Menu_Educ.token,this,pair);
    }

    public void setActiviteSelected( Activite activiteSelected) {
        ActiviteSelected = activiteSelected;
        notifyObservers();
    }

    public void setListeEducateurSelected(ArrayList<Personne> listeEducateurSelected) {
        ListeEducateurSelected = listeEducateurSelected;
        notifyObservers();
    }

    public void setListeEleveSelected(ArrayList<Personne> listeEleveSelected) {
        ListeEleveSelected = listeEleveSelected;
        notifyObservers();
    }

    public Educateur getPersonne(){
        return personne;
    }

    public void setPersonne(Educateur personne) {
        this.personne = personne;
        notifyObservers();
    }

    public ArrayList<Personne> getListeEducateurRecherche() {return ListeEducateurRecherche;}
    public ArrayList<Personne> getListeEducateurSelected() {
        return ListeEducateurSelected;
    }
    public ArrayList<Personne> getListeEleveRecherche() {
        return ListeEleveRecherche;
    }
    public ArrayList<Personne> getListeEleveSelected() {return ListeEleveSelected;}
    public ArrayList<ActiviteDuMoment> getListeActiviteDuMoment() {
        return ListeActiviteDuMoment;
    }

    public void addEducateurSelected(Personne p){
        ListeEducateurSelected.add(p);
        notifyObservers();
    }
    public void removeEducateurSelected(Personne p){
        ListeEducateurSelected.remove(p);
        notifyObservers();
    }
    public void removeEleveSelected(Personne p){
        ListeEleveSelected.remove(p);
        notifyObservers();
    }


    public void setidIMESelected(int i){

        personne.setIdIMESelected(i);
        Pair<AccueilFragment,Integer> pair = new Pair<>(null,null);
        MettreAJourStockage(pair);
        notifyObservers();
    }

    public void resultatAPICompetenceCategorie(JSONObject result) throws JSONException {
        this.listeCompetenceIME.clear();
        this.listCategorie.clear();
        JSONObject json = result.getJSONObject("resultat");
        Log.i("CompetenceCategoriAPI", json.toString());
        Iterator<String> keys = json.keys();

        JSONArray listeCategorie = json.getJSONArray("listeCategorie");
        JSONArray listCompetence = json.getJSONArray("listeCompetence");

        for (int i = 0; i < listeCategorie.length(); i++) {
            JSONObject jsonCat = (JSONObject) listeCategorie.get(i);
            Categorie cat = new Categorie();
            cat.setId(jsonCat.getInt("idCategorie"));
            cat.setNom(jsonCat.getString("nom"));
            cat.setDescription(jsonCat.getString("description"));
            this.listCategorie.add(cat);
        }

        for (int i = 0; i < listCompetence.length(); i++) {
            JSONObject jsonComp = (JSONObject) listCompetence.get(i);

            Competence comp = new Competence(jsonComp.getInt("id"), jsonComp.getString("nom"), jsonComp.getString("description"));

            JSONArray listeCategorieCompetence = jsonComp.getJSONArray("listeCategorieCompetence");
            for(int j = 0; j < listeCategorieCompetence.length(); j++){
                for(Categorie cat : this.listCategorie){
                    if(cat.getId() == listeCategorieCompetence.getInt(j)){
                        comp.addCategorie(cat);
                        Log.e("test", comp.nom + " -> " + cat.getNom());
                    }
                }
            }
            this.listeCompetenceIME.add(comp);
        }
        decreaseRequestCounter();
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer obs :observers){
            obs.update();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public Object getUpdate(Observer obj) {
        return null;
    }

    public void resultApiError() {
        loading.hideLoading();
        decreaseRequestCounter();
    }
}
