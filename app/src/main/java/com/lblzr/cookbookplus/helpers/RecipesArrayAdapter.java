package com.lblzr.cookbookplus.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.models.Recipe;

import java.util.List;

public class RecipesArrayAdapter extends ArrayAdapter<Recipe> {
    public RecipesArrayAdapter(Context context, List<Recipe> recipes) {
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {

        Recipe recipe = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_recipe, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.itemRecipeName);

        name.setText(recipe.getName());

        return convertView;
    }

}
