package com.vonley.boards.z80.registers

import com.vonley.extensions.*

class FlagRegister {
    //bit 7 - SET = Z, Clr = NZ, Zero Flag
    var z: Boolean = false

    //bit 6 SET - nil, Clr = nil, Add/Sub-Flag (BCD)
    var n: Boolean = false

    //bit 5 SET - nil, Clr = nil, Half Carry Flag (BCD)
    var h: Boolean = false

    //bit 4 SET = C, Clr = NC - Carry Flag
    var c: Boolean = false
    //bit 3-0 not used (always zero)

    var byte: UByte
        get() {
            var register: UInt = 0x0u;
            if (z) {
                register = register xor (1u shl 7)
            }
            if (n) {
                register = register xor (1u shl 6)
            }
            if (h) {
                register = register xor (1u shl 5)
            }
            if (c) {
                register = register xor (1u shl 4)
            }
            return ((register and 0xFFu).toUByte())
        }
        set(value) {
            z = (value.toUInt() and 0xFFu shr 7) and 0x01u == 0x01u
            n = (value.toUInt() and 0xFFu shr 6) and 0x01u == 0x01u
            h = (value.toUInt() and 0xFFu shr 5) and 0x01u == 0x01u
            c = (value.toUInt() and 0xFFu shr 4) and 0x01u == 0x01u
        }

    override fun toString(): String {
        return """
            FLAG:
            z   (Zero Flag)         : $z
            n   (Add/Sub-Flag)      : $n
            h   (Half Carry Flag)   : $h
            c   (Carry Flag)        : $c
            
            hex: ${byte.toHexString()}
            bin: ${byte.toBinaryString()}
        """.trimIndent()
    }

    fun reset() {

    }

}