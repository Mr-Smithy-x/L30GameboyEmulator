package tests

import com.vonley.boards.z80.CPU
import com.vonley.boards.z80.instructions.Instruction
import com.vonley.extensions.toBinaryString
import junit.framework.TestCase
import org.junit.jupiter.api.assertThrows

class InstructionTests: TestCase() {

    lateinit var cpu: CPU
    lateinit var instruction: Instruction

    override fun setUp() {
        super.setUp()
        cpu = CPU()
        instruction = Instruction()
    }

    fun testCPLInstructions(){
        // 01001 - 01001 = 9     9
        // ~     - 10110 = 22  -10
        var byte:UByte = 0b1001u
        var byte2:UByte = 0b1111u
        var byte3:UByte = 0b0001u
        println("""
            ${byte.toBinaryString()}: ${(byte.inv()).toBinaryString()}
            ${byte2.toBinaryString()}: ${(byte2.inv()).toBinaryString()}
            ${byte3.toBinaryString()}: ${(byte3.inv()).toBinaryString()}
        """.trimIndent())
    }

    fun testThrowNotImplementedError(){
        assertThrows<NotImplementedError> {
            instruction[instruction.keys.first()]?.execute(cpu.mmu, cpu.cpuRegister)
        }
    }

}
