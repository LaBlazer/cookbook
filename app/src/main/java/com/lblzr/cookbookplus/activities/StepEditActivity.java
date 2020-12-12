package com.lblzr.cookbookplus.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

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

import static com.lblzr.cookbookplus.helpers.FileHelper.getBase64FromBitmap;

public class StepEditActivity extends AppCompatActivity {

    final int REQUEST_CODE_CAMERA = 420;

    private ArrayList<Step> steps;
    private ArrayList<Ingredient> ingredients;

    FloatingActionButton fab;
    ImageButton btnStepImage;
    TextInputEditText txtInputDescription;
    TextInputEditText txtInputName;

    File photoFile;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_step);
        initActionBar();

        fab = findViewById(R.id.fabDoneStep);
        btnStepImage = findViewById(R.id.buttonAddPhoto);
        txtInputDescription = findViewById(R.id.inputStepDescription);
        txtInputName = findViewById(R.id.inputStepName);

        btnStepImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

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
                        Uri photoURI = FileHelper.getUri(getApplicationContext(), photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtInputName.getText().toString().isEmpty() && !txtInputDescription.getText().toString().isEmpty()) {
                    Intent data = getIntent();
                    data.putExtra("name", txtInputName.getText().toString());
                    data.putExtra("image", photoFile != null ? photoFile.getName() : "");
                    data.putExtra("description", txtInputDescription.getText().toString());
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    Snackbar.make(fab, "Some fields are empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CBP", "Activity result");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK && photoFile != null) {
            btnStepImage.setPadding(0, 0, 0, 0);
            btnStepImage.setImageBitmap(FileHelper.getBitmap(photoFile));
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