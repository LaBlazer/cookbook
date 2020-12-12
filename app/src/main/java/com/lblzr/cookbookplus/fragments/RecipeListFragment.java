package com.lblzr.cookbookplus.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lblzr.cookbookplus.R;
import com.lblzr.cookbookplus.helpers.RecipeStore;
import com.lblzr.cookbookplus.helpers.RecipesArrayAdapter;
import com.lblzr.cookbookplus.models.Recipe;

public class RecipeListFragment extends Fragment {
    public interface RecipeSelectedListener {
        void onRecipeSelected(Recipe recipe);
        void onRecipeClicked(Recipe recipe);
        void onRecipeDeselected(Recipe recipe);
    }

    RecipeSelectedListener selectedListener;
    ListView list;
    RecipesArrayAdapter arrayAdapter;
    long checkedItem = -1;

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
        RecipeStore.addRecipe(recipe);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load saved recipes
        RecipeStore.loadRecipes(getContext());
        arrayAdapter = new RecipesArrayAdapter(getContext(), RecipeStore.getRecipes());

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
                        selectedListener.onRecipeDeselected(RecipeStore.getRecipe(pos));

                } else {
                    list.setItemChecked(pos,true);
                    checkedItem = pos;

                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeSelected(RecipeStore.getRecipe(pos));

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
                        selectedListener.onRecipeClicked(RecipeStore.getRecipe(pos));
                } else {
                    checkedItem = -1;
                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeDeselected(RecipeStore.getRecipe(pos));

                }

            }
        });
    }

}
