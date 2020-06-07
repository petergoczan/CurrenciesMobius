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
            "CNY" -> "Chinese Yuan"
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
            "AUD" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Australia.jpg"
            "BGN" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Bulgaria.jpg"
            "BRL" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Brazil.jpg"
            "CAD" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Canada.jpg"
            "CHF" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Switzerland.jpg"
            "CNY" -> "https://www.sciencekids.co.nz/images/pictures/flags96/China.jpg"
            "CZK" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Czech_Republic.jpg"
            "DKK" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Denmark.jpg"
            "EUR" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/255px-Flag_of_Europe.svg.png"
            "GBP" -> "https://www.sciencekids.co.nz/images/pictures/flags96/United_Kingdom.jpg"
            "HKD" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Hong_Kong.jpg"
            "HRK" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Croatia.jpg"
            "IDR" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Indonesia.jpg"
            "ILS" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Israel.jpg"
            "INR" -> "https://www.sciencekids.co.nz/images/pictures/flags96/India.jpg"
            "ISK" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Iceland.jpg"
            "JPY" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Japan.jpg"
            "KRW" -> "https://www.sciencekids.co.nz/images/pictures/flags96/South_Korea.jpg"
            "MXN" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Mexico.jpg"
            "MYR" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Malaysia.jpg"
            "NOK" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Norway.jpg"
            "NZD" -> "https://www.sciencekids.co.nz/images/pictures/flags96/New_Zealand.jpg"
            "PHP" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Philippines.jpg"
            "PLN" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Poland.jpg"
            "RON" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Romania.jpg"
            "RUB" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Russia.jpg"
            "SEK" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Sweden.jpg"
            "SGD" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Singapore.jpg"
            "THB" -> "https://www.sciencekids.co.nz/images/pictures/flags96/Thailand.jpg"
            "USD" -> "https://www.sciencekids.co.nz/images/pictures/flags96/United_States.jpg"
            "ZAR" -> "https://www.sciencekids.co.nz/images/pictures/flags96/South_Africa.jpg"
            else -> "UNKNOWN"
        }
}

