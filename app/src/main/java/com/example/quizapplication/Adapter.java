package com.example.quizapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView txtView = (TextView) convertView.findViewById(R.id.textView);
        ImageView animalImg = (ImageView) convertView.findViewById(R.id.imageView);
        Animal animal = animals.get(position);
        txtView.setText(animal.getName());
        animalImg.setImageResource(animal.getImageId());
        return convertView;
    }
}
