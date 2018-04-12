package com.sadashi.apps.ui.material.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sadashi.apps.ui.material.R;
import com.sadashi.apps.ui.material.fragments.adapter.RevealSamplePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int REVEAL_DURATION_MILL_SEC = 500;
    private static final int DRAWER_GRAVITY = Gravity.START;

    @BindView(R.id.reveal_layout)
    FrameLayout revealLayout;
    @BindView(R.id.reveal)
    View reveal;
    @BindView(R.id.bg_reveal)
    View bgReveal;
    @BindView(R.id.status_reveal_layout)
    FrameLayout statusRevealLayout;
    @BindView(R.id.status_reveal)
    View statusReveal;
    @BindView(R.id.bg_status_reveal)
    View bgStatusReveal;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.search)
    EditText search;

    private int statusBarHeight;
    private AnimatedVectorDrawable menuDrawable;
    private AnimatedVectorDrawable arrowDrawable;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(final int position) {
            TabLayout.Tab tab = tabs.getTabAt(position);
            if (tab == null) {
                return;
            }

            View view = tab.getCustomView();
            if (view == null) {
                return;
            }

            int[] tabLocations = new int[2];
            int[] baseLocations = new int[2];
            view.getLocationInWindow(tabLocations);
            bgReveal.getLocationInWindow(baseLocations);

            startReveal(position,
                    tabLocations[0] + view.getWidth() / 2,
                    (-baseLocations[1]) + tabLocations[1] + view.getHeight() / 2);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        title.setText(R.string.app_name);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSearchView();
            }
        });

        menuDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu_24dp);
        arrowDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_arrow_24dp);
        toolbar.setNavigationIcon(menuDrawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getVisibility() == View.VISIBLE) {
                    drawerLayout.openDrawer(DRAWER_GRAVITY);
                } else {
                    toggleSearchView();
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(DRAWER_GRAVITY);
                return false;
            }
        });

        @ColorRes final int colorResId = RevealSamplePagerAdapter.getTabColorAtPosition(0);
        reveal.setBackgroundResource(colorResId);
        bgReveal.setBackgroundResource(colorResId);
        statusReveal.setBackgroundResource(colorResId);
        bgStatusReveal.setBackgroundResource(colorResId);

        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setAdapter(new RevealSamplePagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);
        setTabText();

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        appBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams params = revealLayout.getLayoutParams();
                params.height = appBarLayout.getHeight() + statusBarHeight;
                revealLayout.setLayoutParams(params);
                appBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        ViewGroup.LayoutParams params = statusRevealLayout.getLayoutParams();
        params.height = statusBarHeight;
        statusRevealLayout.setLayoutParams(params);
    }

    private void toggleSearchView() {
        if (title.getVisibility() == View.VISIBLE) {
            title.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);
            toolbar.setNavigationIcon(menuDrawable);
            menuDrawable.start();
        } else {
            title.setVisibility(View.VISIBLE);
            search.clearFocus();
            search.setVisibility(View.GONE);
            toolbar.setNavigationIcon(arrowDrawable);
            arrowDrawable.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(pageChangeListener);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(DRAWER_GRAVITY)) {
            drawerLayout.closeDrawer(DRAWER_GRAVITY);
        } else {
            super.onBackPressed();
        }
    }

    private void setTabText() {
        int count = tabs.getTabCount();
        for (int index = 0; index < count; index++) {
            TabLayout.Tab tab = tabs.getTabAt(index);
            if (tab == null) {
                continue;
            }

            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null, true);
            textView.setText(String.valueOf(index));
            tab.setCustomView(textView);
        }
    }

    private void startReveal(final int position, int centerX, int centerY) {
        if (!reveal.isAttachedToWindow()) {
            return;
        }

        final @ColorRes int colorResId = RevealSamplePagerAdapter.getTabColorAtPosition(position);
        final float radius = Math.max(reveal.getWidth(), reveal.getHeight()) * 1.2f;

        reveal.setBackgroundResource(colorResId);
        statusReveal.setBackgroundResource(colorResId);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ViewAnimationUtils.createCircularReveal(reveal, centerX, centerY, 0, radius),
                ViewAnimationUtils.createCircularReveal(statusReveal, centerX, centerY, 0, radius));
        animatorSet.setDuration(REVEAL_DURATION_MILL_SEC);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bgReveal.setBackgroundResource(colorResId);
                bgStatusReveal.setBackgroundResource(colorResId);
            }
        });
        animatorSet.start();
    }
}
