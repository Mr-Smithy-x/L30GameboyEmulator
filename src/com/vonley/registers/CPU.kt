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

}