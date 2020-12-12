package com.lblzr.cookbookplus.helpers;

import android.content.Context;

import com.lblzr.cookbookplus.models.Recipe;

import java.util.ArrayList;

public class RecipeStore {
    private static Recipe currentRecipe;
    private static ArrayList<Recipe> recipes;

    public static void setCurrentRecipe(Recipe r) {
        currentRecipe = r;
    }

    public static Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public static void addRecipe(Recipe r) {
        recipes.add(r);
    }

    public static void removeRecipe(Recipe r) {
        recipes.remove(r);
    }

    public static Recipe getRecipe(int index) {
        return recipes.get(index);
    }

    public static ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public static void loadRecipes(Context c) {
        recipes = RecipeSerializer.LoadAll(c);
    }
}
