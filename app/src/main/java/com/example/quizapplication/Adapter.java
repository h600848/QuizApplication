package com.example.quizapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Context context;
    ArrayList<Animal> animals;
    LayoutInflater inflater;

    // Konstruktør for adapteren
    public Adapter(Context ctx, ArrayList<Animal> animals) {
        this.context = ctx;
        this.animals = animals;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int position) {
        // Returnerer objektet på angitte posisjonen
        return animals.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Returnerer posisjonen som ID siden hver posisjon er unik
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sjekk om den gjenbrukbare visningen er null. Hvis den er det, blås den opp.
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_view, parent, false);
        }

        TextView txtView = convertView.findViewById(R.id.textView);
        ImageView animalImg = convertView.findViewById(R.id.imageView);

        Animal animal = animals.get(position);
        txtView.setText(animal.getName());

        // Bruker Glide for å laste inn bildet fra URI
        if (animal.getImageUri() != null) {
            Glide.with(context)
                    .load(animal.getImageUri())
                    .into(animalImg);
        } else {
            // Håndter tilfeller hvor det kanskje ikke er en bilde-URI
            // For eksempel, sett et standardbilde eller la bildet være tomt
        }

        return convertView;
    }
}
