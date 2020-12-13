package tests

import com.vonley.processor.MMU
import junit.framework.TestCase

class ReadWriteTest : TestCase() {

    lateinit var mmu: MMU

    override fun setUp() {
        super.setUp()
        mmu = MMU()
    }

    fun testWriteThenRead() {
        val address = 0x2FDE
        val valueToWrite: Byte = 0x4F.toByte()
        mmu.write(address, valueToWrite)
        val readByte = mmu.read(address)
        assert(valueToWrite == readByte)
    }


}