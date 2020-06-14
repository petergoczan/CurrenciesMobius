package com.petergoczan.currenciesmobius.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.view.list.MainPageListAdapter
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.grey_transparent_overlay.view.*
import kotlinx.android.synthetic.main.no_internet_page.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import javax.inject.Inject

@ActivityScope
class MainPageViewImpl @Inject constructor(private val adapter: MainPageListAdapter) :
    MainPageView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var noInternetPage: View
    private lateinit var communicationErrorOverlay: View
    private lateinit var progressBar: View

    override fun bind(rootView: View) {
        recyclerView = rootView.recycler_view
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        noInternetPage = rootView.no_internet_page
        communicationErrorOverlay = rootView.grey_transparent_overlay
        progressBar = rootView.progress_bar
    }

    override fun initList() {
        adapter.notifyDataSetChanged()
    }

    override fun updateList() {
        for (i in 1 until adapter.itemCount) {
            adapter.notifyItemChanged(i)
        }
    }

    override fun notifyItemMovedToTop(originalItemPosition: Int) {
        adapter.notifyItemMoved(originalItemPosition, 0)
        layoutManager.scrollToPosition(0)
        adapter.notifyItemChanged(0, false)
    }

    override fun hideNoInternetConnectionPage() {
        noInternetPage.visibility = View.GONE
    }

    override fun showNoInternetConnectionPage() {
        noInternetPage.visibility = View.VISIBLE
    }

    override fun showCommunicationErrorOverlay() {
        communicationErrorOverlay.visibility = View.VISIBLE
    }

    override fun hideCommunicationErrorOverlay() {
        communicationErrorOverlay.visibility = View.GONE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}