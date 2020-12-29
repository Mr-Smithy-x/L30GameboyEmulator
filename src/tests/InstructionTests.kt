package tests

import com.vonley.boards.z80.CPU
import com.vonley.boards.z80.instructions.Instruction
import com.vonley.extensions.*
import junit.framework.TestCase
import org.junit.jupiter.api.assertThrows

/**
 *
 * Reference: https://www.chibiakumas.com/z80/ - Carry Flag Section
 */
class InstructionTests : TestCase() {

    lateinit var cpu: CPU
    lateinit var instruction: Instruction

    val startValue: UByte = 0b10011001u
    val carryBit: UByte = 0x0u

    override fun setUp() {
        super.setUp()
        cpu = CPU()
        instruction = Instruction()
    }

    fun testCPLInstructions() {
        // 01001 - 01001 = 9     9
        // ~     - 10110 = 22  -10
        val byte: UByte = 0b1001u
        val byte2: UByte = 0b1111u
        val byte3: UByte = 0b0001u
        println(
            """
            ${byte.toBinaryString()}: ${(byte.inv()).toBinaryString()}
            ${byte2.toBinaryString()}: ${(byte2.inv()).toBinaryString()}
            ${byte3.toBinaryString()}: ${(byte3.inv()).toBinaryString()}
        """.trimIndent()
        )
    }

    //z00c
    fun `testRRC AInstruction`() {
        val rccA = startValue.rcc_a
        assert(rccA.carry == 0b1u.toUByte())
        assert(rccA.result == 0b11001100u.toUByte())
    }

    //z00c
    fun `testRLC AInstruction`() {
        val rlcA = startValue.rlc_a
        assert(rlcA.carry == 0b1u.toUByte())
        assert(rlcA.result == 0b00110011u.toUByte())
    }

    //z00c
    fun `testRR AInstruction`() {
        val rrA = startValue.rr_a(carryBit)
        assert(rrA.carry == 0b1u.toUByte())
        assert(rrA.result == 0b01001100u.toUByte())
    }

    //z00c
    fun `testRL AInstruction`() {
        val rlA = startValue.rl_a(carryBit)
        assert(rlA.carry == 0b1u.toUByte())
        assert(rlA.result == 0b00110010u.toUByte())
    }

    //z00c
    fun `testSLA AInstruction`() {
        val slaA = startValue.sla_a
        assert(slaA.carry == 0b1u.toUByte())
        assert(slaA.result == 0b00110010u.toUByte())
    }

    //z00c
    fun `testSLL AInstruction`() {
        val sllA = startValue.sll_a
        assert(sllA.carry == 0b1u.toUByte())
        assert(sllA.result == 0b00110011u.toUByte())
    }

    //z00c
    fun `testSRA AInstruction`() {
        val sraA = startValue.sra_a
        println(sraA)
        assert(sraA.carry == 0b1u.toUByte())
        assert(sraA.result == 0b11001100u.toUByte())
    }

    //z00c
    fun `testSRL AInstruction`() {
        val srlA = startValue.srl_a
        println(srlA)
        assert(srlA.carry == 0b1u.toUByte())
        assert(srlA.result == 0b01001100u.toUByte())
    }

    fun testThrowNotImplementedError() {
        assertThrows<NotImplementedError> {
            instruction[instruction.keys.first()]?.execute(cpu.mmu, cpu.cpuRegister)
        }
    }
}

