package com.lblzr.cookbookplus.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.fragments.AboutFragment;
import com.lblzr.cookbookplus.fragments.RecipeListFragment;
import com.lblzr.cookbookplus.fragments.SettingsFragment;
import com.lblzr.cookbookplus.helpers.FileHelper;
import com.lblzr.cookbookplus.helpers.RecipeSerializer;
import com.lblzr.cookbookplus.helpers.RecipeStore;
import com.lblzr.cookbookplus.models.Ingredient;
import com.lblzr.cookbookplus.models.Recipe;
import com.lblzr.cookbookplus.models.Step;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecipeListFragment.RecipeSelectedListener {

    private int REQUEST_CODE_RECIPE = 111;

    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private Toolbar toolbarDefault;
    private Toolbar toolbarSelect;
    private RecipeListFragment recipeListFragment;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Delete old images
        FileHelper.deleteFiles(getApplicationContext(), "jpg");

        toolbarSelect = findViewById(R.id.toolbarSelect);
        toolbarDefault = findViewById(R.id.toolbarDefault);
        setSupportActionBar(toolbarDefault);

        // Update username
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView txtEmail = header.findViewById(R.id.textUsername);
        SharedPreferences sp = getSharedPreferences(
                "com.lblzr.cookbook.settings", Context.MODE_PRIVATE);
        txtEmail.setText(sp.getString("email", "Unregistered"));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recipeListFragment.addRecipe("Recipe " + ++id);

                Intent data = new Intent(getApplicationContext(), RecipeEditActivity.class);
//                data.putExtra("name", txtInputName.getText().toString());
//                data.putExtra("amount", Float.parseFloat(txtInputAmount.getText().toString()));
//                data.putExtra("unit", selectedUnit);
//                data.putExtra("optional", checkOptional.isChecked());
                startActivityForResult(data, REQUEST_CODE_RECIPE);
            }
        });

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbarDefault,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            recipeListFragment = new RecipeListFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.flMain, recipeListFragment).commit();
            navigationView.setCheckedItem(R.id.nav_recipes);
            setTitle("Recipes");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RECIPE && resultCode == Activity.RESULT_OK) {

            try {
                // Convert images to base64 and create recipe object

                ArrayList<Step> steps = (ArrayList<Step>) data.getSerializableExtra("steps");
                for(Step s : steps) {
                    s.setImage(FileHelper.getBase64FromBitmap(FileHelper.getBitmap(getApplicationContext(), s.getImage())));
                }

                Recipe r = new Recipe(
                        data.getStringExtra("name"),
                        (ArrayList<Ingredient>) data.getSerializableExtra("ingredients"),
                        steps,
                        FileHelper.getBase64FromBitmap(FileHelper.getBitmap(getApplicationContext(), data.getStringExtra("image"))),
                        data.getIntExtra("duration", 0));
                // Save the recipe and add it to the list
                RecipeSerializer.Save(getApplicationContext(), r);
                recipeListFragment.addRecipe(r);
            } catch (Exception ex) {
                Snackbar.make(fab, "Error while getting recipe", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_recipes:
                fab.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.flMain,
                        recipeListFragment).commit();
                setTitle("Recipes");
                break;
            case R.id.nav_settings:
                fab.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.flMain,
                        new SettingsFragment()).commit();
                setTitle("Settings");
                break;
            case R.id.nav_about:
                fab.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.flMain,
                        new AboutFragment()).commit();
                setTitle("About");
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        toolbarDefault.setVisibility(View.GONE);
        toolbarSelect.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent data = new Intent(getApplicationContext(), RecipeViewActivity.class);
        RecipeStore.setCurrentRecipe(recipe);
        startActivity(data);
    }

    @Override
    public void onRecipeDeselected(Recipe recipe) {
        toolbarDefault.setVisibility(View.VISIBLE);
        toolbarSelect.setVisibility(View.GONE);
    }

}