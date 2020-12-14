package com.lblzr.cookbookplus.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.lblzr.cookbookplus.R;

public class SettingsFragment extends Fragment {
    TextInputEditText txtEmail;
    TextInputEditText txtPassword;

    SharedPreferences preferences;

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getActivity().getSharedPreferences(
                "com.lblzr.cookbook.settings", Context.MODE_PRIVATE);

        txtEmail = view.findViewById(R.id.inputUsername);
        txtPassword = view.findViewById(R.id.inputPassword);

        txtEmail.setText(getSharedPref("email"));
        txtPassword.setText(getSharedPref("password"));

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                setSharedPref("email", s.toString());
            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                setSharedPref("password", s.toString());
            }
        });

    }

    private void setSharedPref(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getSharedPref(String key) {
        return preferences.getString(key, "");
    }
}
