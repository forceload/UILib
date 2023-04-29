package io.github.forceload.uilib.util

fun Double.format(digits: Int) = "%.${digits}f".format(this)