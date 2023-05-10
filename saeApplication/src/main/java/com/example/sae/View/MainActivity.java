package com.example.sae.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sae.API.API;
import com.example.sae.API.DataBase;
import com.example.sae.API.Loading;
import com.example.sae.R;
import com.example.sae.Static;
import com.example.sae.View.Educateur.Menu_Educ;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.DatabaseMetaData;
import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {


    private static final int VERSION = 4;

    private TextInputEditText MainActivityInputLogin;
    private TextInputEditText MainActivityInputMdp;
    private CheckBox MainActivityCheckConnecte;
    private Button MainActivityButtonConnecte;
    private TextView MainActivityTxtError;
    private Loading loading;


    /**
     * creer la popup quand une requete api echoue et l'affiche
     */
    public void resultApiError(){
        loading.hideLoading();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erreur");
        builder.setMessage("Erreur requet");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /**
     * fonction appeller quand l'api a finie
     * @param query resultat de la requete
     * @throws JSONException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setQueryApi(JSONObject query) throws JSONException {

        try {
            if(query.getString("type").equals("educateur")){


                String token = query.getString("token");
                String type = query.getString("type");


                if(MainActivityCheckConnecte.isChecked()){
                    DataBase dbHelper = new DataBase(MainActivity.this);
                    LocalDate date = LocalDate.now();

                    dbHelper.reset();
                    dbHelper.insertOrUpdate(token, date.toString(),type);
                }
                initActiviti(token,type);
            }
            else{
                MainActivityTxtError.setText("Vous n'êtes pas un éducateur.");
                MainActivityTxtError.setVisibility(View.VISIBLE);
                loading.hideLoading();
            }
        } catch (JSONException e) {
            if(query.getInt("error")==13){
                MainActivityTxtError.setText("Vous devez faire votre première connexion.");
                String url = "https://sae-ime.ovh/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
            else{
                MainActivityTxtError.setText("Votre login ou mot de passe sont faux.");
            }

            MainActivityTxtError.setVisibility(View.VISIBLE);
            loading.hideLoading();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        checkVersionApplication();

        MainActivityInputLogin = findViewById(R.id.MainActivityInputLogin);
        MainActivityInputMdp = findViewById(R.id.MainActivityInputMdp);
        MainActivityCheckConnecte = findViewById(R.id.MainActivityCheckConnecte);
        MainActivityButtonConnecte = findViewById(R.id.MainActivityButtonConnecte);
        MainActivityTxtError = findViewById(R.id.MainActivityTxtError);

        loading  = new Loading(this);

        MainActivityInputLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    MainActivityInputMdp.setSelection(0, MainActivityInputMdp.getText().length());
                    return true;
                }
                return false;
            }
        });

        MainActivityInputMdp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    MainActivityButtonConnecte.performClick();
                    return true;
                }
                return false;
            }
        });

        MainActivityButtonConnecte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MainActivityTxtError.setVisibility(View.GONE);
                String txtLogin=MainActivityInputLogin.getText().toString();
                String txtMdp=MainActivityInputMdp.getText().toString();

                if(txtLogin.isEmpty() || txtMdp.isEmpty()){
                    if(txtLogin.isEmpty() && txtMdp.isEmpty()){
                        MainActivityTxtError.setText("Vous devez remplir les champs");
                        MainActivityTxtError.setVisibility(View.VISIBLE);
                    }
                    else if(txtLogin.isEmpty()){
                        MainActivityTxtError.setText("Vous devez remplir le login");
                        MainActivityTxtError.setVisibility(View.VISIBLE);
                    }
                    else {
                        MainActivityTxtError.setText("Vous devez remplir le Mot de passe");
                        MainActivityTxtError.setVisibility(View.VISIBLE);
                    }
                    API api = new API();
                    Log.i("TAG", api.testPost().toString());



                }
                else {



                    InputMethodManager imm = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    Log.i("INFORMATION", "start loading");
                    loading.showLoading();

                    String login= MainActivityInputLogin.getText().toString();
                    String mdp = MainActivityInputMdp.getText().toString();
                    API api = new API();
                    api.queryConnect(login,mdp, MainActivity.this);

                }

            }
        });

    }

    /**
     * initalise l'activite du bon type en envoyen le token recuperer au préalable
     * @param token le token de la personne recuperer grance a son login + mdp
     * @param type
     */
    void initActiviti(String token,String type){
        Log.i("TAG", type);
        switch (type){
            case "educateur":
            case "responsable":
                Intent i = new Intent(MainActivity.this, Menu_Educ.class);
                Bundle b = new Bundle();
                b.putString("token", token);
                i.putExtras(b);
                startActivity(i);
                break;
        }
        loading.hideLoading();

    }

    /**
     * regarde si dans la bdd local il n'y est pas de donnee car si oui on utilise son token pour ce connecter directement
     * car si il y a des donnee dans la bdd local ca veux dire qu'il a checker le rester connecter
     */
    void checkResterConnecte(){
        try {
            DataBase dbHelper = new DataBase(MainActivity.this);
            Cursor cursor = dbHelper.selectAll();

            String token1 = null;
            String dateInit = null;
            String type1 = null;
            while (cursor.moveToNext()) {
                token1 = cursor.getString(cursor.getColumnIndex("token"));
                dateInit = cursor.getString(cursor.getColumnIndex("DateInit"));
                type1 = cursor.getString(cursor.getColumnIndex("type"));
                Log.i("affiche", "token: " + token1 + ", date: " + dateInit);
            }
            cursor.close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate date = LocalDate.parse(dateInit);
                date= date.plusWeeks(1);
                LocalDate dateNow = LocalDate.now();
                if(!(token1.isEmpty())&&dateNow.isBefore(date)){
                    initActiviti(token1,type1);
                }
            }

        }catch (Exception e){

        }
    }

    /**
     * regarder grace a l'api si l'application est a jour sin=on genrer une popup ou il peut telecharger la mise a jour
     */
    void checkVersionApplication(){
        API api = new API();
        api.querryGetVersion(this);
    }
    public void requestApiVersion(int version){
        if(version>VERSION){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Mise à jour nécessaire");
            builder.setMessage("Vous devez mettre à jour votre application");
            builder.setCancelable(false); // Empêche la fermeture de l'AlertDialog en dehors du bouton OK
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = "https://sae-ime.ovh/APK";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        else{
            checkResterConnecte();
        }
    }

}