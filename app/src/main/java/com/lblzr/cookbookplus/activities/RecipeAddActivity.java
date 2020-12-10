package com.lblzr.cookbookplus.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.lblzr.cookbookplus.BuildConfig;
import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.models.Ingredient;
import com.lblzr.cookbookplus.models.Step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeAddActivity extends AppCompatActivity {

    final int REQUEST_CODE_CAMERA = 420;

    private ArrayList<Step> steps;
    private ArrayList<Ingredient> ingredients;

    FloatingActionButton fab;
    ImageButton btnRecipeImage;
    TextInputEditText txtInputTime;
    TextInputEditText txtInputName;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        initActionBar();
        fab = findViewById(R.id.fabDoneRecipe);

        if(getIntent().getSerializableExtra("recipe") != null) {
            //recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        } else {
            //Snackbar.make(fab, "Invalid activity extras",
            //        Snackbar.LENGTH_LONG).show();

        }

        btnRecipeImage = findViewById(R.id.buttonAddPhoto);
        txtInputTime = findViewById(R.id.inputRecipeTime);
        txtInputName = findViewById(R.id.inputRecipeName);

        txtInputTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN ) {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(RecipeAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                List<ResolveInfo> resolvedIntentActivities = getBaseContext().getPackageManager().queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                    String packageName = resolvedIntentInfo.activityInfo.packageName;
                    getBaseContext().grantUriPermission(packageName, Uri.parse(Environment.getStorageDirectory() + "test.jpg"), Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
            }
        });

        // Switch step on FAB press
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(currentStep < recipe.getStepAmount()) {
//                    // switch step
//                    currentStep++;
//
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//                    ft.replace(R.id.flRecipe, fragments.get(currentStep));
//                    ft.addToBackStack("Step " + currentStep);
//                    ft.commit();
//                    updateTitle();
//                }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CBP", "CAMERAAAAAAAAAAA");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            Snackbar.make(fab, "Photo taken", Snackbar.LENGTH_SHORT).show();

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            btnRecipeImage.setImageBitmap(photo);
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