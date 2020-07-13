package com.duylong.christmasrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.duylong.christmasrecipes.fragment.IngredientFragment;
import com.duylong.christmasrecipes.fragment.MethodFragment;
import com.duylong.christmasrecipes.fragment.NutritionFragment;
import com.duylong.christmasrecipes.model.Recipe;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class RecipeDetailActivity extends AppCompatActivity {

    ViewPager viewPager;

    SmartTabLayout pagerTab;

    Recipe seletedItem;

    ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        seletedItem = (Recipe) getIntent().getSerializableExtra("selected_item");

        Log.i("selected vale", seletedItem.getName());
        Log.i("nutrition", seletedItem.getNutrition());
        Log.i("method", seletedItem.getMethods());
        Log.i("ingredient", seletedItem.getIngredients());

        viewPager = (ViewPager) findViewById(R.id.detail_viewpager);
        pagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        recipeImage = (ImageView) findViewById(R.id.recipe_image);

        Glide.with(getApplicationContext()).load(seletedItem.getImageUrl()).into(recipeImage);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.fragment_nutrition, NutritionFragment.class, new Bundler().putSerializable("selected_item", seletedItem).get())
                .add(R.string.fragment_ingredient, IngredientFragment.class, new Bundler().putSerializable("selected_item", seletedItem).get())
                .add(R.string.fragment_method, MethodFragment.class, new Bundler().putSerializable("selected_item", seletedItem).get())
                .create());

        viewPager.setAdapter(adapter);
        pagerTab.setViewPager(viewPager);
    }
}