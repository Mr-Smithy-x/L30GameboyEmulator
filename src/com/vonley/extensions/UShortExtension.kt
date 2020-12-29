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
