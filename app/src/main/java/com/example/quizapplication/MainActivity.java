package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finner gallery_button
        Button gallery = findViewById(R.id.gallery_button);
        // Finner quiz_button
        Button quiz = findViewById(R.id.quiz_button);

        // Setter opp klikkelytter på gallery_button
        gallery.setOnClickListener(v -> {
            // Oppretter en ny Intent for å starte GalleryActivity
            Intent intent = new Intent(this, GalleryActivity.class);
            // Starter aktiviteten med opprettet intent
            startActivity(intent);
        });

        // Setter opp klikkelytter på quiz_button
        quiz.setOnClickListener(v -> {
            // Oppretter en ny Intent for å starte QuizActivity
            Intent intent = new Intent(this, QuizActivity.class);
            // Starter aktiviteten med opprettet intent
            startActivity(intent);
        });
    }
}