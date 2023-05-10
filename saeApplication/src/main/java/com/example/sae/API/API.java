package com.example.sae.API;

import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.example.sae.Model.Activite;
import com.example.sae.Model.ActiviteDuMoment;
import com.example.sae.Model.Categorie;
import com.example.sae.Model.Competence;
import com.example.sae.Model.CompetenceTMP;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Model.Personne;
import com.example.sae.View.Educateur.DuMoment;
import com.example.sae.View.Educateur.AccueilFragment;
import com.example.sae.View.Educateur.EleveDetail;
import com.example.sae.View.Educateur.ModifyActivite;
import com.example.sae.View.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class API {

    //private static final String URL_BASE = "http://192.168.240.88/api";
    private static final String URL_BASE = "https://sae-ime.ovh/api";
    public API(){}

    public void modifyCompetence(String token, int id, String nom, String desc){
        JSONObject jsonReslt = new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Faites quelque chose de long, comme un appel API ou un calcul
                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("id", String.valueOf(id));
                parameters.put("nom", nom);
                parameters.put("description", desc);
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/modify/Competence",parameters, jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
    }

    public void fillCategorieCompetence(String token, String IME, ModelEducateur model){
        JSONObject jsonReslt = new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("fillCategorieCompetence", "start ");
                // Faites quelque chose de long, comme un appel API ou un calcul
                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idIME", IME);
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/competenceCategorie",parameters, jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            model.resultatAPICompetenceCategorie(jsonReslt);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    public void querryGetVersion(MainActivity mainActivity){
        JSONObject jsonReslt = new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> parameters = new HashMap<>();
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/versionApplication",parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("querryGetVersion", jsonReslt.toString());
                        try {

                            mainActivity.requestApiVersion(jsonReslt.getJSONObject("resultat").getInt("resultat"));
                        } catch (JSONException e) {
                            mainActivity.requestApiVersion(0);
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }).start();
    }

    public void querryNewCompetence(String token, String nom, String description, JSONArray listeCategorie,CompetenceTMP compTMP){
        JSONObject jsonReslt = new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Faites quelque chose de long, comme un appel API ou un calcul
                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("nom", nom);
                parameters.put("description", description);
                parameters.put("listeCategorie", listeCategorie.toString());
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/add/competence",parameters, jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            compTMP.resultatApi(jsonReslt.getJSONObject("resultat").getInt("idNouvelleCompetence"));
                        } catch (JSONException e) {

                        }
                    }
                });
            }
        }).start();
    }
    public void querryNewCompetenceActivite(String token, String nom, String description, JSONArray listeCategorie,ModifyActivite modifyActivite){
        JSONObject jsonReslt = new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Faites quelque chose de long, comme un appel API ou un calcul
                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("nom", nom);
                parameters.put("description", description);
                parameters.put("listeCategorie", listeCategorie.toString());
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/add/competence",parameters, jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            modifyActivite.resultatApi(jsonReslt.getJSONObject("resultat").getInt("idNouvelleCompetence"));
                        } catch (JSONException e) {
                            modifyActivite.resultApiError();
                        }
                    }
                });
            }
        }).start();
    }

    public void queryConnect(String login, String mdp, MainActivity mainActivity){
        final JSONObject jsonReslt = new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Faites quelque chose de long, comme un appel API ou un calcul
                Log.i("queryConnect", "start");
                Map<String, String> parameters = new HashMap<>();
                parameters.put("login", login);
                parameters.put("mdp", mdp);
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/connect",parameters, jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            Log.i("queryConnect", jsonReslt.getJSONObject("resultat").toString());
                            mainActivity.setQueryApi(jsonReslt.getJSONObject("resultat"));
                        } catch (JSONException e) {
                            mainActivity.resultApiError();
                        }
                    }
                });
            }
        }).start();

    }
    public void queryPersonne(int id,String token,ModelEducateur modelEducateur){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("queryPersonne", "start");
                // Faites quelque chose de long, comme un appel API ou un calcul
                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("id", String.valueOf(id));
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/personne",parameters,jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.i("queryPersonne", jsonReslt.getJSONObject("resultat").toString());
                            modelEducateur.queryApiPersonne(jsonReslt.getJSONObject("resultat"));
                        } catch (JSONException e) {
                            modelEducateur.finish();
                        }
                    }
                });
            }
        }).start();

    }
    public void queryId(String token,ModelEducateur modelEducateur){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("queryId", "start");
                // Faites quelque chose de long, comme un appel API ou un calcul
                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/id/",parameters,jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.i("queryId", jsonReslt.getJSONObject("resultat").toString());
                            modelEducateur.queryApiID(jsonReslt.getJSONObject("resultat").getInt("id"));
                        } catch (JSONException e) {
                            modelEducateur.finish();
                        }
                    }
                });
            }
        }).start();
    }
    public void rejouer(String token,int idADM,AccueilFragment accueilFragment){
        final Handler handler = new Handler();
        JSONObject jsonReslt= new JSONObject();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Faites quelque chose de long, comme un appel API ou un calcul
                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idADM", String.valueOf(idADM));
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/add/rejouerActiviteDuMoment",parameters,jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            accueilFragment.setRejouerAPI(jsonReslt.getJSONObject("resultat").getInt("idADM"));
                        } catch (JSONException e) {
                            accueilFragment.resultApiError();
                        }
                    }
                });
            }
        }).start();
    }
    public boolean fillEleves(int IME,String token,ModelEducateur modelEducateur,Pair<AccueilFragment,Integer> pair){
        JSONObject jsonReslt= new JSONObject();
        ArrayList<Personne> personnes = new ArrayList<>();
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Faites quelque chose de long, comme un appel API ou un calcul

                Log.i("fillEleves", "start ");

                try {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("token", token);
                    parameters.put("idIME", String.valueOf(IME));
                    ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/eleves",parameters,jsonReslt);
                    apiRecherche.start();

                    JSONObject json = jsonReslt.getJSONObject("resultat");
                    Iterator<String> keys = json.keys();

                    while(keys.hasNext()) {
                        String key = keys.next();
                            if (json.get(key) instanceof JSONObject) {
                                personnes.add(new Personne(((JSONObject) json.get(key)).getInt("idPersonne"),((JSONObject) json.get(key)).getString("nom"),
                                        ((JSONObject) json.get(key)).getString("prenom"),((JSONObject) json.get(key)).getInt("numeroHomonyme")));
                            }
                    }
                } catch (JSONException e) {
                    modelEducateur.resultApiError();
                }

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        modelEducateur.clearEleveSelected();
                        Log.i("fillEleves", personnes.toString());
                        modelEducateur.setListeEleveRecherche(personnes,pair);
                    }
                });
            }
        }).start();
        return true;
    }
    public boolean fillEduacateur(int IME,String token,ModelEducateur modelEducateur){
        JSONObject jsonReslt= new JSONObject();
        ArrayList<Personne> educateurs = new ArrayList<>();

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("fillEduacateur", "start ");
                // Faites quelque chose de long, comme un appel API ou un calcul
                try {

                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("token", token);
                    parameters.put("idIME", String.valueOf(IME));
                    ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/educateurs",parameters,jsonReslt);
                    apiRecherche.start();

                    JSONObject json = jsonReslt.getJSONObject("resultat");
                    Iterator<String> keys = json.keys();


                    while (keys.hasNext()){
                        String key = keys.next();

                        if(json.get(key) instanceof JSONObject){
                            educateurs.add(new Personne(((JSONObject) json.get(key)).getInt("idPersonne"),((JSONObject) json.get(key)).getString("nom"),
                                    ((JSONObject) json.get(key)).getString("prenom"),((JSONObject) json.get(key)).getInt("numeroHomonyme")));
                        }
                    }

                } catch (JSONException e) {
                    modelEducateur.resultApiError();
                }

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("fillEduacateur", educateurs.toString());
                        modelEducateur.setListeEducateurRecherche(educateurs);
                        modelEducateur.clearEducateurSelected();
                        for(Personne p : modelEducateur.getListeEducateurRecherche()){
                            if(p.getId()== modelEducateur.getPersonne().getId()){
                                modelEducateur.addEducateurSelected(p);
                            }
                        }
                    }
                });
            }
        }).start();
        return true;
    }
    public boolean fillActivite(int IME,String token,ModelEducateur modelEducateur,Pair<AccueilFragment,Integer> pair){
        JSONObject jsonReslt= new JSONObject();
        ArrayList<Activite> activites = new ArrayList<>();

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("fillActivite", "start ");
                // Faites quelque chose de long, comme un appel API ou un calcul
                try {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("token", token);
                    parameters.put("idIME", String.valueOf(IME));
                    ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/activite",parameters,jsonReslt);
                    apiRecherche.start();

                    JSONObject json = jsonReslt.getJSONObject("resultat");
                    Iterator<String> keys = json.keys();


                    while (keys.hasNext()){
                        String key = keys.next();

                        if(json.get(key) instanceof JSONObject){
                            activites.add(new Activite(((JSONObject) json.get(key)).getInt("idActivite"),((JSONObject) json.get(key)).getString("nom"),((JSONObject) json.get(key)).getString("description")));
                        }


                    }
                } catch (JSONException e) {
                    modelEducateur.resultApiError();
                }

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("fillActivite", activites.toString());
                        modelEducateur.setListeActivite(activites,pair);
                    }
                });
            }
        }).start();


        return true;
    }

    public boolean fillHistorique(int IME,String token,ModelEducateur modelEducateur,Pair<AccueilFragment,Integer> pair){
        JSONObject jsonReslt= new JSONObject();
        ArrayList<ActiviteDuMoment> activiteDuMoments = new ArrayList<>();

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("fillHistorique2", "start ");
                // Faites quelque chose de long, comme un appel API ou un calcul

                try {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("token", token);
                    parameters.put("idIME", String.valueOf(IME));
                    ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/historique",parameters,jsonReslt);
                    apiRecherche.start();

                    JSONObject json = jsonReslt.getJSONObject("resultat");
                    Iterator<String> keys = json.keys();


                    while (keys.hasNext()) {
                        String key = keys.next();

                        if(json.get(key) instanceof JSONObject){
                            ArrayList<Personne> eleves = new ArrayList<>();
                            if(((JSONObject) json.get(key)).getString("eleves")!="null"){
                                for (String s :((JSONObject) json.get(key)).getString("eleves").split(",")){

                                    for(Personne p : modelEducateur.getListeEleveRecherche()){
                                        if(p.getId()==Integer.valueOf(s)){
                                            eleves.add(p);
                                        }
                                    }
                                }
                            }
                            Activite activite = new Activite(-1,"test","test");
                            for(Activite a : modelEducateur.getListeActivite()){
                                if(a.getId()==Integer.valueOf(((JSONObject) json.get(key)).getString("activite"))){
                                    activite=a;
                                }
                            }
                            activiteDuMoments.add(new ActiviteDuMoment(((JSONObject) json.get(key)).getInt("id"),activite,
                                    ((JSONObject) json.get(key)).getString("date"),eleves,modelEducateur));
                        }



                    }
                } catch (JSONException e) {
                    modelEducateur.resultApiError();
                }

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("fillHistorique2", activiteDuMoments.toString());
                        modelEducateur.setListeActiviteDuMoment(activiteDuMoments,pair);
                    }
                });
            }
        }).start();
        return true;
    }
    public void queryADMComp(int idADM, String token,ActiviteDuMoment activiteDuMoment){
        final JSONObject jsonReslt = new JSONObject();

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("queryADMComp", "start ");
                // Faites quelque chose de long, comme un appel API ou un calcul

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idActiviteDuMoment", String.valueOf(idADM));
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/activiteDuMoment/competance",parameters, jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.i("queryADMComp", jsonReslt.getJSONObject("resultat").toString());
                            activiteDuMoment.setCompetences(jsonReslt.getJSONObject("resultat"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
    public void queryADMRes(int idADM, String token,ActiviteDuMoment activiteDuMoment){
        final JSONObject jsonReslt = new JSONObject();

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("queryADMRes", "start ");
                // Faites quelque chose de long, comme un appel API ou un calcul
                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idActiviteDuMoment", String.valueOf(idADM));
                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/get/activiteDuMoment/resultat",parameters, jsonReslt);
                apiRecherche.start();

                // Utilisez le handler pour mettre à jour la vue dans le thread de l'interface utilisateur
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if(jsonReslt.getString("resultat")!="null"){
                                Log.i("queryADMRes", jsonReslt.getJSONObject("resultat").toString());
                                activiteDuMoment.setResultat(jsonReslt.getJSONObject("resultat"));
                            }
                            else{
                                activiteDuMoment.setResultat(null);
                            }

                        } catch (JSONException e) {
                            try {
                                int error= jsonReslt.getJSONObject("resultat").getInt("error");
                                activiteDuMoment.resultApiError();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();

    }
    public void addADM(String token, ArrayList<Personne> eleves, ArrayList<Personne> educateurs, Activite activite, AccueilFragment accueilFragment, ModelEducateur modelEducateur){
        JSONObject jsonReslt= new JSONObject();

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("addADM", "start ");
                Map<String, String> parameters = new HashMap<>();

                JSONArray JsonEleves= new JSONArray();
                for(Personne personne: eleves){
                    JsonEleves.put(personne.getId());
                }
                JSONArray JsonEducateurs= new JSONArray();
                for(Personne personne: educateurs){
                    JsonEducateurs.put(personne.getId());
                }

                parameters.put("token", token);
                parameters.put("eleves",JsonEleves.toString());
                parameters.put("educateurs", JsonEducateurs.toString());
                parameters.put("idActivite", String.valueOf(activite.getId()));

                Log.i("ALED", parameters.toString());

                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/add/activiteDuMoment",parameters,jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            modelEducateur.MettreAJourStockage(new Pair<AccueilFragment,Integer>(accueilFragment,jsonReslt.getJSONObject("resultat").getInt("idADM")));

                            //Log.i("ALED", jsonReslt.getJSONObject("resultat").toString());
                        } catch (JSONException e) {
                            try {
                                int error= jsonReslt.getJSONObject("resultat").getInt("error");
                                modelEducateur.resultApiError();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();
    }
    public void setResultat(String token,int idADM,DuMoment duMoment,ActiviteDuMoment activiteDuMoment){
        JSONObject jsonResltEleve= new JSONObject();
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONArray idPersonne_idCompetence_valeur = new JSONArray();

                Map<Pair<Integer, Integer>, Integer> res = activiteDuMoment.getResultat();
                for (Map.Entry<Pair<Integer, Integer>, Integer> entry : res.entrySet()) {
                    Pair<Integer, Integer> pair = entry.getKey();
                    Integer valeur = entry.getValue();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("idPersonne",pair.first);
                        jsonObject.put("idCompetence",pair.second);
                        jsonObject.put("valeur",valeur);
                        idPersonne_idCompetence_valeur.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                Log.i("setResultat", "start ");


                JSONArray idEleve = new JSONArray();

                for (Personne p : activiteDuMoment.getListeEleve()) {
                    try {
                        boolean isExist=false;
                        for (int j = 0; j < idEleve.length(); j++) {
                            if(idEleve.getInt(j)==p.getId()) isExist=true;
                        }

                        if(!isExist){
                            idEleve.put(p.getId());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Map<String, String> parametersEleves = new HashMap<>();

                parametersEleves.put("token", token);
                parametersEleves.put("eleves", idEleve.toString());
                parametersEleves.put("idADM", String.valueOf(idADM));

                Log.i("setNewEleve", idEleve.toString());

                ApiRecherchePost apiRechercheEleve=new ApiRecherchePost(URL_BASE+"/add/EleveActiviteDuMoment",parametersEleves,jsonResltEleve);
                apiRechercheEleve.start();

                Log.i("setNewEleve", jsonResltEleve.toString());


                Map<String, String> parameters = new HashMap<>();

                parameters.put("token", token);
                parameters.put("idPersonne_idCompetence_valeur", idPersonne_idCompetence_valeur.toString());
                parameters.put("idADM", String.valueOf(idADM));

                Log.i("setResultat", idPersonne_idCompetence_valeur.toString());

                ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/add/createModifyResultat",parameters,jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        duMoment.endSetResultatApi();
                    }
                });
            }
        }).start();
    }
    public void modifyCompActiviteDuMoment(String token,int idADM,ArrayList<Integer> ListeidCompetences,DuMoment duMoment){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e("modifyCompActivite", "start ");

                JSONArray jsonArray = new JSONArray(ListeidCompetences);

                Log.e("TAG", jsonArray.toString() );

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idADM", String.valueOf(idADM));
                parameters.put("ListeIdCompetences", new JSONArray(ListeidCompetences).toString());
                ApiRecherchePost apiRecherche = new ApiRecherchePost(URL_BASE+"/modify/CompActiviteDuMoment", parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        duMoment.endResultCompApi();
                    }
                });
            }
        }).start();
    }
    public void getCompetanceActivite(String token, int idActivite, Activite activite, ModifyActivite modifyActivite){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("getCompetanceActivite", "start ");

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idActivite", String.valueOf(idActivite));
                ApiRecherchePost apiRecherche = new ApiRecherchePost(URL_BASE+"/get/competanceActivite", parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray json = jsonReslt.getJSONObject("resultat").getJSONArray("resultat");
                            ArrayList<Competence> competences = new ArrayList<>();

                            for (int i = 0; i < json.length(); i++) {
                                competences.add(new Competence(json.getJSONObject(i).getInt("id"), json.getJSONObject(i).getString("nom"), json.getJSONObject(i).getString("description")));
                            }


                            Log.i("getCompetanceActivite", json.toString());
                            activite.fillCompetence(competences);
                            modifyActivite.resultApiCompt();
                        } catch (JSONException e) {
                        }

                    }
                });
            }
            }).start();
    }

    public void ModifyActivite(String token, Activite activite, ModifyActivite modifyActivite){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("ModifyActivite", "start ");

                JSONArray jsonArray = new JSONArray();

                for (Competence competence : activite.getCompetences()){
                    jsonArray.put(competence.getId());
                }

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idAct", String.valueOf(activite.getId()));
                parameters.put("nom", String.valueOf(activite.getNom()));
                parameters.put("desc", String.valueOf(activite.getDesc()));
                parameters.put("ListeIdCompetences", jsonArray.toString());
                ApiRecherchePost apiRecherche = new ApiRecherchePost(URL_BASE+"/modify/Activite", parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        modifyActivite.resultatModifyActivityAPI();
                    }
                });
            }
        }).start();
    }
    public void NewActivite(String token, String nom, String desc,int idIME,JSONArray ListeIdCompetences, ModifyActivite modifyActivite){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("NewActivite", "start ");

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("nom", nom);
                parameters.put("desc", desc);
                parameters.put("idIME", String.valueOf(idIME));
                parameters.put("ListeIdCompetences", ListeIdCompetences.toString());
                ApiRecherchePost apiRecherche = new ApiRecherchePost(URL_BASE+"/add/activite", parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        modifyActivite.resultatNewActivityAPI();
                    }
                });
            }
        }).start();
    }

    public void getActivitesPlayed(String token, int idEleve, String dateDebut, String dateFin, EleveDetail eleveDetail,ModelEducateur modelEducateur){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("NewActivite", "start ");

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idEleve", String.valueOf(idEleve));
                Log.e("ALED", "if not passed " );
                if(!(dateDebut == null || dateFin==null)){
                    Log.e("ALED", "if passed " );
                    parameters.put("start_date", dateDebut);
                    parameters.put("end_date", dateFin);
                }
                ApiRecherchePost apiRecherche = new ApiRecherchePost(URL_BASE+"/get/getActivitesPlayed", parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<ActiviteDuMoment> activiteDuMoments =new ArrayList<>();
                            JSONArray jsonArray = jsonReslt.getJSONObject("resultat").getJSONArray("resultat");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Activite activite = new Activite(jsonArray.getJSONObject(i).getInt("idActivite"),jsonArray.getJSONObject(i).getString("nom"),jsonArray.getJSONObject(i).getString("description"));
                                activiteDuMoments.add(new ActiviteDuMoment(jsonArray.getJSONObject(i).getInt("idADM"),activite,jsonArray.getJSONObject(i).getString("dateHeure"),modelEducateur));
                            }

                            eleveDetail.resultatAPIGetADM(activiteDuMoments);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    public void getEleveADM(String token, int idADM, ActiviteDuMoment activiteDuMoment){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("NewActivite", "start ");

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idActiviteDuMoment", String.valueOf(idADM));
                ApiRecherchePost apiRecherche = new ApiRecherchePost(URL_BASE+"/get/activiteDuMoment/eleves", parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Integer> idEleve = new ArrayList<>();
                        try {
                            JSONArray jsonArray = jsonReslt.getJSONObject("resultat").getJSONArray("resultat");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                idEleve.add(jsonArray.getInt(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        activiteDuMoment.resultApiGetEleve(idEleve);
                    }
                });
            }
        }).start();
    }

    public void modifyCompetenceCategorie(String token, int idComp, ArrayList<Integer> listCategorie){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("modifCompCat", "start ");

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idComp", Integer.toString(idComp));
                parameters.put("listCat", new JSONArray(listCategorie).toString());
                ApiRecherchePost apiRecherche = new ApiRecherchePost(URL_BASE+"/modify/listeCategorieCompetence", parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
    }

    public void modifyCategorieCompetence(String token, int idCat, ArrayList<Integer> listIdCompentence){
        JSONObject jsonReslt= new JSONObject();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("modifCatComp", "start ");

                Map<String, String> parameters = new HashMap<>();
                parameters.put("token", token);
                parameters.put("idCat", Integer.toString(idCat));
                parameters.put("listComp", new JSONArray(listIdCompentence).toString());
                ApiRecherchePost apiRecherche = new ApiRecherchePost(URL_BASE+"/modify/listeCompetenceCategorie", parameters, jsonReslt);
                apiRecherche.start();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
    }

    public  JSONObject testPost(){
        JSONObject jsonReslt= new JSONObject();
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("name", "Jinu Jawad");
            parameters.put("email", "helloworld@gmail.com");
            parameters.put("CODE", "1111");
            parameters.put("message", "Hello Post Test success");
            ApiRecherchePost apiRecherche=new ApiRecherchePost(URL_BASE+"/add/",parameters,jsonReslt);
            apiRecherche.start();

            jsonReslt= jsonReslt.getJSONObject("resultat");

            return jsonReslt;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
