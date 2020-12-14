package com.vonley.processor.registers

import com.vonley.extensions.toBinaryString
import com.vonley.extensions.toHexString

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

    var byte: Byte
        get() {
            var register = 0;
            if (zf) {
                register = register xor (1 shl 7)
            }
            if (n) {
                register = register xor (1 shl 6)
            }
            if (h) {
                register = register xor (1 shl 5)
            }
            if (cy) {
                register = register xor (1 shl 4)
            }
            return (register and 0xFF).toByte()
        }
        set(value) {
            zf = (value.toInt() and 0xFF shr 7) and 1 == 1
            n = (value.toInt() and 0xFF shr 6) and 1 == 1
            h = (value.toInt() and 0xFF shr 5) and 1 == 1
            cy = (value.toInt() and 0xFF shr 4) and 1 == 1
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