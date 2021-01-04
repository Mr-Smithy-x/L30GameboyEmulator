package tests

import com.vonley.boards.z80.CPU
import com.vonley.boards.z80.instructions.Instruction
import com.vonley.boards.z80.memory.MMU
import com.vonley.boards.z80.registers.CPURegister
import com.vonley.boards.z80.registers.FlagRegister
import com.vonley.extensions.isSignedNeg
import com.vonley.extensions.signed
import com.vonley.extensions.toHexString
import junit.framework.TestCase

class JumpCallTests : TestCase() {

    lateinit var cpu: CPU
    val mmu: MMU
        get() {
            return cpu.mmu
        }
    val register: CPURegister
        get() {
            return cpu.cpuRegister
        }
    val fr: FlagRegister
        get() {
            return register.fr
        }
    val instruction: Instruction
        get() {
            return cpu.instruction
        }

    override fun setUp() {
        super.setUp()
        cpu = CPU()
        mmu.writeShort(0x82FDu, 0xED1Du)
        register.pc = 0x82FDu
    }

    fun testUnsignedSignedShit(){
        val v: UByte = 255u
        val value: UByte = (v and 0x80u)
        println("${v.toHexString()} - ${value.toHexString()}");
        println("${v} - ${value} , ${value and 0x7Fu}");
    }

    fun testDda(){
        val v: UByte = 0xFFu
        val o = v or 1u > 0xFFu
        println(o)

    }

    fun testSignedMath() {
        val readByte = mmu.readByte(register.fetchIncByte)
        val value: UByte = readByte.signed
        if(readByte.isSignedNeg){
            register.pc = register.pc.minus(value).toUShort()
        }else{
            register.pc = register.pc.plus(value).toUShort()
        }

        println(register.pc.toHexString())
    }

}