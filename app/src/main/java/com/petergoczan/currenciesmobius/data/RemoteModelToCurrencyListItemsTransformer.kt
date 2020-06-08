package com.petergoczan.currenciesmobius.data

import com.petergoczan.currenciesmobius.mobius.CurrencyListItem
import com.petergoczan.currenciesmobius.mobius.RemoteCurrenciesModel
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

class RemoteModelToCurrencyListItemsTransformer @Inject constructor(private val currencyDetailsMapper: CurrencyDetailsMapper) :
    ObservableTransformer<RemoteCurrenciesModel, List<CurrencyListItem>> {

    override fun apply(upstream: Observable<RemoteCurrenciesModel>): ObservableSource<List<CurrencyListItem>> {
        return upstream.map { model ->
            val items = mutableListOf<CurrencyListItem>()
            val rates = model.rates
            rates::class.memberProperties.forEach {
                items.add(
                    CurrencyListItem(
                        code = it.name,
                        name = currencyDetailsMapper.getNameByCurrencyCode(it.name),
                        imageUrl = currencyDetailsMapper.getIconByCurrencyCode(it.name),
                        multiplierForBaseCurrency = it.getter.call(rates) as Float
                    )
                )
            }
            items
        }
    }
}