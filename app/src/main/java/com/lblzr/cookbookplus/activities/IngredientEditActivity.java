package com.lblzr.cookbookplus.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.models.AmountUnit;

import java.util.ArrayList;

public class IngredientEditActivity extends AppCompatActivity {

    final int REQUEST_CODE_CAMERA = 420;

    private static final ArrayList<String> units = AmountUnit.toArray();

    FloatingActionButton fab;
    TextInputEditText txtInputAmount;
    TextInputEditText txtInputName;
    CheckBox checkOptional;
    Spinner inputUnit;

    String selectedUnit;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        initActionBar();
        fab = findViewById(R.id.fabDoneIngredient);

        txtInputAmount = findViewById(R.id.inputIngredientAmount);
        txtInputName = findViewById(R.id.inputIngredientName);
        inputUnit = findViewById(R.id.inputIngredientUnit);
        checkOptional = findViewById(R.id.checkboxOptional);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(IngredientEditActivity.this,
                R.layout.item_dropdown, units);

        inputUnit.setAdapter(adapter);
        inputUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUnit = units.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtInputAmount.getText().toString().isEmpty() && !txtInputName.getText().toString().isEmpty()) {
                    Intent data = getIntent();
                    data.putExtra("name", txtInputName.getText().toString());
                    data.putExtra("amount", Double.parseDouble(txtInputAmount.getText().toString()));
                    data.putExtra("unit", selectedUnit);
                    data.putExtra("optional", checkOptional.isChecked());
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    Snackbar.make(fab, "Some fields are empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
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