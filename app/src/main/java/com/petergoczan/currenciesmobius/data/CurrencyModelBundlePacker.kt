package com.petergoczan.currenciesmobius.data

import android.os.Bundle
import com.petergoczan.currenciesmobius.mobius.CurrencyListItem
import com.petergoczan.currenciesmobius.mobius.CurrencyModel

private const val BASE_CURRENCY_CODE = "base_currency_code"
private const val IS_ONLINE = "is_online"
private const val AMOUNT_SET_BY_USER = "amount_set_by_user"
private const val REMOTE_MODEL = "remote_model"


fun saveModel(model: CurrencyModel, bundle: Bundle) =
    bundle.apply {
        putString(BASE_CURRENCY_CODE, model.baseCurrencyCode)
        putBoolean(IS_ONLINE, model.isOnline)
        putDouble(AMOUNT_SET_BY_USER, model.amountSetByUser)
        putParcelableArrayList(REMOTE_MODEL, ArrayList(model.items))
    }

fun resolveDefaultModel(savedInstanceState: Bundle?): CurrencyModel =
    if (hasSavedModel(savedInstanceState)) {
        modelFromBundle(savedInstanceState!!)
    } else {
        CurrencyModel()
    }

private fun modelFromBundle(bundle: Bundle) =
    CurrencyModel(
        bundle.getString(BASE_CURRENCY_CODE)!!,
        bundle.getBoolean(IS_ONLINE),
        bundle.getDouble(AMOUNT_SET_BY_USER),
        bundle.getParcelableArrayList<CurrencyListItem>(REMOTE_MODEL)!!.toList()
    )

private fun hasSavedModel(savedInstanceState: Bundle?): Boolean =
    savedInstanceState != null && savedInstanceState.containsKey(BASE_CURRENCY_CODE)


