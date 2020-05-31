package com.petergoczan.currenciesmobius.view.list

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_main_activity_list.view.*

class MainPageListRowViewHolder(root: View) : RecyclerView.ViewHolder(root),
    MainPageListRow {
    private val title: TextView = root.currency_code
    private val subTitle: TextView = root.currency_name
    private val image: ImageView = root.image
    private val quantity: EditText = root.amount

    override fun setTitle(currencyCode: String) {
        title.text = currencyCode
    }

    override fun setSubtitle(currencyName: String) {
        subTitle.text = currencyName
    }

    override fun setImage(imageUrl: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setAmount(amount: Float) {
        quantity.setText(amount.toString())
    }
}