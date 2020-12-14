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
    return """0x${("%x".format(this)).toUpperCase().padStart(4,'0')} """
}


fun Byte.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase().padStart(2, '0')}"""
}

fun Byte.toBinaryString(): String {
    return """0b${(Integer.toBinaryString(this.toInt() and 0xFF)).toUpperCase()}"""
}