package com.lblzr.cookbookplus.models;

import java.util.ArrayList;

public class Recipe {

    ArrayList<Ingredient> ingredients;
    ArrayList<Step> steps;

    public Recipe() {
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public Recipe(ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
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

    public String getStepDescription(int step) {
        return steps.get(step).getDescription();
    }

    public String getStepName(int step) {
        return steps.get(step).getName();
    }

}
