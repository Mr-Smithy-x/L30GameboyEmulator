package com.vonley.boards.z80.registers

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

    var f: UByte
        get() = fr.byte
        set(value) {
            fr.byte = value
        }

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
        get() = ((((a and 0xFFu).toUInt() shl 8) or (f and 0xFFu).toUInt()) and 0xFFFFu).toUShort()
        set(value) {
            a = ((value.toUInt() shr 8) and 0xFFu).toUByte()
            f = ((value.toUInt() and 0xFFu).toUByte())
        }

    fun addPC(number: UShort) {
        pc = pc.plus(number).and(0xFFFFu).toUShort()
    }

    fun addSP(number: UShort) {
        sp = sp.plus(number).and(0xFFFFu).toUShort()
    }

    fun minusPC(number: UShort) {
        pc = pc.minus(number).and(0xFFFFu).toUShort()
    }

    fun minusSP(number: UShort) {
        sp = sp.minus(number).and(0xFFFFu).toUShort()
    }

    fun incHL() {
        hl = hl.plus(0x1u).and(0xFFFFu).toUShort()
    }

    fun decHL() {
        hl = hl.minus(1u).and(0xFFFFu).toUShort()
    }


    override fun toString(): String {
        return """
            CPU:
            A  :    ${a.toHexString()}        F : ${f.toHexString()}        / F  : ${f.toBinaryString()}
            H  :    ${h.toHexString()}        L : ${l.toHexString()}        / HL : ${hl.toHexString()}
            B  :    ${b.toHexString()}        C : ${c.toHexString()}        / BC : ${bc.toHexString()}
            D  :    ${d.toHexString()}        E : ${e.toHexString()}        / DE : ${de.toHexString()}
            AF :    ${af.toHexString()}
            
            
        """.trimIndent() + fr
    }

    fun reset() {
        fr.reset()
    }



}