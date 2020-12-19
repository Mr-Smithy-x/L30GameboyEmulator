package tests

import com.vonley.boards.z80.CPU
import com.vonley.boards.z80.instructions.Instruction
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

    fun testThrowNotImplementedError(){
        assertThrows<NotImplementedError> {
            instruction[instruction.keys.first()]?.execute(cpu.mmu, cpu.cpuRegister)
        }
    }

}
