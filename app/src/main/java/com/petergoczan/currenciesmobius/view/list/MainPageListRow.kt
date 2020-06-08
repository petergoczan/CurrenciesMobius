package com.petergoczan.currenciesmobius.view.list

import com.squareup.picasso.Picasso

interface MainPageListRow {

    fun setTitle(currencyCode: String)

    fun setSubtitle(currencyName: String)

    fun setImage(picasso: Picasso, imageUrl: String)

    fun setAmount(amount: Float)

    fun setAmountChangedListener(onAmountChanged: (Float) -> Unit)
}