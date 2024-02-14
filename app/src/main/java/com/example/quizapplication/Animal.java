package com.example.quizapplication;

public class Animal {
    String name;
    int imageId;

    public Animal (String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
