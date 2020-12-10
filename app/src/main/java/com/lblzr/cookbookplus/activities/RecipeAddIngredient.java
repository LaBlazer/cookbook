package com.lblzr.cookbookplus.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.helpers.FileHelper;
import com.lblzr.cookbookplus.models.Ingredient;
import com.lblzr.cookbookplus.models.Step;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class RecipeAddIngredient extends AppCompatActivity {

    final int REQUEST_CODE_CAMERA = 420;

    private static final String[] units = {"g", "kg", "l", "piece"};

    FloatingActionButton fab;
    TextInputEditText txtInputAmount;
    TextInputEditText txtInputName;
    Spinner inputUnit;

    File photoFile;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        initActionBar();
        fab = findViewById(R.id.fabDoneIngredient);

        if(getIntent().getSerializableExtra("recipe") != null) {
            //recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        } else {
            //Snackbar.make(fab, "Invalid activity extras",
            //        Snackbar.LENGTH_LONG).show();

        }

        txtInputAmount = findViewById(R.id.inputIngredientAmount);
        txtInputName = findViewById(R.id.inputIngredientName);
        inputUnit = findViewById(R.id.inputIngredientUnit);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecipeAddIngredient.this,
                R.layout.dropdown_item, units);

        //inputUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputUnit.setAdapter(adapter);
        //inputUnit.setOnItemSelectedListener(this);


//                List<ResolveInfo> resolvedIntentActivities = getBaseContext().getPackageManager().queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
//                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
//                    String packageName = resolvedIntentInfo.activityInfo.packageName;
//                    getBaseContext().grantUriPermission(packageName, Uri.parse(Environment.getStorageDirectory() + "test.jpg"), Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }
//
//                startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);



        // Switch step on FAB press
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(fab, "Done", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        ActionBar ab = getSupportActionBar();

        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
    }
}