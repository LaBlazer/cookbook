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
import com.lblzr.cookbookplus.models.Step;

import java.io.File;

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
            File imgFile = new File(FileHelper.getFullPath(step.getImage()));
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ImageView image = (ImageView) view.findViewById(R.id.imageStep);
                image.setImageBitmap(myBitmap);
            }
        }

        // Load step title and description
        TextView title = (TextView) view.findViewById(R.id.textStepTitle);
        title.setText(step.getName());
        TextView text = (TextView) view.findViewById(R.id.textStep);
        text.setText(step.getDescription());

    }
}