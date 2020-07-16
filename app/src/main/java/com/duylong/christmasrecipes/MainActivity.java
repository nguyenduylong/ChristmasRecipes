package com.duylong.christmasrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.TextView;
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

    TextView noresultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noresultTextView = findViewById(R.id.no_result_txt);

        recipes = new ArrayList<Recipe>();

        dbManager = new DBManager(this);
        dbManager.open();

//        dbManager.updateDataBase();

        Cursor dataCursor = dbManager.fetch_data();

        recipes = createRecipeList(dataCursor);

        if (recipes.size() > 0) {
            noresultTextView.setVisibility(View.INVISIBLE);
        } else {
            noresultTextView.setVisibility(View.VISIBLE);
        }

        Toast.makeText(getApplicationContext(), recipes.size() + "", Toast.LENGTH_SHORT).show();

        recipeListView = findViewById(R.id.recipe_view);

        recipeAdapter = new RecipeAdapter(this, recipes, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra("selected_item", recipes.get(position));
                startActivity(intent);
            }
        });

        recipeListView.setAdapter(recipeAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recipeListView.setLayoutManager(layoutManager);
        recipeListView.setItemAnimator(new DefaultItemAnimator());

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

    private ArrayList<Recipe> createRecipeList(Cursor dataCursor) {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        if (dataCursor.getCount() > 0) {
            do {
                Recipe recipe = createRecipe(dataCursor);
                recipeList.add(recipe);
            } while (dataCursor.moveToNext());
        }

        return recipeList;
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
                String[] whereClauses;
                appliedFilters = (ArrayMap<String, List<String>>) result;
                Cursor dataCursor = null;

                if (appliedFilters.size() > 0) {
                    int i = 0;
                    whereClauses = new String[appliedFilters.size()];
                    for (Map.Entry<String, List<String>> entry: appliedFilters.entrySet()) {
                        String key = entry.getKey();
                        List<String> values = entry.getValue();
                        Log.i("selected key", key);
                        String whereClause = "";
                        switch (key) {
                            case "skill level":
                                whereClause = generateSkillLevelCondition(values);
                                break;
                            case "kind":
                                whereClause = generateKindCondition(values);
                                break;
                            case "calories":
                                whereClause = generateKcalCondition(values);
                                break;
                            case "ingredients":
                                whereClause = generateIngredientCondition(values);
                                break;
                        }
                        if (!whereClause.equals("")) {
                            whereClauses[i] = whereClause;
                            i++;
                        }
                    }
                    TransitionManager.beginDelayedTransition(recipeListView);
                    dataCursor = dbManager.fetch_data(whereClauses);
                    recipes.clear();
                    if (dataCursor.getCount() > 0) {
                        do {
                            Recipe recipe = createRecipe(dataCursor);
                            recipes.add(recipe);
                        } while (dataCursor.moveToNext());
                    }
                    if (recipes.size() > 0) {
                        noresultTextView.setVisibility(View.INVISIBLE);
                    } else {
                        noresultTextView.setVisibility(View.VISIBLE);
                    }
                    Log.i("result count", recipes.size() + "");
                    recipeAdapter.notifyDataSetChanged();
                } else {
                    dataCursor = dbManager.fetch_data();
                    recipes.clear();
                    if (dataCursor.getCount() > 0) {
                        do {
                            Recipe recipe = createRecipe(dataCursor);
                            recipes.add(recipe);
                        } while (dataCursor.moveToNext());
                    }
                    if (recipes.size() > 0) {
                        noresultTextView.setVisibility(View.INVISIBLE);
                    } else {
                        noresultTextView.setVisibility(View.VISIBLE);
                    }
                    Log.i("result count", recipes.size() + "");
                    recipeAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private String generateIngredientCondition(List<String> values) {
        String whereClause = "";
        if (values.size() > 0) {
            String firstValue = values.get(0);
            whereClause = "ingredients like '%" + firstValue + "%'";
            for (int i = 1; i < values.size(); i++) {
                whereClause = whereClause + " AND ingredients like '%" + values.get(i) + "%'";
            }
            whereClause = "(" + whereClause + ")";
        }
        Log.i("ingredients condition", whereClause);

        return whereClause;
    }

    private String generateKcalCondition(List<String> values) {
        String whereClause = "";
        if (values.size() > 0) {
            String firstValue = values.get(0);
            whereClause = generateKcalConditionByLevel(firstValue);
            for (int i = 1; i < values.size(); i++) {
                whereClause = whereClause + "OR" + generateKcalConditionByLevel(values.get(i));
            }
            whereClause = "(" + whereClause + ")";
        }
        Log.i("calories condition", whereClause);

        return whereClause;
    }

    private String generateKcalConditionByLevel(String level) {
        String whereClause = "(kcals >= 500) ";
        if (level.equals("Medium")) {
            whereClause = " (kcals < 500 AND kcals >= 250)";
        }
        if (level.equals("Low")) {
            whereClause = " (kcals < 250)";
        }
        return whereClause;
    }

    private String generateKindCondition(List<String> values) {
        String whereClause = "";
        if (values.size() > 0) {
            String firstValue = values.get(0);
            whereClause = "name LIKE '%" + firstValue + "%'";

            for (int i = 1; i < values.size(); i++) {
                whereClause = whereClause + " OR name LIKE '%" + values.get(i) + "%'";
            }
            whereClause = "(" + whereClause + ")";
        }
        Log.i("kind where clause", whereClause);

        return whereClause;
    }

    private String generateSkillLevelCondition(List<String> values) {
        String whereClause = "";
        if (values.size() > 0) {
            String firstValue = values.get(0);
            whereClause = "skill_level LIKE '%" + firstValue + "%'";

            for (int i = 1; i < values.size(); i++) {
                whereClause = whereClause + " OR skill_level LIKE '%" + values.get(i) + "%'";
            }
            whereClause = "(" + whereClause + ")";
        }
        Log.i("skill level clause", whereClause);

        return whereClause;
    }
}