package com.sadashi.apps.ui.material.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sadashi.apps.ui.material.R
import com.sadashi.apps.ui.material.views.adapter.SampleListAdapter
import kotlinx.android.synthetic.main.fragment_sample.*

class SampleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sample, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.adapter = SampleListAdapter()
    }
}
