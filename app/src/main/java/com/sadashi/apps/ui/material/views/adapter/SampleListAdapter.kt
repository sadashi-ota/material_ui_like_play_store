package com.sadashi.apps.ui.material.views.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sadashi.apps.extensions.inflate
import com.sadashi.apps.ui.material.R
import kotlinx.android.synthetic.main.sample_list_item.view.*

class SampleListAdapter : RecyclerView.Adapter<SampleListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.text.text = "dummy"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.sample_list_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 50
}
