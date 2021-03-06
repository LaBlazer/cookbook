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
import com.lblzr.cookbookplus.models.Step;

public class RecipeStepFragment extends Fragment {

    private Step step;
    private Recipe recipe;
    private int stepNumber;

    public RecipeStepFragment(Recipe recipe, int step) {
        super(R.layout.fragment_recipe_step);

        this.step = recipe.getStep(step);
        this.recipe = recipe;
        this.stepNumber = step;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load step image
        if(step.hasImage()) {
            ImageView image = (ImageView) view.findViewById(R.id.imageStep);
            image.setImageBitmap(FileHelper.getBitmapFromBase64(step.getImage()));
        }

        // Load step title and description
        TextView title = (TextView) view.findViewById(R.id.textStepTitle);
        title.setText(step.getName());
        TextView text = (TextView) view.findViewById(R.id.textStep);
        text.setText(step.getDescription());

    }

}