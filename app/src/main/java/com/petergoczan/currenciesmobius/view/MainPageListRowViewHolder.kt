package com.petergoczan.currenciesmobius.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_main_activity_list.view.*

class MainPageListRowViewHolder(var root: View) : RecyclerView.ViewHolder(root) {
    val currencyCode = root.currency_code
    val currencyName = root.currency_name
    val image = root.image
    val amount = root.amount
}