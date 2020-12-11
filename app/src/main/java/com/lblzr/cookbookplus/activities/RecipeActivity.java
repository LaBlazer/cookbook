package com.lblzr.cookbookplus.activities;

import android.Manifest;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.fragments.RecipeIngredientsFragment;
import com.lblzr.cookbookplus.fragments.RecipeStepFragment;
import com.lblzr.cookbookplus.models.AmountUnit;
import com.lblzr.cookbookplus.models.Ingredient;
import com.lblzr.cookbookplus.models.Recipe;
import com.lblzr.cookbookplus.models.Step;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import java.util.ArrayList;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class RecipeActivity extends AppCompatActivity {

    final int REQUEST_CODE_STORAGE = 666;

    private int currentStep;
    private Recipe recipe;
    private ArrayList<Fragment> fragments;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initActionBar();
        fab = findViewById(R.id.fabRecipe);

        if(getIntent().getSerializableExtra("recipe") != null) {
            recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        } else {
            Snackbar.make(fab, "Invalid activity extras",
                    Snackbar.LENGTH_LONG).show();
            recipe = new Recipe("Fajnovy receptik", "test.jpg");

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

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                    ft.replace(R.id.flRecipe, fragments.get(currentStep));
                    ft.addToBackStack("Step " + currentStep);
                    ft.commit();
                    updateTitle();
                }
            }
        });

        updateTitle();
    }

    private void initActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbarRecipe);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentStep--;
        updateTitle();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void updateTitle() {
        setTitle(currentStep == 0 ? recipe.getName() : "Step " + currentStep);

        if(currentStep == recipe.getStepAmount()) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }
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