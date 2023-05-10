package com.example.sae.Model;

import java.util.ArrayList;
import java.util.Map;

public class Educateur extends Personne{
    private int idIMESelected;
    private Map<Integer,String> ListeIME;

    public Educateur(){

    }


    public Educateur(int id, String nom, String prenom, int numeroHomonyme, String type,int idIMESelected,Map<Integer,String> ListeIME) {
        super(id, nom, prenom, numeroHomonyme, type);
        this.idIMESelected=idIMESelected;
        this.ListeIME=ListeIME;
    }

    public void setEducateur(Educateur e){
        e.setPersonne(e);
        idIMESelected=e.idIMESelected;
        ListeIME=e.getListeIME();
    }
    public int getIdIMESelected(){
        return idIMESelected;
    }

    public Map<Integer,String> getListeIME(){
        return ListeIME;
    }

    public void setIdIMESelected(int i){
        if(ListeIME.containsKey(i)){
            idIMESelected=i;
        }
    }
}
