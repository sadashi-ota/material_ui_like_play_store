package com.sadashi.apps.ui.material.fragments.adapter

import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.sadashi.apps.ui.material.R
import com.sadashi.apps.ui.material.fragments.SampleFragment

class RevealSamplePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = SampleFragment()

    override fun getCount(): Int = PAGE_NUM

    companion object {

        const val PAGE_NUM = 5

        @ColorRes
        fun getTabColorAtPosition(position: Int): Int {
            return when (position % PAGE_NUM) {
                0 -> R.color.bgColorTabFirst
                1 -> R.color.bgColorTabSecond
                2 -> R.color.bgColorTabThird
                3 -> R.color.bgColorTabFourth
                4 -> R.color.bgColorTabFifth
                else -> R.color.bgColorTabFirst
            }
        }
    }
}
