package tests

import com.vonley.extensions.fromBCD
import com.vonley.extensions.toBCDValue
import com.vonley.extensions.toHexString
import junit.framework.TestCase

class BCDTests : TestCase() {

    fun testBCDMath() {
        //BINARY:        1427
        //BCD:           5    9    3
        val bcd: Int = 0b0101_1001_0011
        val three: Int = bcd and 0xF
        val nine: Int = (bcd shr 4) and 0xF
        val five: Int = (bcd shr 8) and 0xF
        val result: Int = three + nine * 10 + five * 100
        assert(result == 593)
    }

    fun testToBCDConversion() {
        //BINARY:        1427
        //HEX REPRESENTATION: 0x593
        //BCD:           5    9    3
        val bcd: Int = 0b0101_1001_0011
        assert(bcd.toBCDValue() == 593) //int representation
        assert(bcd.toHexString() == "0x593")
    }

    fun testFromBCDConversion() {
        //BINARY:        1427
        //BCD:           5    9    3
        val bcd: Int = 0b0101_1001_0011
        assert(bcd == 1427)
        assert(593.fromBCD() == 1427)
    }

}
