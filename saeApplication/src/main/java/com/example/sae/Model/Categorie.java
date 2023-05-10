package com.example.sae.Model;

import com.example.sae.Oberveur.Observer;
import com.example.sae.Oberveur.Subject;

import java.util.ArrayList;

public class Categorie implements Subject {

    private final ArrayList<Competence> listCompetence;
    private String nom;
    private String description;
    private int id;
    private final ArrayList<Observer> objs = new ArrayList<Observer>();

    public Categorie(){
        id = 0;
        listCompetence = new ArrayList<Competence>();
        nom = "";
        description = "";
    }

    public Categorie(Categorie c){
        id = c.getId();
        listCompetence = c.getListCompetence();
        nom = c.getNom();
        description = c.getDescription();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Competence> getListCompetence() {
        return listCompetence;
    }

    public void setId(int id) {
        this.id = id;
        notifyObservers();
    }

    public void setDescription(String d) {
        description = d;
        notifyObservers();
    }

    public void setNom(String n) {
        nom = n;
    }

    public void addCompetence(Competence c){
        listCompetence.add(c);
        c.listeCategorie.add(this);
        notifyObservers();
    }

    public void removeCompetence(Competence c){
        listCompetence.remove(c);
        c.listeCategorie.remove(this);
        notifyObservers();
    }

    public boolean isIn(Competence c){
        boolean retour = false;
        for(Competence comp : listCompetence){
            if(c.getId() == comp.getId()){
                retour = true;
            }
        }
        return retour;
    }

    @Override
    public void notifyObservers() {
        for (Observer obs: objs){
            obs.update();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        objs.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        objs.remove(observer);
    }

    @Override
    public Object getUpdate(Observer obj) {
        return null;
    }
}
