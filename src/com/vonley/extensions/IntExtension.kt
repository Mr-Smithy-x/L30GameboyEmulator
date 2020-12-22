package com.vonley.extensions

import com.vonley.boards.z80.memory.MMU
import java.math.BigDecimal

fun UShort.getRegion(bootRomEnabled: Boolean = false): MMU.Region {
    return MMU.Region.parse(this, bootRomEnabled)
}

fun UShort.toHexString(): String {
    return """0x${("%x".format(this.toInt())).toUpperCase().padStart(4, '0')} """
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
    return (toUInt() shl bitCount).and(0xFFFFu).toUShort()
}


fun Int.Companion.fromBCD(bcd: Int): Int {
    //lets say bcd is 593
    //how do we calculate it?
    //593 is of 100'ths place
    //lets mod up
    var placements = 1;
    var copy: Int = bcd
    var arr = Array(0) { "" }
    while (copy > 0){
        val remainder = copy % 10
        copy /= 10
        placements += 1
        arr = arr.plus(Integer.toBinaryString(remainder).padStart(4, '0'))
    }
    arr.reverse()
    return Integer.parseInt(arr.joinToString(""), 2)
}

fun Int.fromBCD(): Int {
    //lets say bcd is 593
    //how do we calculate it?
    //593 is of 100'ths place
    //lets mod up
    var placements = 1;
    var copy: Int = this
    var arr = Array(0) { "" }
    while (copy > 0){
        val remainder = copy % 10
        copy /= 10
        placements += 1
        arr = arr.plus(Integer.toBinaryString(remainder).padStart(4, '0'))
    }
    arr.reverse()
    return Integer.parseInt(arr.joinToString(""), 2)
}

fun Int.toBCDValue(): Int {
    var shift: Int
    val iterations: Int = Integer.toBinaryString(this).length / 4
    var multitude = 1
    var result = 0
    for (i in 0..iterations) {
        shift = i * 4
        result += ((this shr shift) and 0xF) * multitude
        multitude *= 10
    }
    return result
}


fun Int.Companion.toBCDValue(integer: Int): Int {
    var shift: Int
    val iterations: Int = Integer.toBinaryString(integer).length / 4
    var multitude = 1
    var result = 0
    for (i in 0..iterations) {
        shift = i * 4
        result += ((integer shr shift) and 0xF) * multitude
        multitude *= 10
    }
    return result
}

fun Int.toBinaryString(): String {
    return "0b${Integer.toBinaryString(this)}"
}

fun Int.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase()}"""
}

fun Short.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase().padStart(4, '0')} """
}

fun Byte.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase().padStart(2, '0')}"""
}

fun Byte.toBinaryString(): String {
    return """0b${(Integer.toBinaryString(this.toInt() and 0xFF)).toUpperCase()}"""
}
