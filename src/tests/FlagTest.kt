package tests

import com.vonley.extensions.toHexString
import com.vonley.registers.CPU
import com.vonley.registers.Flag
import org.junit.Test
/**
 * 0 - 8 signed range is -128 - 127
 * 0 - 8 unsigned range is 0-256
 * 0xFF = maxrange
 * Reference Bitwise Test class for understanding
 * @see BitWiseTests
 */
class FlagTest {

    @Test
    fun testFlagByte() {
        Flag.nz = false
        Flag.n = true
        Flag.h = true

        assert(Flag.flagByte == 0b01100000)
        Flag.nz = true
        assert(Flag.flagByte == 0b11100000)
        Flag.flagByte = 0b10000000
        assert(Flag.nz)
        assert(Flag.n == false)
        assert(Flag.h == false)
    }

    @Test
    fun testAccumulatorWithFlag(){
        Flag.flagByte = 0b11010000
        CPU.a = 0x2F
        assert(Flag.flagByte == 0xD0)
        assert(CPU.af == 0x2FD0)
        CPU.af = 0x2FF0
        assert(CPU.a == 0x2F)
        assert(Flag.flagByte == 0xf0)
    }

}

