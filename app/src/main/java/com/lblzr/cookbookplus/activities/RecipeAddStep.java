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

public class RecipeAddStep extends AppCompatActivity {

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

        if(getIntent().getSerializableExtra("recipe") != null) {
            //recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        } else {
            //Snackbar.make(fab, "Invalid activity extras",
            //        Snackbar.LENGTH_LONG).show();

        }

        btnStepImage = findViewById(R.id.buttonAddPhoto);
        txtInputDescription = findViewById(R.id.inputStepDescription);
        txtInputName = findViewById(R.id.inputStepName);

//        txtInputTime.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN ) {
//                    TimePickerDialog mTimePicker;
//                    mTimePicker = new TimePickerDialog(RecipeAddStep.this, new TimePickerDialog.OnTimeSetListener() {
//                        @Override
//                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                            txtInputTime.setText(String.format(Locale.ENGLISH, "%dh %dm", selectedHour, selectedMinute));
//                        }
//                    }, 0, 0, true);
//                    mTimePicker.setTitle("Select Duration");
//                    mTimePicker.show();
//                }
//                return true;
//            }
//        });

        btnStepImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    photoFile = null;
                    try {
                        photoFile = FileHelper.createTempImageFile(getApplicationContext());
                    } catch (IOException ex) {
                        Log.e("CBP", ex.toString());
                        Snackbar.make(fab, "Error while creating the image", Snackbar.LENGTH_SHORT).show();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(RecipeAddStep.this,
                                "com.example.android.fileprovider",
                                photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
                    }
                }

//                List<ResolveInfo> resolvedIntentActivities = getBaseContext().getPackageManager().queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
//                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
//                    String packageName = resolvedIntentInfo.activityInfo.packageName;
//                    getBaseContext().grantUriPermission(packageName, Uri.parse(Environment.getStorageDirectory() + "test.jpg"), Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }
//
//                startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
            }
        });

        // Switch step on FAB press
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(fab, "Done", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CBP", "Activity result");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK && photoFile != null) {
            Snackbar.make(fab, "Photo taken", Snackbar.LENGTH_SHORT).show();

            Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            btnStepImage.setPadding(0, 0, 0, 0);
            btnStepImage.setImageBitmap(photo);
        }
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