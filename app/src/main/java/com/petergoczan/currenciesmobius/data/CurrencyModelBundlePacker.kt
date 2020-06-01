package com.petergoczan.currenciesmobius.data

import android.os.Bundle
import com.petergoczan.currenciesmobius.mobius.CurrencyListItem
import com.petergoczan.currenciesmobius.mobius.CurrencyModel
import com.petergoczan.currenciesmobius.mobius.RemoteCurrenciesModel

private const val ARG_CURRENCY_MODEL = "currency_model"
private const val IS_ONLINE = "is_online"
private const val AMOUNT_SET_BY_USER = "amount_set_by_user"
private const val SELECTED_ITEM = "selected_item"
private const val REMOTE_MODEL = "remote_model"


fun saveModel(model: CurrencyModel, bundle: Bundle) =
    bundle.putBundle(ARG_CURRENCY_MODEL, modelToBundle(model))

fun resolveDefaultModel(savedInstanceState: Bundle?): CurrencyModel =
    if (hasSavedModel(savedInstanceState)) {
        modelFromBundle(savedInstanceState!!)
    } else {
        CurrencyModel()
    }

private fun modelToBundle(model: CurrencyModel) =
    Bundle().apply {
        putBoolean(IS_ONLINE, model.isOnline)
        putInt(AMOUNT_SET_BY_USER, model.amountSetByUser)
        putSerializable(SELECTED_ITEM, model.selectedItem)
        putSerializable(REMOTE_MODEL, model.remoteModel)
        //TODO make these Parcelable instead
    }

private fun modelFromBundle(bundle: Bundle) =
    CurrencyModel(
        bundle.getBoolean(IS_ONLINE),
        bundle.getSerializable(SELECTED_ITEM) as CurrencyListItem?,
        bundle.getInt(AMOUNT_SET_BY_USER),
        bundle.getSerializable(REMOTE_MODEL) as RemoteCurrenciesModel
    )

private fun hasSavedModel(savedInstanceState: Bundle?): Boolean =
    savedInstanceState != null && savedInstanceState.containsKey(ARG_CURRENCY_MODEL)

