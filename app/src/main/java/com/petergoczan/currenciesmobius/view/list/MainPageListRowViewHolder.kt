package com.petergoczan.currenciesmobius.view.list

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_main_activity_list.view.*

class MainPageListRowViewHolder(root: View) : RecyclerView.ViewHolder(root),
    MainPageListRow {

    private val title: TextView = root.currency_code
    private val subTitle: TextView = root.currency_name
    private val image: ImageView = root.image
    private val amount: EditText = root.amount

    private lateinit var onAmountChangedAction: (Double) -> Unit

    override fun setTitle(currencyCode: String) {
        title.text = currencyCode
    }

    override fun setSubtitle(currencyName: String) {
        subTitle.text = currencyName
    }

    override fun setImage(picasso: Picasso, imageUrl: String) {
        picasso.load(imageUrl).into(image)
    }

    override fun setAmount(amount: Double) {
        this.amount.setText(amount.toString())
    }

    override fun setupAmountChangedListener(onAmountChanged: (Double) -> Unit) {
        if (adapterPosition == 0) {
            onAmountChangedAction = onAmountChanged
            amount.addTextChangedListener(amountTextWatcher)
        } else {
            amount.removeTextChangedListener(amountTextWatcher)
        }
    }

    override fun setRowSelectedListener(rowSelected: (Int) -> Unit) {
        itemView.setOnClickListener {
            rowSelected.invoke(layoutPosition)
            amount.onFocusChangeListener = null
            amount.requestFocus()
        }
        amount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                rowSelected.invoke(layoutPosition)
            }
        }
    }

    private val amountTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
            if (adapterPosition == 0) {
                if (amount.text.isNotEmpty()) {
                    onAmountChangedAction.invoke(amount.text.toString().toDouble())
                } else {
                    amount.setText(DEFAULT_QUANTITY)
                }
            }
        }
    }
}

private const val DEFAULT_QUANTITY = "0.0"