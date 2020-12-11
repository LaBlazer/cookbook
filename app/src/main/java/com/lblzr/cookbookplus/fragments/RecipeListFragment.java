package com.lblzr.cookbookplus.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.helpers.FileHelper;
import com.lblzr.cookbookplus.helpers.RecipesArrayAdapter;
import com.lblzr.cookbookplus.models.Recipe;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment {
    public interface RecipeSelectedListener {
        void onRecipeSelected(Recipe recipe);
        void onRecipeClicked(Recipe recipe);
        void onRecipeDeselected(Recipe recipe);
    }

    RecipeSelectedListener selectedListener;
    ListView list;
    ArrayList<Recipe> recipes = new ArrayList<>();
    RecipesArrayAdapter arrayAdapter;
    long checkedItem;

    public RecipeListFragment() {
        super(R.layout.fragment_recipe_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof RecipeSelectedListener)
            selectedListener = (RecipeSelectedListener) getActivity();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrayAdapter = new RecipesArrayAdapter(getContext(), recipes);

        list = view.findViewById(R.id.listRecipes);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setItemsCanFocus(false);
        list.setAdapter(arrayAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                if (list.isItemChecked(pos)) {
                    list.setItemChecked(pos,false);
                    checkedItem = -1;

                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeDeselected(recipes.get(pos));

                } else {
                    list.setItemChecked(pos,true);
                    checkedItem = pos;

                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeSelected(recipes.get(pos));

                }

                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int pos, long id) {
                list.setItemChecked(pos, false);

                if (checkedItem == -1) {
                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeClicked(recipes.get(pos));
                } else {
                    checkedItem = -1;
                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeDeselected(recipes.get(pos));

                }

            }
        });
    }

}
