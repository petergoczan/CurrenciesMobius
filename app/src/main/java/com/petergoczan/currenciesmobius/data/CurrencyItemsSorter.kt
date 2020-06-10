package com.petergoczan.currenciesmobius.data

import com.petergoczan.currenciesmobius.mobius.CurrencyListItem
import com.petergoczan.currenciesmobius.mobius.CurrencyModel

//So we will see the items in the same order as before
fun getReorderedItemsByExistingList(
    originalModel: CurrencyModel,
    newList: List<CurrencyListItem>
): List<CurrencyListItem> {
    val reorderedList: MutableList<CurrencyListItem> = mutableListOf()
    originalModel.items.forEach { referenceItem ->
        reorderedList.add(newList.find { referenceItem.code == it.code }!!)
    }
    return reorderedList.toList()
}

fun getReorderedItemsForItemClick(
    items: List<CurrencyListItem>,
    itemPosition: Int
): List<CurrencyListItem> {
    val itemToMove = items[itemPosition]
    return items.toMutableList().apply {
        removeAt(itemPosition)
        add(0, itemToMove)
    }.toList()
}
