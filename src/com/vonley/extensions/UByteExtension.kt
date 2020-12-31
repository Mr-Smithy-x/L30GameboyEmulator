package com.vonley.extensions

import com.vonley.boards.z80.registers.BitCarryData

fun UByte.rr(carryBit: UByte): BitCarryData {
    var binary = this
    val tempCarryBit = carryBit
    val carry = binary and 0b1u
    binary = (binary shr 1) or (tempCarryBit shl 7)
    return BitCarryData(carry, binary)
}

fun UByte.rl(carryBit: UByte): BitCarryData {
    var binary = this
    val tempCarryBit = carryBit
    val carry = (binary shr 7) and 0b1u
    binary = (binary shl 1) or tempCarryBit
    return BitCarryData(carry, binary)
}

val UByte.rrc: BitCarryData
    get() {
        var binary = this
        val carry = binary and 0b1u
        binary = (binary shr 1) or (carry shl 7)
        return BitCarryData(carry, binary)
    }

val UByte.rlc: BitCarryData
    get() {
        var binary = this
        val carry = (binary shr 7) and 0b1u
        binary = (binary shl 1) or carry
        return BitCarryData(carry, binary)
    }

val UByte.sla: BitCarryData
    get() {
        var binary = this
        val carry = (binary shr 7) and 0b1u
        binary = binary shl 1
        return BitCarryData(carry, binary)
    }

val UByte.sll: BitCarryData
    get() {
        var binary = this
        val carry = (binary shr 7) and 0b1u
        binary = binary shl 1 or 0b1.toUByte()
        return BitCarryData(carry, binary)
    }

val UByte.sra: BitCarryData
    get() {
        var binary = this
        val carry = binary and 0b1u.toUByte()
        binary = (binary shr 1) or (binary and (binary shr 7 and 0b1u.toUByte()) shl 7)
        return BitCarryData(carry, binary)
    }

val UByte.srl: BitCarryData
    get() {
        var binary = this
        val carry = this and 0b1u.toUByte()
        binary = binary shr 1
        return BitCarryData(carry, binary)
    }

infix fun UByte.shr(bitCount: Int): UByte {
    return (toUInt() shr bitCount).and(0xFFu).toUByte()
}

infix fun UByte.shl(bitCount: Int): UByte {
    return (toUInt() shl bitCount).and(0xFFu).toUByte()
}

fun UByte.isZero(): Boolean {
    return this == 0x0u.toUByte()
}

/**
 * Checks for a carry from bit 3 to bit 4
 * mask off upper halves of bytes as we're really only interested in 4 bit addition for this
 * AND the sum above against 0x10 (which in short checks if the 4th bit (0-7) was set,
 * @param compare - Second byte being tested.
 */
fun UByte.halfCarry(compare: UByte, carryFlag: Boolean = false): UByte {
    val sum = (this and 0xFu) + (compare and 0xFu) + carryFlag.asUByte
    return (sum and 0x10u).toUByte()
}

fun UByte.checkHalfCarry(compare: UByte): Boolean {
    return this.checkHalfCarryAdd(compare, false)
}

/**
 * Checks for a carry from bit 3 to bit 4 during addition.
 * @param compare - Second byte being tested.
 */
fun UByte.checkHalfCarryAdd(compare: UByte, carryFlag: Boolean = false): Boolean {
    return halfCarry(compare, carryFlag) == 0x10u.toUByte()
}