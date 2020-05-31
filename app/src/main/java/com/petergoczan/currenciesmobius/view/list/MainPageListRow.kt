package com.petergoczan.currenciesmobius.view.list

interface MainPageListRow {

    fun setTitle(currencyCode: String)

    fun setSubtitle(currencyName: String)

    fun setImage(imageUrl: String)

    fun setAmount(amount: Float)
}