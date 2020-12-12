package com.lblzr.cookbookplus.models;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Recipe implements Serializable {

    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private String name;
    private String image;
    private int duration;

    private File file;

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
        this(name, ingredients, steps, image, duration, null);
    }

    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, String image,
                  int duration, File file) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.image = image;
        this.duration = duration;
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
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
        return image != null && !image.isEmpty();
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public int getDuration() {
        return duration;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) &&
                Objects.equals(file.getName(), recipe.file.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, file.getName());
    }
}
