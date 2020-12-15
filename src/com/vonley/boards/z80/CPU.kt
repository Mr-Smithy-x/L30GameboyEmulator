package com.vonley.boards.z80

import com.vonley.boards.z80.memory.MMU
import com.vonley.boards.z80.registers.CPURegister
import com.vonley.boards.z80.registers.FlagRegister

class CPU {


    override fun toString(): String {
        return cpuRegister.toString()
    }

    companion object {
        val mmu = MMU()
        val cpuRegister = CPURegister()
        val flagRegister: FlagRegister
            get() {
                return cpuRegister.fr
            }
    }

}