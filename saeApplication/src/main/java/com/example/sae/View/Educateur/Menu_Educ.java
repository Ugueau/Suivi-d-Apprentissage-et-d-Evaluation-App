package com.example.sae.View.Educateur;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sae.API.DataBase;
import com.example.sae.Model.ModelEducateur;
import com.example.sae.Oberveur.Observer;
import com.example.sae.R;
import com.example.sae.databinding.ActivityMenuEducBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Menu_Educ extends AppCompatActivity implements Observer {


    private TextView titre;
    private ConstraintLayout profilPop;
    private Spinner spinnerProfilIME;
    private TextView txtProfilNameIME;
    private TextView txtnomPrenomProfil;
    private TextView txtTypeProfil;
    private BottomNavigationView bottomNavigationView;

    private ModelEducateur modelEducateur;
    private List<Integer> ListeIdIME;

    private AccueilFragment accueilFragment;
    private ActiviteFragment activiteFragment;
    private categorieFragment categorieFragment;
    private EleveFragment eleveFragment;


    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.sae.databinding.ActivityMenuEducBinding binding = ActivityMenuEducBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Bundle b = getIntent().getExtras();
        if(b != null)
            token = b.getString("token");


        modelEducateur = new ModelEducateur(Menu_Educ.this);
        modelEducateur.addObserver(this);

        accueilFragment = new AccueilFragment(modelEducateur);
        activiteFragment= new ActiviteFragment(modelEducateur);
        categorieFragment = new categorieFragment(modelEducateur, this);
        eleveFragment = new EleveFragment(modelEducateur, this);




        /**************************************************************************************
         * toolbar
         ****************************************************************************************/

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        /**************************************************************************************
         * toolbar
         ****************************************************************************************/



        profilPop = findViewById(R.id.PopEducateurRecherche);
        ImageButton buttonProfilFerme = findViewById((R.id.buttonProfilFerme));
        ImageButton menuButton = findViewById(R.id.buttonMenu);
        titre = findViewById(R.id.TitreNav);
        Button buttonProfilDeconnexion = findViewById(R.id.buttonProfilDeconnexion);
        spinnerProfilIME = findViewById(R.id.spinnerProfilIME);
        txtProfilNameIME = findViewById(R.id.txtProfilNameIME);
        txtnomPrenomProfil =findViewById(R.id.txtnomPrenomProfil);
        txtTypeProfil = findViewById(R.id.txtTypeProfil);
        ConstraintLayout backgpopProfil = findViewById(R.id.backgpopProfil);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ImageButton imageButtonRefresh = findViewById(R.id.imageButtonRefresh);





        /**************************************************************************************
         * navBarBottom
         ****************************************************************************************/

        titre.setText("Accueil");

        replaceFragment(accueilFragment);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_educateur_accueil:
                        titre.setText("Accueil");
                        replaceFragment(accueilFragment);
                        break;
                    case R.id.navigation_educateur_eleves:
                        titre.setText("Élèves");
                        replaceFragment(eleveFragment);
                        break;
                    case R.id.navigation_educateur_activites:
                        titre.setText("Activités");
                        replaceFragment(activiteFragment);
                        break;
                    case R.id.navigation_educateur_categories:
                        titre.setText("Catégories");
                        replaceFragment(categorieFragment);
                        break;
                }




                return true;
            }
        });

        /**************************************************************************************
         * navBarBottom
         ****************************************************************************************/
        /**************************************************************************************
         * Listener
         ****************************************************************************************/

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilPop.setVisibility(View.VISIBLE);

            }
        });
        buttonProfilFerme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilPop.setVisibility(View.GONE);
            }
        });
        buttonProfilDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase dbHelper = new DataBase(Menu_Educ.this);
                dbHelper.reset();
                finish();
            }
        });
        spinnerProfilIME.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                if(ListeIdIME.get(position)!=modelEducateur.getPersonne().getIdIMESelected()){
                    String item = parent.getItemAtPosition(position).toString();
                    modelEducateur.setidIMESelected(ListeIdIME.get(position));
                    //Log.i("onItemSelected", "position:"+position+" id:"+id+" valeur:"+item);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        backgpopProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilPop.setVisibility(View.GONE);
            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_Layout_Nav);
                if (fragment instanceof AccueilFragment) {
                    Menu menu = bottomNavigationView.getMenu();
                    MenuItem item = menu.getItem(0);
                    item.setChecked(true);
                    titre.setText(R.string.title_accueil);
                } else if (fragment instanceof ActiviteFragment) {
                    Menu menu = bottomNavigationView.getMenu();
                    MenuItem item = menu.getItem(2);
                    item.setChecked(true);
                    titre.setText(R.string.title_activites);
                } else if (fragment instanceof categorieFragment) {
                    Menu menu = bottomNavigationView.getMenu();
                    MenuItem item = menu.getItem(3);
                    item.setChecked(true);
                    titre.setText(R.string.title_categories);
                } else if (fragment instanceof EleveFragment){
                    Menu menu = bottomNavigationView.getMenu();
                    MenuItem item = menu.getItem(1);
                    item.setChecked(true);
                    titre.setText(R.string.title_eleves);
                }else if (fragment instanceof DuMoment){
                    replaceFragment(accueilFragment);
                }
                else if (fragment instanceof ModifyActivite){
                    replaceFragment(activiteFragment);
                }
            }
        });

        imageButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair<AccueilFragment,Integer> pair = new Pair<>(null,null);
                modelEducateur.MettreAJourStockage(pair);
            }
        });


        /**************************************************************************************
         * Listener
         ****************************************************************************************/

        Log.i("INFORMATION", "stop loading");
    }



    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout_Nav,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }




    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count > 0) {
            fm.popBackStack();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void update() {

        txtnomPrenomProfil.setText(String.format("%s %s", modelEducateur.getPersonne().getPrenom(), modelEducateur.getPersonne().getNom()));
        txtTypeProfil.setText(modelEducateur.getPersonne().getType());
        /***************************************************************************************
         * Spinner
         ****************************************************************************************/
        // Spinner Drop down elements
        List<String> listeNameIME = new ArrayList<>();
        ListeIdIME = new ArrayList<>();
        Map<Integer,String> ListeIME=modelEducateur.getPersonne().getListeIME();
        for (Map.Entry<Integer, String> pair : ListeIME.entrySet()) {
            listeNameIME.add(pair.getValue());
            ListeIdIME.add(pair.getKey());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeNameIME);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerProfilIME.setAdapter(dataAdapter);
        spinnerProfilIME.setSelection(ListeIdIME.indexOf(modelEducateur.getPersonne().getIdIMESelected()));

        /**************************************************************************************
         * Spinner
         ****************************************************************************************/
        //update TextView avec le nom de l'ime dans la partie profil
        txtProfilNameIME.setText(modelEducateur.getPersonne().getListeIME().get(modelEducateur.getPersonne().getIdIMESelected()));
        //Log.i("Update", "Update");
    }
}