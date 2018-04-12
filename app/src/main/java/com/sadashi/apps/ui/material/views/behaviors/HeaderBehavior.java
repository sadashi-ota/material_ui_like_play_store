package com.sadashi.apps.ui.material.views.behaviors;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * ヘッダーView用のビヘイビアー
 */
public class HeaderBehavior extends CoordinatorLayout.Behavior<View> {
    private int defaultDependencyTop = -1;

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View view, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View view, View dependency) {
        if (defaultDependencyTop == -1) {
            defaultDependencyTop = dependency.getTop();
        }

        view.setTranslationY(dependency.getTop() - defaultDependencyTop);
        return true;
    }
}
