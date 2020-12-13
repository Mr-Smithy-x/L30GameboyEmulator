package com.vonley.processor

import com.vonley.processor.registers.CPURegister
import com.vonley.processor.registers.FlagRegister

class CPU {


    override fun toString(): String {
        return cpuRegister.toString()
    }

    companion object {
        val mmu = MemoryManagementUnit()
        val cpuRegister = CPURegister()
        val flagRegister: FlagRegister
            get() {
                return cpuRegister.fr
            }
    }

}