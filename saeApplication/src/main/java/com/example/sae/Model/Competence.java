package com.example.sae.Model;

import com.example.sae.Oberveur.Observer;
import com.example.sae.Oberveur.Subject;

import java.util.ArrayList;

public class Competence {
    public int id;
    public String nom;
    public String desc;
    public ArrayList<Categorie> listeCategorie = new ArrayList<>();

    public Competence(int id, String nom, String desc) {
        this.id = id;
        this.nom = nom;
        this.desc = desc;
    }

    public void addCategorie(Categorie c) {
        listeCategorie.add(c);
        c.getListCompetence().add(this);
    }

    public void removeCategorie(Categorie c) {
        if (listeCategorie.size() != 1) {
            listeCategorie.remove(c);
            c.getListCompetence().remove(this);
        }
    }

    public int getId() {
        return id;
    }

    public boolean isIn(Categorie cat) {

        boolean retour;
        retour = false;

        for (Categorie c : listeCategorie) {
            if (cat.getId() == c.getId()) {
                retour = true;
            }
        }
        return retour;
    }
}
