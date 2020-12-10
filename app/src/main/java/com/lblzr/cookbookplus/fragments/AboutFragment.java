package com.lblzr.cookbookplus.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lblzr.cookbookplus.R;

public class AboutFragment extends Fragment {
    ListView list;

    public AboutFragment() {
        super(R.layout.fragment_about);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
