package com.vonley.processor.registers

import com.vonley.extensions.toBinaryString
import com.vonley.extensions.toHexString

class CPURegister {

    val fr = FlagRegister()

    //HI            //LO
    var a: Byte = 0 //Accumulator & Flags

    var b: Byte = 0;
    var c: Byte = 0  //BC
    var d: Byte = 0;
    var e: Byte = 0  //DE
    var h: Byte = 0;
    var l: Byte = 0  //HL

    //Stack Pointer
    var sp: Short = 0

    //Program Counter
    var pc: Short = 0

    var hl: Short
        get() = ((((h.toInt() and 0xFF shl 8)) or (l.toInt() and 0xFF)) and 0xFFFF).toShort()
        set(value) {
            h = ((value.toInt() shr 8) and 0xFF).toByte()
            l = ((value.toInt() and 0xFF).toByte())
        }

    var de: Short
        get() = ((((d.toInt() and 0xFF shl 8)) or (e.toInt() and 0xFF)) and 0xFFFF).toShort()
        set(value) {
            d = ((value.toInt() shr 8) and 0xFF).toByte()
            e = ((value.toInt() and 0xFF).toByte())
        }

    var bc: Short
        get() = ((((b.toInt() and 0xFF shl 8)) or (c.toInt() and 0xFF)) and 0xFFFF).toShort()
        set(value) {
            b = ((value.toInt() shr 8) and 0xFF).toByte()
            c = ((value.toInt() and 0xFF).toByte())
        }

    var af: Short
        get() {
            return ((((a.toInt() and 0xFF) shl 8) or (fr.byte.toInt() and 0xFF)) and 0xFFFF).toShort()
        }
        set(value) {
            a = ((value.toInt() shr 8) and 0xFF).toByte()
            fr.byte = ((value.toInt() and 0xFF).toByte())
        }

    override fun toString(): String {
        return """
            CPU:
            A  :    ${a.toHexString()}        F : ${fr.byte.toHexString()}        / F  : ${fr.byte.toBinaryString()}
            H  :    ${h.toHexString()}        L : ${l.toHexString()}        / HL : ${hl.toHexString()}
            B  :    ${b.toHexString()}        C : ${c.toHexString()}        / BC : ${bc.toHexString()}
            D  :    ${d.toHexString()}        E : ${e.toHexString()}        / DE : ${de.toHexString()}
            AF :    ${af.toHexString()}
            
            
        """.trimIndent() + fr
    }
}