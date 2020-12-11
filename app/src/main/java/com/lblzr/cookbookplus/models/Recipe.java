package com.lblzr.cookbookplus.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private String name;
    private String image;
    private int duration;

    public Recipe(String name) {
        this(name, new ArrayList<Ingredient>(), new ArrayList<Step>(), null, 0);
    }

    public Recipe(String name, String image) {
        this(name, new ArrayList<Ingredient>(), new ArrayList<Step>(), image, 0);
    }

    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        this(name, ingredients, steps, null, 0);
    }

    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, String image, int duration) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.image = image;
        this.duration = duration;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean hasImage() {
        return !image.isEmpty();
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public String getIngredientsString() {
        StringBuilder text = new StringBuilder();

        for (Ingredient i : ingredients) {
            text.append(i.toString()).append("\n");
        }

        return text.toString();
    }

    public int getStepAmount() {
        return steps.size();
    }

    public Step getStep(int step) {
        return steps.get(step);
    }

    public String getName() {
        return name;
    }
}
