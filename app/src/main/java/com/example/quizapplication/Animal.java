package com.example.quizapplication;

import android.content.Context;
import android.net.Uri;

public class Animal {
    String name;
    Uri imageUri;

    // Konstruktør for URI bilder
    public Animal (String name, Uri imageUri) {
        this.name = name;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    // Hjelpemetode for å konvertere drawable ID til URI
    public static Uri getDrawableUri(Context context, int drawableId) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + drawableId);
    }
}
