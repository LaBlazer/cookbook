package com.lblzr.cookbookplus.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.models.Step;

import java.util.List;

public class StepsArrayAdapter extends ArrayAdapter<Step> {
    public StepsArrayAdapter(Context context, List<Step> steps) {
        super(context, 0, steps);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {

        Step step = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_step, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.itemStepName);

        name.setText((position + 1) + ". " + step.getName());

        return convertView;
    }
}
