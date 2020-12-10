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

import java.util.ArrayList;

public class RecipeListFragment extends Fragment {
    public interface RecipeSelectedListener {
        void onRecipeSelected(int id);
        void onRecipeClicked(int id);
        void onRecipeDeselected(int id);
    }

    RecipeSelectedListener selectedListener;
    ListView list;
    ArrayList<String> recipes = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
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

    public void addRecipe(String recipe) {
        recipes.add(recipe);
        listAdapter.notifyDataSetChanged();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_activated_1,
                recipes);

        list = view.findViewById(R.id.listRecipes);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setItemsCanFocus(false);
        list.setAdapter(listAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
//                Log.d("CBP", "longclick pos = " + pos + ", id = " + id + " checked " + list.isItemChecked(pos));
//                Log.d("CBP", "Checked " + list.getCheckedItemCount());
//                Log.d("CBP", "Checked " + list.getCheckedItemPosition());

                if (list.isItemChecked(pos)) {
                    list.setItemChecked(pos,false);
                    checkedItem = -1;

                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeDeselected(pos);

                } else {
                    list.setItemChecked(pos,true);
                    checkedItem = pos;

                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeSelected(pos);

                }

                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int pos, long id) {
//                Log.d("CBP", "itemClick: position = " + pos + ", id = " + id + " checked " + list.isItemChecked(pos));
//                Log.d("CBP", "Checked " + list.getCheckedItemCount());
//                Log.d("CBP", "Checked " + list.getCheckedItemPosition());
                list.setItemChecked(pos, false);

                if (checkedItem == -1) {
                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeClicked(pos);
                } else {
                    checkedItem = -1;
                    // callback
                    if(selectedListener != null)
                        selectedListener.onRecipeDeselected(pos);

                }

            }
        });
    }

}
