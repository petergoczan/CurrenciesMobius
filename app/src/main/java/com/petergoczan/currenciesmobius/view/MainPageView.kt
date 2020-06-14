package com.petergoczan.currenciesmobius.view

import android.view.View

interface MainPageView {

    fun bind(rootView: View)

    fun initList()

    fun updateList()

    fun notifyItemMovedToTop(originalItemPosition: Int)

    fun hideNoInternetConnectionPage()

    fun showNoInternetConnectionPage()

    fun showCommunicationErrorOverlay()

    fun hideCommunicationErrorOverlay()
}