package com.example.quizapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    // Liste med animals
    private ArrayList<Animal> animals;
    // Liste for spørsmål
    private ArrayList<Question> questions;
    // Deklarerer ImageView som en privat klassevariabel
    private ImageView quizImageView;
    private Button choice1, choice2, choice3;
    private String correctAnswer;
    private int score = 0, attempts = 0;
    private TextView feedbackTextView, scoreTextView;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Henter den globale applikasjonskonteksten
        MyApp app = (MyApp) getApplicationContext();
        // Henter listen med dyr fra MyApp
        animals = app.getAnimals();
        // Genererer spørsmål
        questions = generateQuestions();

        // Initialiserer views
        feedbackTextView = findViewById(R.id.feedbackTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        quizImageView = findViewById(R.id.quizImageView);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);

        setUpQuiz();
    }

    private void setUpQuiz() {
        if (currentQuestionIndex >= questions.size()) {
            feedbackTextView.setText("Quiz completed!"); // Når alle spørsmålene er besvart
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex);

        // Oppdaterer UI med spørsmål
        feedbackTextView.setText(currentQuestion.getQuestion());
        scoreTextView.setText("Score: " + score + "/ Forsøk: " + attempts);

        // Setter opp svaralternativer
        List<String> choices = new ArrayList<>();
        Collections.addAll(choices, currentQuestion.getOptions());
        String correctAnswer = currentQuestion.getCorrectAnswer(); // Lagrer det riktige svaret for senere sjekk

        // Blander svarene for å vise dem i tilfeldig rekkefølge
        Collections.shuffle(choices);

        // Setter valgene til knappene
        choice1.setText(choices.get(0));
        choice2.setText(choices.get(1));
        choice3.setText(choices.get(2));

        // Setter opp klikkelytter for svaralternativene
        choice1.setOnClickListener(view -> checkAnswer(choice1.getText().toString(), correctAnswer));
        choice2.setOnClickListener(view -> checkAnswer(choice2.getText().toString(), correctAnswer));
        choice3.setOnClickListener(view -> checkAnswer(choice3.getText().toString(), correctAnswer));

        // Viser bilde relatert til spørsmålet
        displayImage(currentQuestionIndex);
    }

    private void checkAnswer(String selectedAnswer, String correctAnswer) {
        attempts++;

        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            feedbackTextView.setText("Riktig!");
        } else {
            feedbackTextView.setText("Feil! Riktig svar er: " + correctAnswer);
        }

        // Går videre til neste spørsmål etter en kort forsinkelse
        feedbackTextView.postDelayed(() -> {
            currentQuestionIndex++;
            setUpQuiz();
        }, 1500);
    }

    // Genererer en liste med spørsmål basert på bildene i galleriet
    private ArrayList<Question> generateQuestions() {
        ArrayList<Question> generatedQuestions = new ArrayList<>();
        for (Animal animal : animals) {
            String question = "Hva er navnet på dette dyret?";
            String correctAnswer = animal.getName();
            String[] options = new String[]{correctAnswer, "Option 1", "Option 2"}; // Tilfeldige alternativer
            generatedQuestions.add(new Question(question, correctAnswer, options));
        }
        Collections.shuffle(generatedQuestions); // Blander spørsmålene
        return generatedQuestions;
    }

    // Viser bildet knyttet til spørsmålet
    private void displayImage(int questionIndex) {
        Animal currentAnimal = animals.get(questionIndex);
        Glide.with(this)
                .load(currentAnimal.getImageUri())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("QuizActivity", "Failed to load image", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("QuizActivity", "Image loaded successfully");
                        return false;
                    }
                })
                .into(quizImageView);
    }
}
