package com.sadashi.apps.ui.material.views.behaviors

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * ヘッダーView用のビヘイビアー
 */
class HeaderBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {
    private var defaultDependencyTop = -1

    override fun layoutDependsOn(parent: CoordinatorLayout,view: View, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, view: View, dependency: View): Boolean {
        if (defaultDependencyTop == -1) {
            defaultDependencyTop = dependency.top
        }

        view.translationY = (dependency.top - defaultDependencyTop).toFloat()
        return true
    }
}
