package com.vonley.extensions
import com.vonley.boards.z80.memory.MMU

infix fun UShort.shr(bitCount: Int): UShort {
    return (toUInt() shr bitCount).and(0xFFFFu).toUShort()
}

infix fun UShort.shl(bitCount: Int): UShort {
    return (toUInt() shl bitCount).and(0xFFFFu).toUShort()
}

fun UShort.getRegion(bootRomEnabled: Boolean = false): MMU.Region {
    return MMU.Region.parse(this, bootRomEnabled)
}



/**
 * Checks for a carry from bit 3 to bit 4
 * mask off upper halves of bytes as we're really only interested in 4 bit addition for this
 * AND the sum above against 0x10 (which in short checks if the 4th bit (0-7) was set,
 * @param compare - Second byte being tested.
 */
fun UShort.halfCarry(compare: UShort, carryFlag: Boolean = false): UShort {
    val sum = (this and 0x0FFFu) + (compare and 0x0FFFu) + carryFlag.asUByte
    return sum.toUShort()
}

fun UShort.checkHalfCarry(compare: UShort): Boolean {
    return this.checkHalfCarryADC(compare, false)
}

/**
 * Checks for a carry from bit 7 to bit 8 during addition.
 * @param compare - Second byte being tested.
 * @param carryFlag - For ADC: If carry flag is 1, set true, false if 0
 */
fun UShort.checkCarryADC(compare: UShort, carryFlag: Boolean = false): Boolean {
    return (this + compare + if (carryFlag) 1u else 0u) > 0xFFFFu
}

fun UShort.checkCarryAdd(compare: UShort): Boolean {
    return this.checkCarryADC(compare, false)
}


/**
 * Checks for a carry from bit 3 to bit 4 during addition.
 * @param compare - Second byte being tested.
 */
fun UShort.checkHalfCarryADC(compare: UShort, carryFlag: Boolean = false): Boolean {
    return halfCarry(compare, carryFlag) > 0x0FFFu
}

/**
 * Checks for a carry from bit 3 to bit 4 during subtraction.
 * @param compare - Second byte being tested.
 */
fun UShort.checkHalfCarrySub(compare: UShort): Boolean {
    return (this and 0xFu) < (compare and 0xFu)
}
