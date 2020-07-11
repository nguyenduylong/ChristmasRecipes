package com.duylong.christmasrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.duylong.christmasrecipes.adapter.OnRecyclerClickListener;
import com.duylong.christmasrecipes.adapter.RecipeAdapter;
import com.duylong.christmasrecipes.database.DBManager;
import com.duylong.christmasrecipes.fragment.FilterFragment;
import com.duylong.christmasrecipes.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener {

    DBManager dbManager;

    ArrayList<Recipe> recipes;

    RecipeAdapter recipeAdapter;

    RecyclerView recipeListView;

    FloatingActionButton fab;

    FilterFragment filterFragment;

    private ArrayMap<String, List<String>> appliedFilters = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipes = new ArrayList<Recipe>();

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor dataCursor = dbManager.fetch_data();
        do {
            Recipe recipe = createRecipe(dataCursor);
            recipes.add(recipe);
        } while (dataCursor.moveToNext());

        recipeListView = findViewById(R.id.recipe_view);

        recipeAdapter = new RecipeAdapter(this, recipes, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
//                Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
//                startActivity(intent);
            }
        });

        recipeListView.setAdapter(recipeAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recipeListView.setLayoutManager(layoutManager);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        filterFragment = FilterFragment.newInstance();
        filterFragment.setParentFab(fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterFragment.show(getSupportFragmentManager(), filterFragment.getTag());
            }
        });

    }

    private Recipe createRecipe(Cursor cursor) {
        String name = cursor.getString(1);
        String skillLevel = cursor.getString(3);
        Recipe recipe = new Recipe(name, skillLevel);

        recipe.setImageUrl(cursor.getString(2));
        recipe.setDescription(cursor.getString(4));
        recipe.setNutrition(cursor.getString(5));
        recipe.setIngredients(cursor.getString(6));
        recipe.setMethods(cursor.getString(7));

        return recipe;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (filterFragment.isAdded()) {
            filterFragment.dismiss();
            filterFragment.show(getSupportFragmentManager(), filterFragment.getTag());
        }
    }

    public ArrayMap<String, List<String>> getAppliedFilters() {
        return appliedFilters;
    }

    @Override
    public void onOpenAnimationStart() {

    }

    @Override
    public void onOpenAnimationEnd() {

    }

    @Override
    public void onCloseAnimationStart() {

    }

    @Override
    public void onCloseAnimationEnd() {

    }

    @Override
    public void onResult(Object result) {
        if (result.toString().equalsIgnoreCase("swiped_down")) {

        } else {
            if (result != null) {
                appliedFilters = (ArrayMap<String, List<String>>) result;
                if (appliedFilters.size() > 0) {
                    for (Map.Entry<String, List<String>> entry: appliedFilters.entrySet()) {
                        Log.i("key", entry.getKey());
                        Log.i("selectedValue", entry.getValue().toString());
                    }
                }
            }
        }
    }
}