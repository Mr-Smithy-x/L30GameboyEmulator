package tests

import org.junit.Test

/**
 * To understand calculations you need to understand Signed Magnitude
 * Left most bit tells you whether its a negative or positive. 0 for positive 1 for negative
 * 0011 = 3
 * 1011 = -3
 * Problem is that you cannot do math with signed bits thats why 1 & 2 complements exists
 * in order to do math in 1's complement you have to flip the bits so
 * 0011 = 3
 * 1100 = -3
 * which then becomes
 * 1111 = -0 - as you can see we have a -0, not a +0
 * Lets talk 2's complement, on the left both we say 0 represents positive and 1 represents negative but
 * we add a 1 to the negative binary value
 * 0011 = 3
 * 1101 = -3
 * 0000 = +0 - as you can see we have a +0 not a -0 which makes a different from 1's complement
 * With 2's complement you're able to represent binary in its proper format
 * also note that since theres no left over bits to to carry over the one, it makes that positive
 * 2's complement tells you to drop the left most bit value if its != 1 (ie. if its not a negative number)
 * With 2 complements also not that helps with store numbers withing a range
 * 4  bits = 0 to 15 while signed is -8 to 7
 * 8  bits = 0 through 255 while signed is -128 to 127
 * 16 bits = 0 through 65,535 while signed is -32,768 to 32,767
 * 32 bits = 0 through 4,294,967,295 while signed is -2147483648 - 2147483647
 * 64 bits = 0 through 18446744073709551615 while signed is -9,223,372,036,854,775,808 - 9,223,372,036,854,775,807
 * also know that int (Integer) has only a 16 bit range, don't ask why just know that its law, (ie. don't question gravity, the numbers are just law)
 * so long was created with having 32bit range in mind
 * long long = 64bit range
 *
 * on 32bit machines
 * int = 32bits
 * long = 32 bits aswell
 * long long = 64 bits
 *
 * on 64bit machines
 * int = 32bit
 * long = 32 or 64bits
 * long long = 64bits
 *
 * int8_t and uint8_t (also known as a byte cus 8 bits = a byte)
 * int16_t and uint16_t - 2 bytes or a word
 * int32_t and uint32_t - 4 bytes or a double word
 * int64_t and uint64_t - 8 bytes or a quad word
 * as im writing this document im having having a biggest epiphany, like when you find a piece to a puzzle and it fits together to create an image, thats my brain
 * Good now that you understand signed vs unsigned you should know what the int & uint stands for
 * double and floats are a different beast, would love to do write up on that. - Vonley
*/
class BitWiseTests {
    //sgn 64  32 16 8  4 2 1
    //0   1   0  1  0  1 0 0 = 4 + 16 + 64 = 84
    //0   1   0  1  1  0 0 1 = 1 + 8 + 16 + 64 = 89
    val a = 84
    val b = 89

    @Test
    fun testAnd(){
        //Where both have corresponding ones
        //sgn 64  32 16 8  4 2 1
        //0   1   0  1  0  1 0 0 = 4 + 16 + 64 = 84
        //0   1   0  1  1  0 0 1 = 1 + 8 + 16 + 64 = 89
        //-----------------------------------------------
        //0   1   0  1  0  0 0 0 = 16 + 64 = 80
        assert(a and b == 80)
    }

    @Test
    fun testOr(){
        //Where either or have corresponding ones
        //sgn 64  32 16 8  4 2 1
        //0   1   0  1  0  1 0 0 = 4 + 16 + 64 = 84
        //0   1   0  1  1  0 0 1 = 1 + 8 + 16 + 64 = 89
        //-----------------------------------------------
        //0   1   0  1  1  1 0 1 = 16 + 64 + 8 + 4 + 1 = 93
        assert(a or b == 93)
    }

    @Test
    fun testXor(){
        //Where either or have corresponding ones
        //sgn 64  32 16 8  4 2 1
        //0   1   0  1  0  1 0 0 = 4 + 16 + 64 = 84
        //0   1   0  1  1  0 0 1 = 1 + 8 + 16 + 64 = 89
        //-----------------------------------------------
        //0   0   0  0  1  1 0 1 = 13
        assert(a xor b == 13)
    }

    @Test //We are working with 2 complements
    fun testNot2ndComplement(){
        //flips the operator
        // sgn  128 64  32 16 8 4 2 1
        // 0    0   1   0  1  0 1 0 0 = 4 + 16 + 64 = 84
        // 0    1   0   1  0  1 0 1 1 = 1 + 2 + 8 + 32 + 128 = 171 or unsigned int representation // 1st complement
        // 1    0   1   0  1  0 1 0 0 = 4 + 8 + 16 + 64 = -85 signed int representation // 2s complement


        // 0    0   1   0  1  1 0 0 1 = 1 + 8 + 16 + 64 = 89
        // 1    0   1   0  1  1 0 1 1 = 1 + 2 + 8 + 16 + 64 = -90 //2nd complement signed
        // 0    1   0   1  0  0 1 1 0 = 2 + 4 + 8 + 32 + 128 = 166  //1st complement unsigned
        // in other words if you think of 0-255, 255 - 89 = 166, 89 is the inverse value but -90 is inverse representation
        //64 is max range so 128 isnt really counted
        assert(a.inv() == -85)
        assert(b.inv() == -90)
    }

    @Test
    fun testLeftShift(){
        //shift 2 spaces to the left
        // sgn  256   128 64  32 16 8 4 2 1
        // 0    0     0   1   0  1  0 1 0 0 = 4 + 16 + 64 = 84 << 2
        // 0    1     0   1   0  1  0 0 0 0 = 16 + 64 + 256 = 336


        // 0    0    0    1   0  1  1 0 0 1 = 1 + 8  + 16 + 64 = 89
        // 0    1    0    1   1  0  0 1 0 0 = 4 + 32 + 64 + 128 = 356
        assert(a shl 2 == 336 )
        assert(b shl 2 == 356)
    }

    @Test
    fun testRightShift(){
        //shift 2 spaces to the right
        // sgn  128 64  32 16 8 4 2 1
        // 0    0   1   0  1  0 1 0 0 = 4 + 16 + 64 = 84
        // 0    0   0   0  1  0 1 0 1 = 16 + 4 + 1 = 21


        // 0    0   1   0  1  1 0 0 1 = 1 + 8 + 16 + 64 = 89
        // 0    0   0   0  1  0 1 1 0 = 16 + 4 + 2 = 22
        assert(a shr 2 == 21)
        assert(b shr 2 == 22)
    }


    @Test
    fun testCombine(){
        val instruction = (0xCB00u.toUShort() or (0x13u).toUShort().and(0x00FFu))
        assert(instruction == 0xCB13u.toUShort())
    }

}