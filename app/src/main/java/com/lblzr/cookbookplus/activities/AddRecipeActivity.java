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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.helpers.FileHelper;
import com.lblzr.cookbookplus.helpers.IngredientsArrayAdapter;
import com.lblzr.cookbookplus.helpers.StepsArrayAdapter;
import com.lblzr.cookbookplus.models.AmountUnit;
import com.lblzr.cookbookplus.models.Ingredient;
import com.lblzr.cookbookplus.models.Recipe;
import com.lblzr.cookbookplus.models.Step;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class AddRecipeActivity extends AppCompatActivity {

    final int REQUEST_CODE_CAMERA = 420;
    final int REQUEST_CODE_INGREDIENT = 666;
    final int REQUEST_CODE_STEP = 333;

    private ArrayList<Step> steps;
    private ArrayList<Ingredient> ingredients;

    FloatingActionButton fab;
    ImageButton btnRecipeImage;
    ImageButton btnAddIngredient;
    ImageButton btnAddStep;
    TextInputEditText txtInputTime;
    TextInputEditText txtInputName;
    ListView listSteps;
    ListView listIngredients;

    File photoFile;
    Recipe recipe;
    StepsArrayAdapter stepsAdapter;
    IngredientsArrayAdapter ingredientsAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        initActionBar();

        if(getIntent().getSerializableExtra("recipe") != null) {
            recipe = (Recipe) getIntent().getSerializableExtra("recipe");
            steps = recipe.getSteps();
            ingredients = recipe.getIngredients();
        } else {
            steps = new ArrayList<>();
            ingredients = new ArrayList<>();
        }

        fab = findViewById(R.id.fabDoneRecipe);
        btnRecipeImage = findViewById(R.id.buttonAddPhoto);
        txtInputTime = findViewById(R.id.inputRecipeTime);
        txtInputName = findViewById(R.id.inputRecipeName);
        btnAddIngredient = findViewById(R.id.buttonAddIngredient);
        btnAddStep = findViewById(R.id.buttonAddStep);
        listSteps = findViewById(R.id.listSteps);
        listIngredients = findViewById(R.id.listIngredients);

        stepsAdapter = new StepsArrayAdapter(this, steps);
        listSteps.setAdapter(stepsAdapter);

        ingredientsAdapter = new IngredientsArrayAdapter(this, ingredients);
        listIngredients.setAdapter(ingredientsAdapter);

        txtInputTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN ) {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(AddRecipeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            txtInputTime.setText(String.format(Locale.ENGLISH, "%dh %dm", selectedHour, selectedMinute));
                        }
                    }, 0, 0, true);
                    mTimePicker.setTitle("Select Duration");
                    mTimePicker.show();
                }
                return true;
            }
        });

        btnRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    photoFile = null;
                    try {
                        photoFile = FileHelper.createImageFile(getApplicationContext());
                    } catch (IOException ex) {
                        Log.e("CBP", ex.toString());
                        Snackbar.make(fab, "Error while creating the image", Snackbar.LENGTH_SHORT).show();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(AddRecipeActivity.this,
                                "com.example.android.fileprovider",
                                photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
                    }
                }
            }
        });

        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(getApplicationContext(), AddIngredientActivity.class);
//                data.putExtra("name", txtInputName.getText().toString());
//                data.putExtra("amount", Float.parseFloat(txtInputAmount.getText().toString()));
//                data.putExtra("unit", selectedUnit);
//                data.putExtra("optional", checkOptional.isChecked());
                startActivityForResult(data, REQUEST_CODE_INGREDIENT);
            }
        });

        btnAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(getApplicationContext(), AddStepActivity.class);
//                data.putExtra("name", txtInputName.getText().toString());
//                data.putExtra("amount", Float.parseFloat(txtInputAmount.getText().toString()));
//                data.putExtra("unit", selectedUnit);
//                data.putExtra("optional", checkOptional.isChecked());
                startActivityForResult(data, REQUEST_CODE_STEP);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtInputName.getText().toString().isEmpty() ||
                txtInputTime.getText().toString().isEmpty() ||
                steps.size() == 0 || ingredients.size() == 0) {
                    Snackbar.make(fab, "Some fields are empty", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Do stuff
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CBP", "Activity request " + requestCode + ", result: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA && photoFile != null){
                Snackbar.make(fab, "Photo taken", Snackbar.LENGTH_SHORT).show();

                Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                btnRecipeImage.setPadding(0, 0, 0, 0);
                btnRecipeImage.setImageBitmap(photo);

            } else if(requestCode == REQUEST_CODE_INGREDIENT) {
                Ingredient i = new Ingredient(
                        data.getStringExtra("name"),
                        data.getDoubleExtra("amount", 0),
                        AmountUnit.fromString(data.getStringExtra("unit")),
                        data.getBooleanExtra("optional", false));
                ingredients.add(i);
                ingredientsAdapter.notifyDataSetChanged();

            } else if (requestCode == REQUEST_CODE_STEP) {
                Step s = new Step(
                        data.getStringExtra("name"),
                        data.getStringExtra("description"),
                        data.getStringExtra("image"));
                steps.add(s);
                stepsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
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