package com.vonley.extensions

import com.vonley.boards.z80.registers.BitCarryData

fun UByte.rr_a(carryBit: UByte): BitCarryData {
    var binary = this
    val tempCarryBit = carryBit
    val carry = binary and 0b1u
    binary = (binary shr 1) or (tempCarryBit shl 7)
    return BitCarryData(carry, binary)
}

fun UByte.rl_a(carryBit: UByte): BitCarryData {
    var binary = this
    val tempCarryBit = carryBit
    val carry = (binary shr 7) and 0b1u
    binary = (binary shl 1) or tempCarryBit
    return BitCarryData(carry, binary)
}

val UByte.rcc_a: BitCarryData
    get() {
        var binary = this
        val carry = binary and 0b1u
        binary = (binary shr 1) or (carry shl 7)
        return BitCarryData(carry, binary)
    }

val UByte.rlc_a: BitCarryData
    get() {
        var binary = this
        val carry = (binary shr 7) and 0b1u
        binary = (binary shl 1) or carry
        return BitCarryData(carry, binary)
    }

val UByte.sla_a: BitCarryData
    get() {
        var binary = this
        val carry = (binary shr 7) and 0b1u
        binary = binary shl 1
        return BitCarryData(carry, binary)
    }

val UByte.sll_a: BitCarryData
    get() {
        var binary = this
        val carry = (binary shr 7) and 0b1u
        binary = binary shl 1 or 0b1.toUByte()
        return BitCarryData(carry, binary)
    }

val UByte.sra_a: BitCarryData
    get() {
        var binary = this
        val carry = binary and 0b1u.toUByte()
        binary = (binary shr 1) or (binary and (binary shr 7 and 0b1u.toUByte()) shl 7)
        return BitCarryData(carry, binary)
    }

val UByte.srl_a: BitCarryData
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

