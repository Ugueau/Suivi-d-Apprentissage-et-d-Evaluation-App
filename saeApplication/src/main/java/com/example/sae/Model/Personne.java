package com.example.sae.Model;

import java.util.Objects;

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private int numeroHomonyme;
    private String type;

    public Personne(){

    }

    public Personne(int id, String nom,String prenom,int numeroHomonyme,String type){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.numeroHomonyme=numeroHomonyme;
        this.type=type;
    }
    public Personne(int id, String nom,String prenom,int numeroHomonyme){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.numeroHomonyme=numeroHomonyme;
    }

    public void setPersonne(Personne p){
        id= p.id;
        nom= p.nom;
        prenom= p.prenom;
        numeroHomonyme= p.numeroHomonyme;
        type=p.type;
    }

    public int getId() {
        return id;
    }
    public int getNumeroHomonyme() {
        return numeroHomonyme;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getType() {
        return type;
    }
    public String getPrenomNomNum(){
        if(numeroHomonyme==0){
            return prenom+" "+nom;
        }
        else{
            return prenom+" "+nom+" "+numeroHomonyme;
        }
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setNumeroHomonyme(int numeroHomonyme) {
        this.numeroHomonyme = numeroHomonyme;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numeroHomonyme=" + numeroHomonyme +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return id == personne.id && numeroHomonyme == personne.numeroHomonyme && Objects.equals(nom, personne.nom) && Objects.equals(prenom, personne.prenom) && Objects.equals(type, personne.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, numeroHomonyme, type);
    }

    public String getRechercher(){
        return nom+" "+prenom+" "+numeroHomonyme;
    }
}
