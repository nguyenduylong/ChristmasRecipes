package com.duylong.christmasrecipes.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duylong.christmasrecipes.R;
import com.duylong.christmasrecipes.model.Recipe;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MethodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MethodFragment extends Fragment {

    TextView methodTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_method, container, false);

        methodTextView = rootView.findViewById(R.id.method_txt);

        Bundle bundler = getArguments();
        Recipe selectedItem = (Recipe) bundler.getSerializable("selected_item");

        String method = selectedItem.getMethods();
        String[] steps = method.split("\n");
        for (int i = 0; i < steps.length; i++) {
            steps[i] = ("<b>Step {step}. </b>").replace("{step}", Integer.toString(i + 1)) + steps[i];
        }

        method = TextUtils.join("<br><br>", steps);

        methodTextView.setText(Html.fromHtml(method));
        methodTextView.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}