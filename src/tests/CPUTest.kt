package tests

import com.vonley.registers.CPU
import org.junit.Test
/**
 * 0 - 8 signed range is -128 - 127
 * 0 - 8 unsigned range is 0-256
 * Reference Bitwise Test class for understanding
 * @see BitWiseTests
 */
class CPUTest {

    @Test
    fun testBC() {
        CPU.b = 0xA8
        CPU.c = 0x10
        assert(CPU.bc == (CPU.b shl 8) or CPU.c)
    }

    @Test
    fun testDE() {
        CPU.d = 0x3F
        CPU.e = 0x7F
        assert(CPU.de == (CPU.d shl 8) or CPU.e)
    }

    @Test
    fun testHL() {
        CPU.h = 0x3F
        CPU.l = 0x7F
        assert(CPU.hl == (CPU.h shl 8) or CPU.l)
    }

}