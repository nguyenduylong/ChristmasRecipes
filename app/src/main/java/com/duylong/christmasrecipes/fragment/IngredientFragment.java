package com.duylong.christmasrecipes.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duylong.christmasrecipes.R;
import com.duylong.christmasrecipes.model.Recipe;

public class IngredientFragment extends Fragment {

    TextView ingredientTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_ingredient, container, false);

        ingredientTextView = rootView.findViewById(R.id.ingredient_txt);

        Bundle bundler = getArguments();
        Recipe selectedItem = (Recipe) bundler.getSerializable("selected_item");

        String ingredient = selectedItem.getIngredients();

        String[] ingredients = ingredient.split("\n");

        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = "<b>{step}. </b>".replace("{step}", Integer.toString(i + 1)) + ingredients[i];
        }

        ingredient = TextUtils.join("<br><br>", ingredients);

        ingredientTextView.setText(Html.fromHtml(ingredient));
        ingredientTextView.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}