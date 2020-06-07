package com.petergoczan.currenciesmobius.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.view.list.MainPageListAdapter
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

@ActivityScope
class MainPageViewImpl @Inject constructor(private val adapter: MainPageListAdapter) :
    MainPageView {

    private lateinit var recyclerView: RecyclerView

    override fun bind(rootView: View) {
        recyclerView = rootView.recycler_view
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun update() {
        adapter.notifyDataSetChanged()
    }
}