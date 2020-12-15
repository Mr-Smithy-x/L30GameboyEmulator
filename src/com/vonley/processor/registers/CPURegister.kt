package com.vonley.processor.registers

import com.vonley.extensions.toBinaryString
import com.vonley.extensions.toHexString

class CPURegister {

    val fr = FlagRegister()

    //HI            //LO
    var a: UByte = 0x0u //Accumulator & Flags

    var b: UByte = 0x0u
    var c: UByte = 0x0u  //BC
    var d: UByte = 0x0u
    var e: UByte = 0x0u  //DE
    var h: UByte = 0x0u
    var l: UByte = 0x0u //HL

    //Stack Pointer
    var sp: UShort = 0x0u

    //Program Counter
    var pc: UShort = 0x0u

    var hl: UShort
        get() = ((((h and 0xFFu).toUInt() shl 8) or (l and 0xFFu).toUInt()) and 0xFFFFu).toUShort()
        set(value) {
            h = ((value.toUInt() shr 8) and 0xFFu).toUByte()
            l = ((value.toUInt() and 0xFFu).toUByte())
        }

    var de: UShort
        get() = ((((d and 0xFFu).toUInt() shl 8) or (e and 0xFFu).toUInt()) and 0xFFFFu).toUShort()
        set(value) {
            d = ((value.toUInt() shr 8) and 0xFFu).toUByte()
            e = ((value.toUInt() and 0xFFu).toUByte())
        }

    var bc: UShort
        get() = ((((b and 0xFFu).toUInt() shl 8) or (c and 0xFFu).toUInt()) and 0xFFFFu).toUShort()
        set(value) {
            b = ((value.toUInt() shr 8) and 0xFFu).toUByte()
            c = ((value.toUInt() and 0xFFu).toUByte())
        }

    var af: UShort
        get() = ((((a and 0xFFu).toUInt() shl 8) or (fr.byte and 0xFFu).toUInt()) and 0xFFFFu).toUShort()
        set(value) {
            a = ((value.toUInt() shr 8) and 0xFFu).toUByte()
            fr.byte = ((value.toUInt() and 0xFFu).toUByte())
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