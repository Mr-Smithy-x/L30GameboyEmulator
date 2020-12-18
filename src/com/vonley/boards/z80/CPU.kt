package com.vonley.boards.z80

import com.vonley.boards.z80.instructions.Instruction
import com.vonley.boards.z80.memory.MMU
import com.vonley.boards.z80.registers.CPURegister
import com.vonley.boards.z80.registers.FlagRegister

class CPU {

    val mmu = MMU()
    val cpuRegister = CPURegister()
    val instruction = Instruction()
    var running = false
    val flagRegister: FlagRegister
        get() = cpuRegister.fr


    fun step() {
        val pc = cpuRegister.pc
        val op = instruction[pc]
        op!!.execute(mmu, cpuRegister)

    }

    fun power(on: Boolean) {
        if (!running) {
            cpuRegister.pc = 0x100u //boot
        } else {
            mmu.reset()
            cpuRegister.reset()
        }
        running = on
    }

    override fun toString(): String {
        return cpuRegister.toString()
    }


    companion object {
    }

}