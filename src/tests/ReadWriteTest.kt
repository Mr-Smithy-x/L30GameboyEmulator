package tests

import com.vonley.boards.z80.memory.MMU
import com.vonley.extensions.toHexString
import junit.framework.TestCase
import org.junit.jupiter.api.assertThrows

class ReadWriteTest : TestCase() {

    lateinit var mmu: MMU

    override fun setUp() {
        super.setUp()
        mmu = MMU()
    }

    fun testWriteThrowAccessError() {
        val address: UShort = 0x2F3Eu
        val byte: UByte = 0xA3u
        assertThrows<IllegalAccessException> {
            mmu.writeByte(address, byte)
        }
    }

    fun testWriteShort() {
        val address: UShort = 0x8FDEu
        val valueToWrite: UShort = 0x4FA0u
        mmu.writeShort(address, valueToWrite)
        val readShort = mmu.readShort(address)
        val readLoByte = mmu.readByte(address)
        val readHiByte = mmu.readByte(address.inc())
        //little endian order
        //0x4FA0 looks like - 0xA04F when written
        assert(readHiByte == (valueToWrite.toUInt() shr 8 and 0xFFu).toUByte())
        assert(readLoByte == (valueToWrite.toUInt() and 0xFFu).toUByte())
        println((readShort and 0xFFFFu).toHexString())
        assert(readShort == valueToWrite)

    }

    //read via little endian
    fun testReadShort() {
        val address: UShort = 0x8FDEu
        val hiValueToWrite: UByte = 0x4Fu
        val loValueToWrite: UByte = 0xFFu
        mmu.writeByte(address, loValueToWrite)
        mmu.writeByte(address.inc(), hiValueToWrite)
        val readShort: UShort = mmu.readShort(address)
        assert(readShort == (0x4FFFu).toUShort())
    }

    fun testWriteThenRead() {
        val address: UShort = 0x8FDEu
        val valueToWrite: UByte = 0x4Fu
        mmu.writeByte(address, valueToWrite)
        val readByte = mmu.readByte(address)
        assert(valueToWrite == readByte)
    }

}