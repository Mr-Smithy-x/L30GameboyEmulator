package tests

import com.vonley.extensions.toHexString
import com.vonley.processor.registers.CPURegister
import junit.framework.TestCase
import org.junit.Test
import kotlin.experimental.and

/**
 * 0 - 8 signed range is -128 - 127
 * 0 - 8 unsigned range is 0-256
 * 0xFF = maxrange
 * Reference Bitwise Test class for understanding
 * @see BitWiseTests
 */
class CPUTest : TestCase() {

    lateinit var cpuRegister: CPURegister

    override fun setUp() {
        super.setUp()
        cpuRegister = CPURegister()
    }

    @Test
    fun testBC() {
        cpuRegister.b = 0xA8.toByte()
        cpuRegister.c = 0x10
        assert(cpuRegister.bc == ((((cpuRegister.b.toInt() and 0xFF shl 8)) or (cpuRegister.c.toInt() and 0xFF)) and 0xFFFF).toShort())
        println(cpuRegister)
    }

    @Test
    fun testDE() {
        cpuRegister.d = 0x3F
        cpuRegister.e = 0x7F
        assert(cpuRegister.de == ((((cpuRegister.d.toInt() and 0xFF shl 8)) or (cpuRegister.e.toInt() and 0xFF)) and 0xFFFF).toShort())
        println(cpuRegister)
    }

    @Test
    fun testHL() {
        cpuRegister.h = 0x3F
        cpuRegister.l = 0x7F
        assert(cpuRegister.hl == ((((cpuRegister.h.toInt() and 0xFF shl 8)) or (cpuRegister.l.toInt() and 0xFF)) and 0xFFFF).toShort())
        println(cpuRegister)
    }

    @Test
    fun testSetBC() {
        val b: Byte = 0x2F
        val c: Byte = 0x18

        cpuRegister.b = b
        cpuRegister.c = c

        assert(cpuRegister.bc == ((((cpuRegister.b.toInt() and 0xFF shl 8)) or (cpuRegister.c.toInt() and 0xFF)) and 0xFFFF).toShort())
        cpuRegister.b = 0
        cpuRegister.c = 0
        cpuRegister.bc = 0x2F18
        assert(cpuRegister.b == b)
        assert(cpuRegister.c == c)
        println(cpuRegister)
    }

    @Test
    fun testSetDE() {
        val d: Byte = 0x34
        val e: Byte = 0x9F.toByte()
        cpuRegister.d = d
        cpuRegister.e = e

        assert(cpuRegister.de == ((((cpuRegister.d.toInt() and 0xFF shl 8)) or (cpuRegister.e.toInt() and 0xFF)) and 0xFFFF).toShort())
        cpuRegister.d = 0
        cpuRegister.e = 0
        cpuRegister.de = 0x349F
        assert(cpuRegister.d == d)
        assert(cpuRegister.e == e)
        println(cpuRegister)
    }

    @Test
    fun testSetHL() {
        val h: Byte = 0x27
        val l: Byte = 0x4F

        cpuRegister.h = h
        cpuRegister.l = l
        assert(cpuRegister.hl == ((((cpuRegister.h.toInt() and 0xFF shl 8)) or (cpuRegister.l.toInt() and 0xFF)) and 0xFFFF).toShort())
        cpuRegister.h = 0
        cpuRegister.l = 0
        cpuRegister.hl = 0x274F
        assert(cpuRegister.h == h)
        assert(cpuRegister.l == l)
        println(cpuRegister)
    }

}