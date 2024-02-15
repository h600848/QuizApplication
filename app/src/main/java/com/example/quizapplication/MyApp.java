package com.example.quizapplication;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyApp  extends Application {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String ANIMALS_KEY = "AnimalsKey";
    private ArrayList<Animal> animals = new ArrayList<>();
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        gson = new Gson();
        // Gjenoppretter listen over dyr når appen starter
        restoreAnimals();
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    // Lagrer listen over dyr ved hjelp av Gson
    private void saveAnimals() {
        String json = gson.toJson(animals);
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("animals", json);
        editor.apply();
    }

    // Gjenoppretter listen over dyr ved hjelp av Gson
    private void restoreAnimals() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String json = prefs.getString("animals", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Animal>>(){}.getType();
            animals = gson.fromJson(json, type);
        } else {
            // Legg til startbilder hvis listen er tom
            animals.add(new Animal("Polar bear", Animal.getDrawableUri(getApplicationContext(), R.drawable.polar_bear)));
            animals.add(new Animal("Gorilla", Animal.getDrawableUri(getApplicationContext(), R.drawable.gorilla)));
        }
    }
}
