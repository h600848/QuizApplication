package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class GalleryActivity extends AppCompatActivity {
    ArrayList<Animal> animals = new ArrayList<>();
    // Deklarerer ListView som klassevariabel
    ListView listView;
    // Deklarerer Adapter som klassevariabel
    Adapter adapter;
    // Deklarerer boolsk variabel for å holde styr på stigende eller synktende rekkefølge
    private boolean isSortedAscending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Legger til start bilder
        animals.add(new Animal("Polar bear", R.drawable.polar_bear));
        animals.add(new Animal("Gorilla", R.drawable.gorilla));

        // Finner listView, oppretter adapteren og bruker listView på adapteren
        listView = (ListView) findViewById(R.id.customListView);
        adapter = new Adapter(getApplicationContext(), animals);
        listView.setAdapter(adapter);

        // Setter opp itemlytter på listView for å kunne slette bilder når vi trykker på bildet(itemet)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Har laget metode for å be om bekreftelse fra brukeren før bildet slettes
            deletePicture(position);
        });

        // Finner knappene add, delete og sort:
        Button add = findViewById(R.id.add_button);
        Button sort = findViewById(R.id.sort_button);

        // Setter opp klikkelytter på add knappen
        add.setOnClickListener(v -> {
            // Må her implementere logikken til knappen
        });

        // Setter opp klikkelytter på sort knappen
        sort.setOnClickListener(v -> {
            if (isSortedAscending) {
                // Sorterer listen i stigende rekkefølge
                Collections.sort(animals, (x1, x2) -> x1.getName().compareTo(x2.getName()));
                isSortedAscending = false;
            } else {
                // Sorterer listen i synkende rekkefølge
                Collections.sort(animals, (x1, x2) -> x2.getName().compareTo(x1.getName()));
                isSortedAscending = true;
            }
            // Oppdaterer adapteren
            adapter.notifyDataSetChanged();
        });
    }

    // Metode for å be om bekreftelse før bildet slettes.
    private void deletePicture(int position) {
        // Bruker AlertDialog.Builder for å bygge og vise en dialogboks til brukeren
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete picture");
        builder.setMessage("Are you sure you want to delete the picture?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Sletter elementet fra listen
            animals.remove(position);
            // Oppdaterer adapteren
            adapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}