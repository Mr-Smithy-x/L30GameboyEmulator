package com.vonley.extensions

fun Int.fromBCD(): Int = Int.fromBCD(this)

fun Int.toBCDValue(): Int = Int.toBCDValue(this)

fun Int.Companion.fromBCD(bcd: Int): Int {
    //lets say bcd is 593
    //how do we calculate it?
    //593 is of 100'ths place
    //lets mod up
    var placements = 1;
    var copy: Int = bcd
    var arr = Array(0) { "" }
    while (copy > 0) {
        val remainder = copy % 10
        copy /= 10
        placements += 1
        arr = arr.plus(Integer.toBinaryString(remainder).padStart(4, '0'))
    }
    arr.reverse()
    return Integer.parseInt(arr.joinToString(""), 2)
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