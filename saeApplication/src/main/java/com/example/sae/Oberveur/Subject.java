package com.example.sae.Oberveur;

import com.example.sae.Oberveur.Observer;

import java.util.ArrayList;

public interface Subject {

        //method to notify observers of change
        public void notifyObservers();

        public void addObserver(Observer observer);
        public void removeObserver(Observer observer);

        //method to get updates from subject
        public Object getUpdate(Observer obj);

}
