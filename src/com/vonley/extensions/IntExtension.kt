package com.vonley.extensions


fun Int.toBinaryString(): String? {
    return Integer.toBinaryString(this)
}

fun Int.toHexString(): String? {
    return Integer.toHexString(this)
}