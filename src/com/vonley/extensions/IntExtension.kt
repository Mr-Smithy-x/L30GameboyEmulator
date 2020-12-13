package com.vonley.extensions

import com.vonley.processor.MMU

fun Int.getRegion(bootRomEnabled: Boolean = false): MMU.Region {
    return MMU.Region.parse(this, bootRomEnabled)
}

fun Int.toBinaryString(): String {
    return "0b${Integer.toBinaryString(this)}"
}

fun Int.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase()}"""
}


fun Short.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase()} """
}


fun Byte.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase()}"""
}