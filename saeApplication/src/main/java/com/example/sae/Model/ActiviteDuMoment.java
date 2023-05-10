package com.example.sae.Model;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.example.sae.API.API;
import com.example.sae.API.Loading;
import com.example.sae.Oberveur.Observer;
import com.example.sae.Oberveur.Subject;
import com.example.sae.View.Educateur.Menu_Educ;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ActiviteDuMoment implements Subject {
    private int id;
    private final Activite activite;
    private final String date;
    ModelEducateur modelEducateur;
    private ArrayList<Personne> listeEleve;

    private final ArrayList<Observer> observers = new ArrayList<>();


    private final ArrayList<Competence> competences = new ArrayList<>();
    //la paire est id de la Personne/ id de la competence
    private final Map<Pair<Integer,Integer>,Integer> resultat = new HashMap<>();

    private Loading loading;

    public ActiviteDuMoment(int id,Activite activite,String date,ArrayList<Personne> listeEleve,ModelEducateur modelEducateur){
        this.id=id;
        this.activite=activite;
        this.date=date;
        this.listeEleve=listeEleve;
        this.modelEducateur=modelEducateur;
    }
    public ActiviteDuMoment(int id,Activite activite,String date,ModelEducateur modelEducateur){
        this.id=id;
        this.activite=activite;
        this.date=date;
        this.modelEducateur=modelEducateur;
    }

    public ArrayList<Personne> getListeEleve() {
        return listeEleve;
    }
    public int getId() {
        return id;
    }
    public String getDate() {
        String[] DateHeure = date.split(" ");
        String[] DateDetaille = DateHeure[0].split("-");
        return DateDetaille[2]+"/"+DateDetaille[1]+"/"+DateDetaille[0]+" "+DateHeure[1];
    }
    public Activite getActivite() {
        return activite;
    }



    public void addEleve(Personne personne){
        this.listeEleve.add(personne);
        for(Competence competence:competences){
            // -1 si ya rien dans la recherche API alors remplir
            resultat.put(new Pair<>(personne.getId(),competence.id),-1);
        }
        notifyObservers();
    }
    public void addComp(Competence competence){
        this.competences.add(competence);
        for(Personne eleve:listeEleve){
            // -1 si ya rien dans la recherche API alors remplir
            resultat.put(new Pair<>(eleve.getId(),competence.id),-1);
        }
        //notifyObservers();
    }
    private void deleteCompResultat(int id){
        Map<Pair<Integer,Integer>,Integer> tmp = new HashMap<>();
        for(Map.Entry<Pair<Integer,Integer>,Integer> pair:resultat.entrySet()){
            if (pair.getKey().second == id){
                tmp.put(pair.getKey(), pair.getValue());
            }
        }
    }
    public void removeComp(Competence competence){
        competences.remove(competence);
        deleteCompResultat(competence.getId());
        //notifyObservers();
    }
    private void deletePersonneResultat(int id){
        Map<Pair<Integer,Integer>,Integer> tmp = new HashMap<>();
        for(Map.Entry<Pair<Integer,Integer>,Integer> pair:resultat.entrySet()){
            if (pair.getKey().first == id){
                tmp.put(pair.getKey(), pair.getValue());

            }
        }
        for(Map.Entry<Pair<Integer,Integer>,Integer> pair:tmp.entrySet()){
            resultat.remove(pair.getKey());
        }
    }
    public void removeEleve(Personne personne){
        int id = personne.getId();
        this.listeEleve.remove(personne);
        deletePersonneResultat(id);
        notifyObservers();
    }

    public void setCompetences(JSONObject json) {
        if(json!=null){

            Iterator<String> keys = json.keys();
            while (keys.hasNext()){
                String key = keys.next();

                try {
                    if(json.get(key) instanceof JSONObject){
                        //Log.i("TAG", ((JSONObject) json.get(key)).getString("nomCompetence"));
                        competences.add(new Competence(((JSONObject) json.get(key)).getInt("idCompetence"),((JSONObject) json.get(key)).getString("nomCompetence"),((JSONObject) json.get(key)).getString("descCompetence")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        API api = new API();
        api.queryADMRes(id, Menu_Educ.token,this);

    }





    public void setResultat(JSONObject jsonResultat) {
        //API recherche Resultat

        for(Personne personne:getListeEleve()){
            for(Competence competence:competences){
                // -1 si ya rien dans la recherche API alors remplir
                resultat.put(new Pair<>(personne.getId(),competence.id),-1);
            }
        }

        if(jsonResultat!=null){
            Iterator<String> keys = jsonResultat.keys();
            while (keys.hasNext()){
                String key = keys.next();

                try {
                    if(jsonResultat.get(key) instanceof JSONObject){
                        changeResultat(((JSONObject) jsonResultat.get(key)).getInt("idPersonne"),
                                ((JSONObject) jsonResultat.get(key)).getInt("idCompetence"),((JSONObject) jsonResultat.get(key)).getInt("note"));
                        //competences.add(new Competence(((JSONObject) json.get(key)).getInt("idCompetence"),((JSONObject) json.get(key)).getString("nomCompetence"),((JSONObject) json.get(key)).getString("descCompetence")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        loading.hideLoading();
        notifyObservers();
    }

    public void resultApiError(){
        loading.hideLoading();
    }

    public ArrayList<Competence> getCompetences() {
        return competences;
    }

    public Map<Pair<Integer, Integer>, Integer> getResultat() {
        return resultat;
    }

    public int getResultat(int idPersonne, int idCompetence) {
        Pair<Integer, Integer> key = new Pair<>(idPersonne, idCompetence);
        if (resultat.containsKey(key)) {
            Integer value = resultat.get(key);
            if (value != null) {
                return value.intValue();
            }
        }
        // Handle the case where the key doesn't exist or the value is null
        return 0;
    }


    public void setNoteResultat(int idPersonne, int idCompetence, int valeur){
        if(resultat.get(new Pair<>(idPersonne,idCompetence))==null) return;
        resultat.remove(new Pair<>(idPersonne,idCompetence));
        resultat.put(new Pair<>(idPersonne,idCompetence),valeur);
    }

    public void changeResultat(int idPersonne,int idCompetence,int resultat){
        this.resultat.remove(new Pair<>(idPersonne,idCompetence));
        this.resultat.put(new Pair<>(idPersonne,idCompetence),resultat);
    }

    /**
     * remplit c'est donner en fesant des requet api car en plus d'etre un model cette classe est un obervable
     * @param context le contexte pour pouvoir mettre le loading quand les requete api sont faire
     */
    public void fillActiviteDuMoment(Context context){
        competences.clear();
        resultat.clear();

        if(listeEleve==null){
            Log.e("TAG", "null" );
            API api = new API();
            loading = new Loading(context);
            loading.showLoading();
            api.getEleveADM(Menu_Educ.token,id,this);

        }
        else{
            Log.e("TAG", "pas null" );
            Log.i("listeEleve dans fill", listeEleve.toString());

            loading = new Loading(context);
            loading.showLoading();
            API api = new API();
            api.queryADMComp(id, Menu_Educ.token,this);
        }
    }

    public void resultApiGetEleve(ArrayList<Integer> idEleves){
        listeEleve = new ArrayList<>();
        for(Personne personne:modelEducateur.getListeEleveRecherche()){
            if(idEleves.indexOf(personne.getId())!=-1){
                listeEleve.add(personne);
            }
        }



        API api = new API();
        api.queryADMComp(id, Menu_Educ.token,this);
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
}
