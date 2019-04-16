package com.sadashi.apps.ui.material.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import com.sadashi.apps.ui.material.R
import com.sadashi.apps.ui.material.fragments.adapter.RevealSamplePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var search: SearchView
    private lateinit var searchMenu: MenuItem

    private var statusBarHeight: Int = 0
    private lateinit var menuDrawable: AnimatedVectorDrawable
    private lateinit var arrowDrawable: AnimatedVectorDrawable

    private val pageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            val tab = tabs.getTabAt(position) ?: return

            val view = tab.customView ?: return

            val tabLocations = IntArray(2)
            val baseLocations = IntArray(2)
            view.getLocationInWindow(tabLocations)
            bgReveal.getLocationInWindow(baseLocations)

            startReveal(
                position,
                tabLocations[0] + view.width / 2,
                -baseLocations[1] + tabLocations[1] + view.height / 2
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleText.setText(R.string.app_name)
        titleText.setOnClickListener { toggleSearchView() }

        menuDrawable = getDrawable(R.drawable.ic_menu_24dp) as AnimatedVectorDrawable
        arrowDrawable = getDrawable(R.drawable.ic_arrow_24dp) as AnimatedVectorDrawable
        toolbar.navigationIcon = menuDrawable
        toolbar.setNavigationOnClickListener {
            if (titleText.visibility == View.VISIBLE) {
                drawerLayout.openDrawer(DRAWER_GRAVITY)
            } else {
                toggleSearchView()
            }
        }
        toolbar.inflateMenu(R.menu.search)
        searchMenu = toolbar.menu.findItem(R.id.menu_search)
        search = searchMenu.actionView as SearchView
        search.setIconifiedByDefault(false)

        val icon = search.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_mag_icon)
        icon.setImageDrawable(null)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(DRAWER_GRAVITY)
            false
        }

        @ColorRes val colorResId = RevealSamplePagerAdapter.getTabColorAtPosition(0)
        reveal.setBackgroundResource(colorResId)
        bgReveal.setBackgroundResource(colorResId)
        statusReveal.setBackgroundResource(colorResId)
        bgStatusReveal.setBackgroundResource(colorResId)

        viewPager.addOnPageChangeListener(pageChangeListener)
        viewPager.adapter = RevealSamplePagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
        setTabText()

        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        appbar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val params = revealLayout.layoutParams
                params.height = appbar.height + statusBarHeight
                revealLayout.layoutParams = params
                appbar.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        val params = statusRevealLayout.layoutParams
        params.height = statusBarHeight
        statusRevealLayout.layoutParams = params
    }

    private fun toggleSearchView() {
        if (titleText.visibility == View.VISIBLE) {
            titleText.visibility = View.GONE
            searchMenu.isVisible = true
            search.isIconified = false
            toolbar.navigationIcon = menuDrawable
            menuDrawable.start()
        } else {
            search.clearFocus()
            search.isIconified = true
            searchMenu.isVisible = false
            toolbar.navigationIcon = arrowDrawable
            arrowDrawable.start()
            titleText.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.removeOnPageChangeListener(pageChangeListener)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(DRAWER_GRAVITY)) {
            drawerLayout.closeDrawer(DRAWER_GRAVITY)
        } else {
            super.onBackPressed()
        }
    }

    private fun setTabText() {
        val count = tabs.tabCount
        for (index in 0 until count) {
            val tab = tabs.getTabAt(index) ?: continue

            val textView = LayoutInflater.from(this)
                .inflate(R.layout.custom_tab, null, true) as TextView
            textView.text = index.toString()
            tab.customView = textView
        }
    }

    private fun startReveal(position: Int, centerX: Int, centerY: Int) {
        if (!reveal.isAttachedToWindow) {
            return
        }

        @ColorRes val colorResId = RevealSamplePagerAdapter.getTabColorAtPosition(position)
        val radius = Math.max(reveal.width, reveal.height) * 1.2f

        reveal.setBackgroundResource(colorResId)
        statusReveal.setBackgroundResource(colorResId)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ViewAnimationUtils.createCircularReveal(reveal, centerX, centerY, 0f, radius),
            ViewAnimationUtils.createCircularReveal(statusReveal, centerX, centerY, 0f, radius)
        )
        animatorSet.duration = REVEAL_DURATION_MILL_SEC.toLong()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                bgReveal.setBackgroundResource(colorResId)
                bgStatusReveal.setBackgroundResource(colorResId)
            }
        })
        animatorSet.start()
    }

    companion object {
        private const val REVEAL_DURATION_MILL_SEC = 500
        private const val DRAWER_GRAVITY = Gravity.START
    }
}
