package com.lblzr.cookbookplus.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.helpers.FileHelper;
import com.lblzr.cookbookplus.models.Recipe;

import java.io.File;

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
            image.setImageBitmap(FileHelper.getBitmap(recipe.getImage()));
        }

        // Load recipe ingredients
        TextView text = (TextView) view.findViewById(R.id.textIngredient);
        text.setText(recipe.getIngredientsString());
    }
}