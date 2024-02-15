package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class GalleryActivity extends AppCompatActivity {
    // Liste med animals
    ArrayList<Animal> animals;
    // Deklarerer ListView som klassevariabel
    ListView listView;
    // Deklarerer Adapter som klassevariabel
    Adapter adapter;
    // Deklarerer boolsk variabel for å holde styr på stigende eller synktende rekkefølge
    private boolean isSortedAscending = true;
    // Lager en konstant for å identifisere resultatet fra bildevalg aktiviteten.
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Hent listen fra tilpassey MyApp-klasse
        MyApp app = (MyApp) getApplicationContext();
        animals = app.getAnimals();

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
            // Bruker intent for å la brukeren velge et bilde fra mobilen
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

    // Metode for å håndtere resultatet fra bildevalg aktiviteten.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // Ber brukeren om et navn til bildet
            final EditText input = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Add a name for the picture")
                    .setView(input)
                    .setPositiveButton("OK", (dialog, which) -> {
                        String name = input.getText().toString();
                        // Legger til det nye bildet med bildets URI
                        animals.add(new Animal(name, selectedImageUri));
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Lagrer listen over dyr når aktiviteten pauser
        saveAnimals();
    }

    private void saveAnimals() {
        ((MyApp) getApplication()).setAnimals(animals);
    }
}