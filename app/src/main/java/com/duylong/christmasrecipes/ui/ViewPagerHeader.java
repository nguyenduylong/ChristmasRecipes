package com.duylong.christmasrecipes.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerHeader extends MotionLayout implements ViewPager.OnPageChangeListener {
    public ViewPagerHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int numPages = 3;
        float progress = (position + positionOffset) / (numPages - 1);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
