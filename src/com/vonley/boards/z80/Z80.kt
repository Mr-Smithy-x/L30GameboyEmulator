package com.vonley.boards.z80

import com.vonley.boards.z80.components.CPU
import com.vonley.boards.z80.components.Rom
import com.vonley.boards.z80.memory.MMU
import com.vonley.boards.z80.registers.CPURegister

class Z80 {

    val mmu = MMU()
    val cpuRegister = CPURegister()
    val cpu = CPU(mmu, cpuRegister)

    lateinit var rom: Rom

    fun setRom(file: String) {
        rom = Rom.load(file)
        mmu.loadROM(rom)
    }

}