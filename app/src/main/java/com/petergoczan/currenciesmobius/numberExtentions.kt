package com.petergoczan.currenciesmobius

fun Double.roundToTwoDecimals(): Double = "%.2f".format(this).toDouble()