package com.lblzr.cookbookplus.activities;

import android.Manifest;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.fragments.RecipeIngredientsFragment;
import com.lblzr.cookbookplus.fragments.RecipeStepFragment;
import com.lblzr.cookbookplus.models.Ingredient;
import com.lblzr.cookbookplus.models.Recipe;
import com.lblzr.cookbookplus.models.Step;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.View;

import java.util.ArrayList;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class RecipeActivity extends AppCompatActivity {

    final int REQUEST_CODE_STORAGE = 666;

    private int currentStep;
    private Recipe recipe;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = findViewById(R.id.toolbarRecipe);
        FloatingActionButton fab = findViewById(R.id.fabRecipe);
        setSupportActionBar(toolbar);

        if(getIntent().getSerializableExtra("recipe") != null) {
            //recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        } else {
            Snackbar.make(fab, "Invalid activity extras",
                    Snackbar.LENGTH_LONG).show();
            recipe = new Recipe("Fajnovy receptik", "varna.jpg");
            recipe.addIngredient(new Ingredient("sodium hydroxide", 2, Ingredient.AmountUnit.PIECES));
            recipe.addIngredient(new Ingredient("acetone", 0.42, Ingredient.AmountUnit.LITRES));
            recipe.addIngredient(new Ingredient("hydrochloric acid", 1, Ingredient.AmountUnit.LITRES));
            recipe.addIngredient(new Ingredient("sodium hydroxide", 200, Ingredient.AmountUnit.GRAMS));
            recipe.addIngredient(new Ingredient("pseudoephedrine", 2000, Ingredient.AmountUnit.GRAMS));
            recipe.addIngredient(new Ingredient("phosphorus", 6, Ingredient.AmountUnit.CUPS));

            recipe.addStep(new Step("Vsetko zmiesame", "Jebneme to do sklenenej flasky a zmiesame."));
            recipe.addStep(new Step("Vsetko uvarime", "Mmmm, uz se nam to kraaaasne deliii. To bude mnamka."));
            recipe.addStep(new Step("Mame veci", "Daj si piko daj si piko pojde ti karta. Daj si piko daj si piko pojde ti karta. Mnam Mnam. MmmMMMm, ja to pikenko milujem....", "hqdefault (1).jpg"));
        }

        currentStep = 0;

        // Create array of step fragments
        makeFragments();

        // Switch step on FAB press
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentStep < recipe.getStepAmount()) {
                    // switch step
                    currentStep++;

                    Fragment f = fragments.get(currentStep);
                    getSupportFragmentManager().beginTransaction().add(R.id.flRecipe,
                            f).addToBackStack("Step " + currentStep).commit();
                    updateTitle();
                }
            }
        });

        updateTitle();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentStep--;
        updateTitle();
    }

    private void setStep(int step) {


    }

    private void updateTitle() {
        setTitle(currentStep == 0 ? recipe.getName() : "Step " + currentStep);
    }

    @AfterPermissionGranted(REQUEST_CODE_STORAGE)
    private void makeFragments() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {

            fragments = new ArrayList<>();
            fragments.add(new RecipeIngredientsFragment(recipe));
            for(int i = 0; i < recipe.getStepAmount(); i++) {
                fragments.add(new RecipeStepFragment(recipe, i));
            }

            // Display ingredients list by default
            getSupportFragmentManager().beginTransaction().add(R.id.flRecipe, fragments.get(0)).commit();

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.storage_permissions_rationale),
                    REQUEST_CODE_STORAGE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}