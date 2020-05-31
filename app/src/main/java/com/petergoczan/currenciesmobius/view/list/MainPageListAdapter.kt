package com.petergoczan.currenciesmobius.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petergoczan.currenciesmobius.R
import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.presentation.MainPagePresenter
import javax.inject.Inject

@ActivityScope
class MainPageListAdapter @Inject constructor(private val presenter: MainPagePresenter) :
    RecyclerView.Adapter<MainPageListRowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageListRowViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_main_activity_list, parent, false)
        return MainPageListRowViewHolder(row)
    }

    override fun onBindViewHolder(holder: MainPageListRowViewHolder, position: Int) {
        presenter.onBindViewAtListPosition(position, holder)
    }

    override fun getItemCount(): Int {
        return presenter.getListItemCount()
    }
}