package tests

import com.vonley.registers.CPU
import org.junit.Test
/**
 * 0 - 8 signed range is -128 - 127
 * 0 - 8 unsigned range is 0-256
 * 0xFF = maxrange
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

    @Test
    fun testSetBC(){
        val b = 0x2F
        val c = 0x18

        CPU.b = b
        CPU.c = c

        assert(CPU.bc == (CPU.b shl 8) or CPU.c)
        CPU.b = 0
        CPU.c = 0
        CPU.bc = 0x2F18
        assert(CPU.b == b)
        assert(CPU.c == c)
    }

    @Test
    fun testSetDE(){
        val d = 0x34
        val e = 0x9F
        CPU.d = d
        CPU.e = e

        assert(CPU.de == (CPU.d shl 8) or CPU.e)
        CPU.d = 0
        CPU.e = 0
        CPU.de = 0x349F
        assert(CPU.d == d)
        assert(CPU.e == e)
    }

    @Test
    fun testSetHL(){
        val h = 0x27
        val l = 0x4F

        CPU.h = h
        CPU.l = l
        assert(CPU.hl == (CPU.h shl 8) or CPU.l)
        CPU.h = 0
        CPU.l = 0
        CPU.hl = 0x274F
        assert(CPU.h == h)
        assert(CPU.l == l)
    }

}