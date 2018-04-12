package com.sadashi.apps.ui.material.fragments.adapter;

import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sadashi.apps.ui.material.R;
import com.sadashi.apps.ui.material.fragments.SampleFragment;

public class RevealSamplePagerAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_NUM = 5;

    public RevealSamplePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new SampleFragment();
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    @ColorRes
    public static int getTabColorAtPosition(int position) {
        switch (position % PAGE_NUM) {
            case 0:
                return R.color.bgColorTabFirst;
            case 1:
                return R.color.bgColorTabSecond;
            case 2:
                return R.color.bgColorTabThird;
            case 3:
                return R.color.bgColorTabFourth;
            case 4:
                return R.color.bgColorTabFifth;
            default:
                return R.color.bgColorTabFirst;
        }
    }
}
