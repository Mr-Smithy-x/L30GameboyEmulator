package com.vonley.registers

object CPU {

    //HI            //LO
    var a: Int = 0 //Accumulator & Flags

    var b: Int = 0; var c: Int = 0  //BC
    var d: Int = 0; var e: Int = 0  //DE
    var h: Int = 0; var l: Int = 0  //HL

    //Stack Pointer
    var sp: Int = 0
    //Program Counter
    var pc: Int = 0

    val hl: Int
        get() {
            //(h << 8) | l
            return (h shl 8) or l
        }

    val de: Int
        get() {
            return (d shl 8) or e
        }

    val bc: Int
        get() {
            return (b shl 8) or c
        }

}