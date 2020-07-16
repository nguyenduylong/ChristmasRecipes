package com.duylong.christmasrecipes.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duylong.christmasrecipes.R;
import com.duylong.christmasrecipes.model.Recipe;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;

public class NutritionFragment extends Fragment {

    TextView nutritionCalories, nutritionFat, nutritionSaturate, nutritionCarb, nutritionSugar, nutritionFibre, nutritionProtein, nutritionSalt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nutrition, container, false);

        nutritionCalories = rootView.findViewById(R.id.nutritrion_calories);
        nutritionFat = rootView.findViewById(R.id.nutritrion_fat);
        nutritionSaturate = rootView.findViewById(R.id.nutritrion_saturates);
        nutritionCarb = rootView.findViewById(R.id.nutritrion_carb);
        nutritionSugar = rootView.findViewById(R.id.nutritrion_sugar);
        nutritionFibre = rootView.findViewById(R.id.nutritrion_fibre);
        nutritionProtein = rootView.findViewById(R.id.nutritrion_protein);
        nutritionSalt = rootView.findViewById(R.id.nutritrion_salt);


        Bundle bundler = getArguments();
        Recipe selectedItem = (Recipe) bundler.getSerializable("selected_item");

        String nutritionInfo = selectedItem.getNutrition();
        String[] nutritionFacts = nutritionInfo.split("\n");
        for (int i = 0; i < nutritionFacts.length; i++) {
            String[] factStrings = nutritionFacts[i].split(":");
            String factName = factStrings[0];
            String factValue = factStrings[1];
            switch (factName) {
                case "kcal":
                    nutritionCalories.setText(factValue);
                    break;
                case "fat":
                    nutritionFat.setText(factValue);
                    break;
                case "saturates":
                    nutritionSaturate.setText(factValue);
                    break;
                case "carbs":
                    nutritionCarb.setText(factValue);
                    break;
                case "sugars":
                    nutritionSugar.setText(factValue);
                    break;
                case "fibre":
                    nutritionFibre.setText(factValue);
                    break;
                case "protein":
                    nutritionProtein.setText(factValue);
                    break;
                case "salt":
                    nutritionSalt.setText(factValue);
                    break;
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}