package com.lblzr.cookbookplus.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.fragments.AboutFragment;
import com.lblzr.cookbookplus.fragments.RecipeListFragment;
import com.lblzr.cookbookplus.fragments.SettingsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecipeListFragment.RecipeSelectedListener {

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

        toolbarSelect = findViewById(R.id.toolbarSelect);
        toolbarDefault = findViewById(R.id.toolbarDefault);
        setSupportActionBar(toolbarDefault);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeListFragment.addRecipe("Recipe " + ++id);
                Snackbar.make(view, "Recipe added", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbarDefault,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null) {
            recipeListFragment = new RecipeListFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.flMain, recipeListFragment).commit();
            navigationView.setCheckedItem(R.id.nav_recipes);
            setTitle("Recipes");
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.flMain,
                        recipeListFragment).commit();
                setTitle("Recipes");
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.flMain,
                        new SettingsFragment()).commit();
                setTitle("Settings");
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.flMain,
                        new AboutFragment()).commit();
                setTitle("About");
                break;
        }

//        Snackbar.make(fab, item.getTitle(), Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRecipeSelected(int id) {
        toolbarDefault.setVisibility(View.GONE);
        toolbarSelect.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecipeClicked(int id) {
        Snackbar.make(fab, "Clicked " + id, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void onRecipeDeselected(int id) {
        toolbarDefault.setVisibility(View.VISIBLE);
        toolbarSelect.setVisibility(View.GONE);
    }

    //    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}