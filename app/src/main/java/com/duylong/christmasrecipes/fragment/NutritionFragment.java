package com.duylong.christmasrecipes.fragment;


import android.os.Bundle;
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

    TextView nutritionInfo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nutrition, container, false);

        nutritionInfo = rootView.findViewById(R.id.nutrition_info);
        Bundle bundler = getArguments();
        Recipe selectedItem = (Recipe) bundler.getSerializable("selected_item");

        nutritionInfo.setText(selectedItem.getNutrition());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}