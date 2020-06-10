package com.petergoczan.currenciesmobius.view.list

import com.squareup.picasso.Picasso

interface MainPageListRow {

    fun setTitle(currencyCode: String)

    fun setSubtitle(currencyName: String)

    fun setImage(picasso: Picasso, imageUrl: String)

    fun setAmount(amount: Double)

    fun setupAmountChangedListener(onAmountChanged: (Double) -> Unit)

    fun setRowSelectedListener(rowSelected: (Int) -> Unit)

    fun getAdapterPosition(): Int
}