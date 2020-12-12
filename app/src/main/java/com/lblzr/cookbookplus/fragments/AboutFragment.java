package com.lblzr.cookbookplus.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.lblzr.cookbookplus.R;

public class AboutFragment extends Fragment {

    private int count = 0;

    public AboutFragment() {
        super(R.layout.fragment_about);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtAbout = view.findViewById(R.id.textAbout);
        txtAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(++count == 10) {
                    Snackbar.make(v, "You are now a developer!", Snackbar.LENGTH_LONG)
                            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show();
                }
            }
        });

    }
}
