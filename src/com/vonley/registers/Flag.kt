package com.vonley.registers

object Flag {
    //bit 7 - SET = Z, Clr = NZ, Zero Flag
    var nz: Boolean = false

    //bit 6 SET - nil, Clr = nil, Add/Sub-Flag (BCD)
    var n: Boolean = false

    //bit 5 SET - nil, Clr = nil, Half Carry Flag (BCD)
    var h: Boolean = false

    //bit 4 SET = C, Clr = NC - Carry Flag
    var cy: Boolean = false
    //bit 3-0 not used (always zero)

    var flagByte: Int
        get() {
            var register = 0;
            if (nz) {
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
            return register
        }
        set(value) {
            nz = (value shr 7) and 1 == 1
            n = (value shr 6) and 1 == 1
            h = (value shr 5) and 1 == 1
            cy = (value shr 4) and 1 == 1
        }

}