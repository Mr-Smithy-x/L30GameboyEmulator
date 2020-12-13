package tests

import com.vonley.extensions.toHexString
import com.vonley.processor.MMU
import junit.framework.TestCase

class ReadWriteTest : TestCase() {

    lateinit var mmu: MMU

    override fun setUp() {
        super.setUp()
        mmu = MMU()
    }

    fun testWriteShort() {
        val address = 0x2FDE
        val valueToWrite: Short = 0x4FA0
        mmu.writeShort(address, valueToWrite)
        val readShort = mmu.readShort(address)
        val readHiByte = mmu.read(address)
        val readLoByte = mmu.read(address + 1)
        assert(readHiByte == (valueToWrite.toInt() shr 8 and 0xFF).toByte())
        assert(readLoByte == (valueToWrite.toInt() and 0xFF).toByte())
        assert(readShort == valueToWrite)
    }

    fun testReadShort() {
        val address = 0x2FDE
        val hiValueToWrite: Byte = 0x4F
        val loValueToWrite: Byte = (0xFF).toByte()
        mmu.write(address, hiValueToWrite)
        mmu.write(address + 1, loValueToWrite)
        val readShort: Short = mmu.readShort(address)
        assert(readShort == (0x4FFF).toShort())
    }

    fun testWriteThenRead() {
        val address = 0x2FDE
        val valueToWrite: Byte = 0x4F.toByte()
        mmu.write(address, valueToWrite)
        val readByte = mmu.read(address)
        assert(valueToWrite == readByte)
    }


}