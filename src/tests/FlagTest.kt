package tests

import com.vonley.processor.registers.CPURegister
import com.vonley.processor.registers.FlagRegister
import junit.framework.TestCase
import org.junit.Test

/**
 * 0 - 8 signed range is -128 - 127
 * 0 - 8 unsigned range is 0-256
 * 0xFF = maxrange
 * Reference Bitwise Test class for understanding
 * @see BitWiseTests
 */
class FlagTest : TestCase() {


    lateinit var cpuRegister: CPURegister
    val flagRegister: FlagRegister
        get() {
            return cpuRegister.fr
        }

    override fun setUp() {
        super.setUp()
        cpuRegister = CPURegister()
    }

    @Test
    fun testFlagByte() {
        flagRegister.zf = false
        flagRegister.n = true
        flagRegister.h = true

        assert(flagRegister.byte == 0b01100000)
        flagRegister.zf = true
        assert(flagRegister.byte == 0b11100000)
        flagRegister.byte = 0b10000000
        assert(flagRegister.zf)
        assert(flagRegister.n == false)
        assert(flagRegister.h == false)
        println(cpuRegister.fr)
    }

    @Test
    fun testAccumulatorWithFlag() {
        flagRegister.byte = 0b11010000
        cpuRegister.a = 0x2F
        assert(flagRegister.byte == 0xD0)
        assert(cpuRegister.af == 0x2FD0)
        cpuRegister.af = 0x2FF0
        assert(cpuRegister.a == 0x2F)
        assert(flagRegister.byte == 0xf0)
        println(cpuRegister)
    }

}

