package com.vonley.processor.registers

import com.vonley.extensions.toBinaryString
import com.vonley.extensions.toHexString

class CPURegister {

    val fr = FlagRegister()

    //HI            //LO
    var a: Int = 0 //Accumulator & Flags

    var b: Int = 0;
    var c: Int = 0  //BC
    var d: Int = 0;
    var e: Int = 0  //DE
    var h: Int = 0;
    var l: Int = 0  //HL

    //Stack Pointer
    var sp: Int = 0

    //Program Counter
    var pc: Int = 0

    var hl: Int
        get() = (h shl 8) or l
        set(value) {
            h = (value shr 8)
            l = (value and 0xFF)
        }

    var de: Int
        get() = (d shl 8) or e
        set(value) {
            d = (value shr 8)
            e = (value and 0xFF)
        }

    var bc: Int
        get() = (b shl 8) or c
        set(value) {
            b = (value shr 8)
            c = (value and 0xFF)
        }

    var af: Int
        get() {
            return (a shl 8) or fr.byte
        }
        set(value) {
            a = (value shr 8)
            fr.byte = (value and 0xFF)
        }

    override fun toString(): String {
        return """
            CPU:
            A  :    0x${a.toHexString()}        F : 0x${fr.byte.toHexString()}  / F  : 0b${fr.byte.toBinaryString()}
            H  :    0x${h.toHexString()}        L : 0x${l.toHexString()}    / HL : 0x${hl.toHexString()}
            B  :    0x${b.toHexString()}        C : 0x${b.toHexString()}    / BC : 0x${bc.toHexString()}
            D  :    0x${d.toHexString()}        E : 0x${e.toHexString()}    / DE : 0x${de.toHexString()}
            AF :    0x${af.toHexString()}
            
            
        """.trimIndent() + fr
    }
}