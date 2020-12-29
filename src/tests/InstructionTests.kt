package tests

import com.vonley.boards.z80.CPU
import com.vonley.boards.z80.instructions.Instruction
import com.vonley.extensions.shl
import com.vonley.extensions.shr
import com.vonley.extensions.toBinaryString
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
    fun `testRRC AInstruction`(){
        var binary = startValue
        val carry = binary and 0b1u
        binary = (binary shr 1) or (carry shl 7)
        assert(carry == 0b1u.toUByte())
        assert(binary == 0b11001100u.toUByte())
    }

    //z00c
    fun `testRLC AInstruction`(){
        var binary = startValue
        val carry = (binary shr 7) and 0b1u
        binary = (binary shl 1) or carry
        assert(carry == 0b1u.toUByte())
        assert(binary == 0b00110011u.toUByte())
    }

    //z00c
    fun `testRR AInstruction`(){
        var binary = startValue
        val tempCarryBit = carryBit
        val carry = binary and 0b1u
        binary = (binary shr 1) or (tempCarryBit shl 7)
        assert(carry == 0b1u.toUByte())
        assert(binary == 0b01001100u.toUByte())
    }

    //z00c
    fun `testRL AInstruction`(){
        var binary = startValue
        val tempCarryBit = carryBit
        val carry = (binary shr 7) and 0b1u
        binary = (binary shl 1) or tempCarryBit
        assert(carry == 0b1u.toUByte())
        assert(binary == 0b00110010u.toUByte())
    }

    //z00c
    fun `testSLA AInstruction`(){
        var binary = startValue
        val carry = (binary shr 7) and 0b1u
        binary = binary shl 1
        assert(carry == 0b1u.toUByte())
        assert(binary == 0b00110010u.toUByte())
    }


    //z00c
    fun `testSLL AInstruction`(){
        var binary = startValue
        val carry = (binary shr 7) and 0b1u
        binary = binary shl 1 or 0b1.toUByte()
        assert(carry == 0b1u.toUByte())
        assert(binary == 0b00110011u.toUByte())
    }

    //z00c
    fun `testSRA AInstruction`(){
        var binary = startValue
        val carry = binary and 0b1u.toUByte()
        binary = (binary shr 1) or (binary and (binary shr 7 and 0b1u.toUByte()) shl 7)
        assert(carry == 0b1u.toUByte())
        assert(binary == 0b11001100u.toUByte())
    }

    //z00c
    fun `testSRL AInstruction`(){
        var binary = startValue
        val carry = binary and 0b1u.toUByte()
        binary = binary shr 1
        assert(carry == 0b1u.toUByte())
        assert(binary == 0b01001100u.toUByte())
    }

    fun testThrowNotImplementedError() {
        assertThrows<NotImplementedError> {
            instruction[instruction.keys.first()]?.execute(cpu.mmu, cpu.cpuRegister)
        }
    }

}
