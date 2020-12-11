package com.lblzr.cookbookplus.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.models.Ingredient;

import java.util.List;

public class IngredientsArrayAdapter extends ArrayAdapter<Ingredient> {
    public IngredientsArrayAdapter(Context context, List<Ingredient> ingredients) {
        super(context, 0, ingredients);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {

        Ingredient ingredient = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.itemIngredientName);

        name.setText(ingredient.toString());

        return convertView;
    }

}
