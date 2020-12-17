package com.vonley.extensions

import com.vonley.boards.z80.memory.MMU

fun UShort.getRegion(bootRomEnabled: Boolean = false): MMU.Region {
    return MMU.Region.parse(this, bootRomEnabled)
}

fun UShort.toHexString(): String {
    return """0x${("%x".format(this.toInt())).toUpperCase().padStart(4,'0')} """
}

fun UInt.toHexString(): String {
    return """0x${("%x".format(this.toInt())).toUpperCase()}"""
}

fun UByte.toHexString(): String {
    return """0x${("%x".format(this.toInt())).toUpperCase().padStart(2, '0')}"""
}

fun UByte.toBinaryString(): String {
    return """0b${(Integer.toBinaryString(this.toInt() and 0xFF)).toUpperCase()}"""
}


infix fun UShort.shr(bitCount: Int): UShort {
    return (toUInt() shr bitCount).and(0xFFFFu).toUShort()
}

infix fun UShort.shl(bitCount: Int): UShort {
    return (toUInt() shr bitCount).and(0xFFFFu).toUShort()
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
