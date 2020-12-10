package com.lblzr.cookbookplus.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lblzr.cookbookplus.R;

public class SettingsFragment extends Fragment {
    ListView list;

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        list = (ListView) view.findViewById(R.id.listRecipes);


    }
}
