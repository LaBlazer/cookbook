package com.lblzr.cookbookplus.helpers;

import com.lblzr.cookbookplus.models.Recipe;

public class RecipeStore {
    private static Recipe currentRecipe;

    public static void setCurrentRecipe(Recipe r) {
        currentRecipe = r;
    }

    public static Recipe getCurrentRecipe() {
        return currentRecipe;
    }
}
