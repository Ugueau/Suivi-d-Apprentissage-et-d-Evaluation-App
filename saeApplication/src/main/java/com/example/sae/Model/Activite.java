package com.example.sae.Model;

import android.view.Display;

import com.example.sae.View.Educateur.ModifyActivite;

import java.util.ArrayList;

public class Activite {
    private final int id;
    private String nom;
    private String desc;
    private ArrayList<Competence> competences = new ArrayList<>();

    ModifyActivite modifyActivite;
    public Activite(){
        this.id=-1;
        nom=null;
        desc=null;
    }
    public Activite(int id, String nom, String desc){
        this.id=id;
        this.nom=nom;
        this.desc=desc;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getRechercher(){
        return nom;
    }

    public ArrayList<Competence> getCompetences() {
        return competences;
    }
    public void fillCompetence(ArrayList<Competence> competences){
        this.competences=competences;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }
}