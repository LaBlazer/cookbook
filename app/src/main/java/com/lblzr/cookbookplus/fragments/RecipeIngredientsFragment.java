package com.lblzr.cookbookplus.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.helpers.FileHelper;
import com.lblzr.cookbookplus.models.Recipe;

public class RecipeIngredientsFragment extends Fragment {

    private Recipe recipe;

    public RecipeIngredientsFragment(Recipe recipe) {
        super(R.layout.fragment_recipe_ingredients);

        this.recipe = recipe;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load recipe image
        if(recipe.hasImage()) {
            ImageView image = (ImageView) view.findViewById(R.id.imageIngredient);
            image.setImageBitmap(FileHelper.getBitmapFromBase64(recipe.getImage()));
        }

        // Load recipe ingredients
        TextView text = (TextView) view.findViewById(R.id.textIngredient);
        text.setText(recipe.getIngredientsString());
    }
}