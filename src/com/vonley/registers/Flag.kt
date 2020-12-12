package com.vonley.registers

object Flag {
    //bit 7 - SET = Z, Clr = NZ, Zero Flag
    var nz: Int = 0
    //bit 6 SET - nil, Clr = nil, Add/Sub-Flag (BCD)
    var n: Int = 0
    //bit 5 SET - nil, Clr = nil, Half Carry Flag (BCD)
    var h: Int = 0
    //bit 4 SET = C, Clr = NC - Carry Flag
    var cy: Int = 0
    //bit 3-0 not used (always zero)

}