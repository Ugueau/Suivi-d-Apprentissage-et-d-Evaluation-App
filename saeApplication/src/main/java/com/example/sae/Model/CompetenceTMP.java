package com.example.sae.Model;

import com.example.sae.API.API;

import org.json.JSONArray;

import java.util.ArrayList;

public class CompetenceTMP {
    public String nom = "";
    public String desc = "";
    public ArrayList<Categorie> listeCategorie = new ArrayList<>();
    public Competence comptenceCree;

    public CompetenceTMP(){
    }

    public void resultatApi(int id){
        comptenceCree = new Competence(id, nom, desc);
        for(Categorie cat : listeCategorie){
            cat.addCompetence(comptenceCree);
        }
    }

    public void requeteAPI(String token) throws Exception {
        if(!nom.isEmpty() && !desc.isEmpty() && !listeCategorie.isEmpty()){
            API api = new API();
            JSONArray jsonArrayListeCategorie = new JSONArray();
            for(Categorie cat : listeCategorie){
                jsonArrayListeCategorie.put(cat.getId());
            }
            api.querryNewCompetence(token, nom, desc, jsonArrayListeCategorie, this);
        }
        else{
            throw new Exception("les champs de nom, description et de categorie doivent etre initialisé");
        }
    }

    public boolean isIn(Categorie cat){

        boolean retour;
        retour = false;

        for(Categorie c : listeCategorie){
            if(cat.getId() == c.getId()){
                retour = true;
            }
        }
        return retour;
    }
}
