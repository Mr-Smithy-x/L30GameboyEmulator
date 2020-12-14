package tests

import com.vonley.extensions.toHexString
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

        assert(flagRegister.byte == (0b01100000 and 0xFF).toByte())
        flagRegister.zf = true
        assert(flagRegister.byte == (0b11100000 and 0xFF).toByte())
        flagRegister.byte = (0b10000000 and 0xFF).toByte()
        assert(flagRegister.zf)
        assert(flagRegister.n == false)
        assert(flagRegister.h == false)
        println(cpuRegister.fr)
    }

    @Test
    fun testAccumulatorWithFlag() {
        flagRegister.byte = (0b11010000 and 0xFF).toByte()
        cpuRegister.a = 0x2F
        assert(flagRegister.byte == (0xD0).and(0xFF).toByte())
        assert(cpuRegister.af == (0x2FD0).and(0xFFFF).toShort())
        cpuRegister.af = 0x2FF0
        assert(cpuRegister.a == (0x2F and 0xFF).toByte())
        assert(flagRegister.byte == (0xF0 and 0xFF).toByte())
        println(cpuRegister)
    }

}

