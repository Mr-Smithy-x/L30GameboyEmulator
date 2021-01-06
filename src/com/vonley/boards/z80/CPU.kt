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

    fun power(on: Boolean, sgb: Boolean = false) {
        if (!running) {
            cpuRegister.af = 0x01B0u
            cpuRegister.bc = 0x0013u
            cpuRegister.hl = 0x00D8u
            cpuRegister.sp = 0xFFFEu
            cpuRegister.pc = 0x100u //boot
            mmu.writeByte(0xFF05u, 0x00u)  // TIMA
            mmu.writeByte(0xFF06u, 0x00u)  // TMA
            mmu.writeByte(0xFF07u, 0x00u)  // TAC
            mmu.writeByte(0xFF10u, 0x80u)  // NR10
            mmu.writeByte(0xFF11u, 0xBFu)  // NR11
            mmu.writeByte(0xFF12u, 0xF3u)  // NR12
            mmu.writeByte(0xFF14u, 0xBFu)  // NR14
            mmu.writeByte(0xFF16u, 0x3Fu)  // NR21
            mmu.writeByte(0xFF17u, 0x00u)  // NR22
            mmu.writeByte(0xFF19u, 0xBFu)  // NR24
            mmu.writeByte(0xFF1Au, 0x7Fu)  // NR30
            mmu.writeByte(0xFF1Bu, 0xFFu)  // NR31
            mmu.writeByte(0xFF1Cu, 0x9Fu)  // NR32
            mmu.writeByte(0xFF1Eu, 0xBFu)  // NR33
            mmu.writeByte(0xFF20u, 0xFFu)  // NR41
            mmu.writeByte(0xFF21u, 0x00u)  // NR42
            mmu.writeByte(0xFF22u, 0x00u)  // NR43
            mmu.writeByte(0xFF23u, 0xBFu)  // NR30
            mmu.writeByte(0xFF24u, 0x77u)  // NR50
            mmu.writeByte(0xFF25u, 0xF3u)  // NR51
            mmu.writeShort(0xFF26u, if (sgb) 0xF0u else 0xF1u) //NR52
            mmu.writeByte(0xFF40u, 0x91u)   // LCDC
            mmu.writeByte(0xFF42u, 0x00u)   // SCY
            mmu.writeByte(0xFF43u, 0x00u)   // SCX
            mmu.writeByte(0xFF45u, 0x00u)   // LYC
            mmu.writeByte(0xFF47u, 0xFCu)   // BGP
            mmu.writeByte(0xFF48u, 0xFFu)   // OBP0
            mmu.writeByte(0xFF49u, 0xFFu)   // OBP1
            mmu.writeByte(0xFF4Au, 0x00u)   // WY
            mmu.writeByte(0xFF4Bu, 0x00u)   // WX
            mmu.writeByte(0xFFFFu, 0x00u)   // IE
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