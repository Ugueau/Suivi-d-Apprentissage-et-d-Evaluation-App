package com.example.sae;

import android.view.View;


public class Static {
    public static void setVisibilite(View view, int visibilite){
        view.setVisibility(visibilite);
    }
    private static int getResulat(int nbr,int lenthRechercher){
        return lenthRechercher-nbr;
    }

    /**
     * function qui genere un nombre qui sinifie si le mot rechercher ressemble ou pas
     * @param recherches le mot qui est rentrer pour etre comperer
     * @param to le mot qui est comparer
     * @return le nombre de lettre en commun pour savoir la ressemblance
     */
    public static int recherche(String recherches,String to) {
        if (recherches == "") {
            return -1;
        } else {
            int maxcpt = 0;
            for (int y = 0; y < to.length(); y++) {
                int cpt = 0;
                for (int i = 0; i < recherches.length(); i++) {
                    if(y+i<to.length()){
                        if (Character.toUpperCase(to.charAt(y + i)) == Character.toUpperCase(recherches.charAt(i))) {
                            cpt++;
                            if (maxcpt < cpt) maxcpt = cpt;
                        } else {
                            cpt = 0;
                            break;
                        }
                    }

                }
            }
            return getResulat(maxcpt,recherches.length());
        }
    }
}
