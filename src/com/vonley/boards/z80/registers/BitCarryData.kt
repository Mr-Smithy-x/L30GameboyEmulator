package com.vonley.boards.z80.registers

import com.vonley.extensions.toBinaryString

data class BitCarryData(var carry: UByte, var result: UByte) {

    override fun toString(): String {
        return """
            Result: ${result.toBinaryString()} 
            Carry: ${carry.toBinaryString()}
        """.trimIndent()
    }

}
