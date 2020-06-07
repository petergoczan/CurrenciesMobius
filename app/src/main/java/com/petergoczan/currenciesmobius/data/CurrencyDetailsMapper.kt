package com.petergoczan.currenciesmobius.data

import com.petergoczan.currenciesmobius.di.ActivityScope
import javax.inject.Inject

/**
 * At first I wanted to use a remote API to get this data, the best one I found was this: https://restcountries.eu/
 * The problem was that based on a currency code multiple results were returned (e.g.: https://restcountries.eu/rest/v2/currency/AUD?fields=flag;name)
 * I could have overcame this by introducing a mapping between currency code and country name for filtering the results.
 * Another issue was the question of the number of requests fired. In mobius we usually create a whole new copy of the model rather then just updating the items
 * in it, so I would have to came up with a persisting solution that doesn't contradict the mobius architecture. Another problem was that
 * there was not Euro flag in any of the results, only the flags of the countries having euro.
 * Then I decided that I've been investing time and effort into the wrong problem, hence the mapping below as a result.
 */
@ActivityScope
class CurrencyDetailsMapper @Inject constructor() {

    fun getNameByCurrencyCode(code: String): String =
        when (code) {
            "AUD" -> "Australian Dollar"
            "BGN" -> "Bulgarian Lev"
            "BRL" -> "Brazilian Real"
            "CAD" -> "Canadian Dollar"
            "CHF" -> "Swiss Franc"
            "CZK" -> "Czech Koruna"
            "DKK" -> "Danish Krone"
            "EUR" -> "Euro"
            "GBP" -> "British Pound"
            "HKD" -> "Hong Kong Dollar"
            "HRK" -> "Croatian Kuna"
            "IDR" -> "Indonesian Rupiah"
            "ILS" -> "Israeli Shekel"
            "INR" -> "Indian Rupee"
            "ISK" -> "Icelandic krona"
            "JPY" -> "Japanese Yen"
            "KRW" -> "South Korean Won"
            "MXN" -> "Mexican Peso"
            "MYR" -> "Malaysian Ringgit"
            "NOK" -> "Norwegian Krone"
            "NZD" -> "New Zealand Dollar"
            "PHP" -> "Philippines Peso"
            "PLN" -> "Polish Zloty"
            "RON" -> "Romanian Leu"
            "RUB" -> "Russian Ruble"
            "SEK" -> "Swedish Krona"
            "SGD" -> "Singapore Dollar"
            "THB" -> "Thailand Baht"
            "USD" -> "United States Dollar"
            "ZAR" -> "South African Rand"
            else -> "UNKNOWN"
        }

    fun getIconByCurrencyCode(code: String): String =
        when (code) {
            "AUD" -> "https://restcountries.eu/data/aus.svg"
            "BGN" -> "https://restcountries.eu/data/bgr.svg"
            "BRL" -> "https://restcountries.eu/data/bra.svg"
            "CAD" -> "https://restcountries.eu/data/can.svg"
            "CHF" -> "https://restcountries.eu/data/che.svg"
            "CZK" -> "https://restcountries.eu/data/cze.svg"
            "DKK" -> "https://restcountries.eu/data/dnk.svg"
            "EUR" -> "https://images-na.ssl-images-amazon.com/images/I/51vZXseSIXL._AC_SX450_.jpg"
            "GBP" -> "https://restcountries.eu/data/gbr.svg"
            "HKD" -> "https://restcountries.eu/data/hkg.svg"
            "HRK" -> "https://restcountries.eu/data/hrv.svg"
            "IDR" -> "https://restcountries.eu/data/idn.svg"
            "ILS" -> "https://restcountries.eu/data/isr.svg"
            "INR" -> "https://restcountries.eu/data/ind.svg"
            "ISK" -> "https://restcountries.eu/data/isl.svg"
            "JPY" -> "https://restcountries.eu/data/jpn.svg"
            "KRW" -> "https://restcountries.eu/data/kor.svg"
            "MXN" -> "https://restcountries.eu/data/mex.svg"
            "MYR" -> "https://restcountries.eu/data/mys.svg"
            "NOK" -> "https://restcountries.eu/data/nor.svg"
            "NZD" -> "https://restcountries.eu/data/nzl.svg"
            "PHP" -> "https://restcountries.eu/data/phl.svg"
            "PLN" -> "https://restcountries.eu/data/pol.svg"
            "RON" -> "https://restcountries.eu/data/rou.svg"
            "RUB" -> "https://restcountries.eu/data/rus.svg"
            "SEK" -> "https://restcountries.eu/data/swe.svg"
            "SGD" -> "https://restcountries.eu/data/sgp.svg"
            "THB" -> "https://restcountries.eu/data/tha.svg"
            "USD" -> "https://restcountries.eu/data/usa.svg"
            "ZAR" -> "https://restcountries.eu/data/zaf.svg"
            else -> "UNKNOWN"
        }
}

