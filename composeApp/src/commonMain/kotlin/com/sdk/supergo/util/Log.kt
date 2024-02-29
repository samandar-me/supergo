package com.sdk.supergo.util

import io.github.aakira.napier.Napier

fun logEe(msg: String) {
    Napier.e(msg, tag = "@@@Go")
}
fun logDe(msg: String) {
    Napier.d(msg, tag = "@@@Go")
}
