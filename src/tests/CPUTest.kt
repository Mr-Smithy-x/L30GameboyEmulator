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
        cpuRegister.b = 0xA8u
        cpuRegister.c = 0x10u
        assert(cpuRegister.bc == ((((cpuRegister.b.toUInt() and 0xFFu shl 8)) or (cpuRegister.c.toUInt() and 0xFFu)) and 0xFFFFu).toUShort())
        println(cpuRegister)
    }

    @Test
    fun testDE() {
        cpuRegister.d = 0x3Fu
        cpuRegister.e = 0x7Fu
        assert(cpuRegister.de == ((((cpuRegister.d.toUInt() and 0xFFu shl 8)) or (cpuRegister.e.toUInt() and 0xFFu)) and 0xFFFFu).toUShort())
        println(cpuRegister)
    }

    @Test
    fun testHL() {
        cpuRegister.h = 0x3Fu
        cpuRegister.l = 0x7Fu
        assert(cpuRegister.hl == ((((cpuRegister.h.toUInt() and 0xFFu shl 8)) or (cpuRegister.l.toUInt() and 0xFFu)) and 0xFFFFu).toUShort())
        println(cpuRegister)
    }

    @Test
    fun testSetBC() {
        val b: UByte = 0x2Fu
        val c: UByte = 0x18u

        cpuRegister.b = b
        cpuRegister.c = c

        assert(cpuRegister.bc == ((((cpuRegister.b.toUInt() and 0xFFu shl 8)) or (cpuRegister.c.toUInt() and 0xFFu)) and 0xFFFFu).toUShort())
        cpuRegister.b = 0u
        cpuRegister.c = 0u
        cpuRegister.bc = 0x2F18u
        assert(cpuRegister.b == b)
        assert(cpuRegister.c == c)
        println(cpuRegister)
    }

    @Test
    fun testSetDE() {
        val d: UByte = 0x34u
        val e: UByte = 0x9Fu
        cpuRegister.d = d
        cpuRegister.e = e

        assert(cpuRegister.de == ((((cpuRegister.d.toUInt() and 0xFFu shl 8)) or (cpuRegister.e.toUInt() and 0xFFu)) and 0xFFFFu).toUShort())
        cpuRegister.d = 0x0u
        cpuRegister.e = 0x0u
        cpuRegister.de = 0x349Fu
        assert(cpuRegister.d == d)
        assert(cpuRegister.e == e)
        println(cpuRegister)
    }

    @Test
    fun testSetHL() {
        val h: UByte = 0x27u
        val l: UByte = 0x4Fu

        cpuRegister.h = h
        cpuRegister.l = l
        assert(cpuRegister.hl == ((((cpuRegister.h.toUInt() and 0xFFu shl 8)) or (cpuRegister.l.toUInt() and 0xFFu)) and 0xFFFFu).toUShort())
        cpuRegister.h = 0x0u
        cpuRegister.l = 0x0u
        cpuRegister.hl = 0x274Fu
        assert(cpuRegister.h == h)
        assert(cpuRegister.l == l)
        println(cpuRegister)
    }

}