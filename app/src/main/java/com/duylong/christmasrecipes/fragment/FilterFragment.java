package com.duylong.christmasrecipes.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.bumptech.glide.Glide;
import com.duylong.christmasrecipes.MainActivity;
import com.duylong.christmasrecipes.R;
import com.duylong.christmasrecipes.constant.FilterList;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FilterFragment extends AAH_FabulousFragment {

    ArrayMap<String, List<String>> appliedFilters = new ArrayMap<>();

    List<LinearLayout> itemViews = new ArrayList<>();

    TabLayout typeTab;
    ImageButton freshButton, applyButton;

    SectionPagerAdapter sectionPagerAdapter;

    public static FilterFragment newInstance() {
        FilterFragment filterFragment = new FilterFragment();
        return filterFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appliedFilters = ((MainActivity) getActivity()).getAppliedFilters();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.filter_view, null);

        RelativeLayout relativeLayout = contentView.findViewById(R.id.rl_content);
        LinearLayout linearButtons = contentView.findViewById(R.id.ll_buttons);
        freshButton = contentView.findViewById(R.id.imgbtn_refresh);
        applyButton = contentView.findViewById(R.id.imgbtn_apply);
        ViewPager viewPager = contentView.findViewById(R.id.vp_types);
        typeTab = (TabLayout) contentView.findViewById(R.id.tabs_types);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFilter(appliedFilters);
            }
        });

        freshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (LinearLayout itemView: itemViews) {
                    itemView.setTag("unselected");
                    itemView.setBackgroundResource(R.drawable.item_unselected);
                    TextView textView = itemView.findViewById(R.id.txt_title);
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_items));
                }
                appliedFilters.clear();
            }
        });


        sectionPagerAdapter = new SectionPagerAdapter();
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(sectionPagerAdapter);
        sectionPagerAdapter.notifyDataSetChanged();

        typeTab.setupWithViewPager(viewPager);



        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(linearButtons); // optional; layout to stick at bottom on slide
        setViewPager(viewPager); //optional; if you use viewpager that has scrollview
        setViewMain(relativeLayout); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style);
    }

    public class SectionPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            ViewGroup layout = (ViewGroup) layoutInflater.inflate(R.layout.filter_sorters, container, false);
            FlexboxLayout flexboxLayout = (FlexboxLayout) layout.findViewById(R.id.fbl);

            switch (position) {
                case 0:
                    inflateLayoutWithFilters("kind", flexboxLayout);
                    break;
                case 1:
                    inflateLayoutWithFilters("calories", flexboxLayout);
                    break;
                case 2:
                    inflateLayoutWithFilters("skill level", flexboxLayout);
                    break;
                case 3:
                    inflateLayoutWithFilters("ingredients", flexboxLayout);
                    break;
            }

            container.addView(layout);
            return layout;
        }

        private void inflateLayoutWithFilters(final String filter_category, FlexboxLayout flexboxLayout) {
            List<String> keys = new ArrayList<>();
            int[] drawables = new int[]{};
            switch (filter_category) {
                case "kind":
                    keys = FilterList.KINDS;
                    drawables = FilterList.KIND_DRAWABLES;
                    break;
                case "skill level":
                    keys = FilterList.SKILLS;
                    drawables = FilterList.SKILL_DRAWABLES;
                    break;
                case "calories":
                    keys = FilterList.CALORIES;
                    drawables = FilterList.CALORIE_DRAWABLES;
                    break;
                case "ingredients":
                    keys = FilterList.INGREDIENTS;
                    drawables = FilterList.INGREDIENT_DRAWABLES;
                    break;
            }

            if (keys.size() > 0 && drawables.length > 0) {
                for (int i = 0; i < keys.size(); i++) {
                    View subOption = getActivity().getLayoutInflater().inflate(R.layout.single_item, null);
                    final LinearLayout itemLayout = subOption.findViewById(R.id.item_view);
                    final TextView textView = (TextView) subOption.findViewById(R.id.txt_title);
                    final ImageView itemImage = (ImageView) subOption.findViewById(R.id.item_img);

                    textView.setText(keys.get(i));
                    itemImage.setImageResource(drawables[i]);

                    final int finalI = i;
                    final List<String> finalKeys = keys;
                    itemLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (view.getTag() != null && itemLayout.getTag().equals("selected")) {
                                itemLayout.setTag("unselected");
                                itemLayout.setBackgroundResource(R.drawable.item_unselected);
                                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_items));
                                removeFromSelectedMap(filter_category, finalKeys.get(finalI));
                            } else {
                                itemLayout.setTag("selected");
                                itemLayout.setBackgroundResource(R.drawable.item_selected);
                                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_header));
                                addToSelectedMap(filter_category, finalKeys.get(finalI));
                            }
                        }
                    });

                    if (appliedFilters != null && appliedFilters.get(filter_category) != null && appliedFilters.get(filter_category).contains(keys.get(finalI))) {
                        itemLayout.setTag("selected");
                        itemLayout.setBackgroundResource(R.drawable.item_selected);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_header));
                    } else {
                        itemLayout.setBackgroundResource(R.drawable.item_unselected);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_items));
                    }

                    itemViews.add(itemLayout);

                    flexboxLayout.addView(subOption);
                }
            }
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "KIND";
                case 1:
                    return "CAROLIES";
                case 2:
                    return "LEVEL";
                case 3:
                    return "ingredient";

            }
            return "";
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        private void addToSelectedMap(String key, String value) {
            if (appliedFilters.get(key) != null && !appliedFilters.get(key).contains(value)) {
                appliedFilters.get(key).add(value);
            } else {
                List<String> temp = new ArrayList<>();
                temp.add(value);
                appliedFilters.put(key, temp);
            }
        }

        private void removeFromSelectedMap(String key, String value) {
            if (appliedFilters.get(key).size() == 1) {
                appliedFilters.remove(key);
            } else {
                appliedFilters.get(key).remove(value);
            }
        }
    }
}
