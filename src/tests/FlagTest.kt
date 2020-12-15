package tests

import com.vonley.boards.z80.registers.CPURegister
import com.vonley.boards.z80.registers.FlagRegister
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

        assert(flagRegister.byte == (0b01100000u and 0xFFu).toUByte())
        flagRegister.zf = true
        assert(flagRegister.byte == (0b11100000u and 0xFFu).toUByte())
        flagRegister.byte = (0b10000000u and 0xFFu).toUByte()
        assert(flagRegister.zf)
        assert(flagRegister.n == false)
        assert(flagRegister.h == false)
        println(cpuRegister.fr)
    }

    @Test
    fun testAccumulatorWithFlag() {
        flagRegister.byte = (0b11010000u and 0xFFu).toUByte()
        cpuRegister.a = 0x2Fu
        assert(flagRegister.byte == (0xD0u).and(0xFFu).toUByte())
        assert(cpuRegister.af == (0x2FD0u).and(0xFFFFu).toUShort())
        cpuRegister.af = 0x2FF0u
        assert(cpuRegister.a == (0x2Fu and 0xFFu).toUByte())
        assert(flagRegister.byte == (0xF0u and 0xFFu).toUByte())
        println(cpuRegister)
    }

}

