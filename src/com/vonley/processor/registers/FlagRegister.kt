package com.vonley.processor.registers

import com.vonley.extensions.*

class FlagRegister {
    //bit 7 - SET = Z, Clr = NZ, Zero Flag
    var zf: Boolean = false

    //bit 6 SET - nil, Clr = nil, Add/Sub-Flag (BCD)
    var n: Boolean = false

    //bit 5 SET - nil, Clr = nil, Half Carry Flag (BCD)
    var h: Boolean = false

    //bit 4 SET = C, Clr = NC - Carry Flag
    var cy: Boolean = false
    //bit 3-0 not used (always zero)

    var byte: UByte
        get() {
            var register: UInt = 0x0u;
            if (zf) {
                register = register xor (1u shl 7)
            }
            if (n) {
                register = register xor (1u shl 6)
            }
            if (h) {
                register = register xor (1u shl 5)
            }
            if (cy) {
                register = register xor (1u shl 4)
            }
            return ((register and 0xFFu).toUByte())
        }
        set(value) {
            zf = (value.toUInt() and 0xFFu shr 7) and 0x01u == 0x01u
            n = (value.toUInt() and 0xFFu shr 6) and 0x01u == 0x01u
            h = (value.toUInt() and 0xFFu shr 5) and 0x01u == 0x01u
            cy = (value.toUInt() and 0xFFu shr 4) and 0x01u == 0x01u
        }

    override fun toString(): String {
        return """
            FLAG:
            zf  (Zero Flag)         : $zf
            n   (Add/Sub-Flag)      : $n
            h   (Half Carry Flag)   : $h
            cy  (Carry Flag)        : $cy
            
            hex: ${byte.toHexString()}
            bin: ${byte.toBinaryString()}
        """.trimIndent()
    }

}