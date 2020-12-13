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
            A  :    ${a.toHexString()}        F : ${fr.byte.toHexString()}  / F  : ${fr.byte.toBinaryString()}
            H  :    ${h.toHexString()}        L : ${l.toHexString()}    / HL : ${hl.toHexString()}
            B  :    ${b.toHexString()}        C : ${b.toHexString()}    / BC : ${bc.toHexString()}
            D  :    ${d.toHexString()}        E : ${e.toHexString()}    / DE : ${de.toHexString()}
            AF :    ${af.toHexString()}
            
            
        """.trimIndent() + fr
    }
}