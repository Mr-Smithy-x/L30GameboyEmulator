package com.vonley.processor.memory

import com.vonley.contracts.IntDifferenceImpl
import com.vonley.extensions.getRegion
import kotlin.experimental.and


//MBC1
//XXXX XXXS = X doesnt care, S is the select mode for MBC1
//16Mbit Rom / 8kbyte RAM or 4MBit Rom/32KByte Ram
//if S = 0 16/8 is selected. If S = 1 4/32 is selected

//if 16/8 mode
//writing XXXXXXBB = X doesnt care B = Bank Select bits in 4000-5FFF area

//MBC2
class MMU {
    //Start	End	    Description	Notes
    //0000  00FF - The bootrom
    private val bootrom = UByteArray(0x100)

    //0000	3FFF	16KB ROM bank 00	From cartridge, usually a fixed bank
    private val rombank0 = UByteArray(0x4000)

    //4000	7FFF	16KB ROM Bank 01~NN	From cartridge, switchable bank via MB (if any)
    private val rombank1 = UByteArray(0x4000)

    //8000	9FFF	8KB Video RAM (VRAM)	Only bank 0 in Non-CGB mode Switchable bank 0/1 in CGB mode
    private val vram = UByteArray(0x2000)

    //A000	BFFF	8KB External RAM	In cartridge, switchable bank if any
    private val eram = UByteArray(0x2000)

    //C000	CFFF	4KB Work RAM (WRAM) bank 0
    //D000	DFFF	4KB Work RAM (WRAM) bank 1~N	Only bank 1 in Non-CGB mode Switchable bank 1~7 in CGB mode
    private val wrambank = UByteArray(0x2000)

    //E000	FDFF	Mirror of C000~DDFF (ECHO RAM)	Nintendo says use of this area is prohibited.
    private val echoram = UByteArray(0x1E00)//or 1DFF?

    //FE00	FE9F	Sprite attribute table (OAM)
    private val oam = UByteArray(0x60)

    //FF00	FF7F	I/O Registers
    private val ioregs = UByteArray(0x80)

    //FEA0	FEFF	Not Usable	Nintendo says use of this area is prohibited
    private val unusable = UByteArray(0x60)

    //FF80	FFFE	High RAM (HRAM)
    private val hram = UByteArray(0x7F)

    //FFFF	FFFF	Interrupts Enable Register (IE)
    private var interupt = 0


    enum class Region(
        val canRead: Boolean = false,
        val canWrite: Boolean = false,
        protected val range: IntRange = -1..-1
    ) : IntDifferenceImpl {
        BOOT_ROM(true, true, 0x0000..0x00FF) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        ROM_BANK_0(true, false, 0x0000..0x3FFF) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        ROM_BANK_1(true, false, 0x4000..0x7FFF) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        VIDEO_RAM(true, true, 0x8000..0x9FFF) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        EXTERNAL_RAM(true, true, 0xA000..0xBFFF) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        WORK_RAM(true, true, 0xC000..0xDFFF) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        ECHO_RAM(true, false, 0xE000..0xFDFF) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        OAM_SPRITE(true, true, 0xFE00..0xFE9F) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        UNUSABLE(true, false, 0xFEA0..0xFEFF) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        },
        IO_REG(true, true, 0xFF00..0xFF7F) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        }, //Joy Pad
        ZERO_PAGE_RAM(true, true, 0xFF80..0xFFFE) {
            override val endInclusive: Int
                get() = range.last
            override val start: Int
                get() = range.first
        }; //H Ram

        companion object {
            fun parse(value: Int, bootRomEnabled: Boolean = false): Region {
                return when {
                    bootRomEnabled && value in BOOT_ROM -> BOOT_ROM
                    value in ROM_BANK_0 -> ROM_BANK_0
                    value in ROM_BANK_1 -> ROM_BANK_1
                    value in VIDEO_RAM -> VIDEO_RAM
                    value in EXTERNAL_RAM -> EXTERNAL_RAM
                    value in WORK_RAM -> WORK_RAM
                    value in ECHO_RAM -> ECHO_RAM
                    value in OAM_SPRITE -> OAM_SPRITE
                    value in UNUSABLE -> UNUSABLE
                    value in IO_REG -> IO_REG
                    value in ZERO_PAGE_RAM -> ZERO_PAGE_RAM
                    else -> {
                        throw ArrayIndexOutOfBoundsException("You're out of bounds")
                    }
                }
            }
        }
    }


    private fun getByteArrayAtRegion(address: Int): UByteArray {
        return when (address.getRegion()) {
            Region.BOOT_ROM -> bootrom
            Region.ROM_BANK_0 -> rombank0
            Region.ROM_BANK_1 -> rombank1
            Region.VIDEO_RAM -> vram
            Region.EXTERNAL_RAM -> eram
            Region.WORK_RAM -> wrambank
            Region.ECHO_RAM -> echoram
            Region.OAM_SPRITE -> oam
            Region.UNUSABLE -> unusable
            Region.IO_REG -> ioregs
            Region.ZERO_PAGE_RAM -> hram
        }
    }

    fun readByte(address: Int): UByte {
        val region = address.getRegion()
        val regionArray = getByteArrayAtRegion(address)
        val index = address and region.difference
        val byte = regionArray[index]
        //println("Reading Addr: ${address.toHexString()} = Val: ${byte.toHexString()} at Idx: $index of Reg: $region with size: ${regionArray.size}")
        return byte.and(0xFFu)
    }

    fun writeShort(address: Int, value: UShort) {
        val left = ((value.toUInt() shr 8) and 0xFFu).toUByte()
        val right = ((value.toUInt() and 0xFFu)).toUByte()
        writeByte(address, left)
        writeByte(address + 1, right)
    }

    fun readShort(address: Int): UShort {
        val lo = readByte(address)
        val hi = readByte(address + 1)
        return (((lo and 0xFFu).toUInt() shl 8) or (hi and 0xFFu).toUInt()).and(0xFFFFu).toUShort();//We are taking the byte, upscaling it to a short
        //short is a signed word -32768 to +32767, Word is 0 - 65535, we are just representing the binary in 2's complement
        //using a short
    }


    fun writeByte(address: Int, value: UByte) {
        val region = address.getRegion()
        if (region.canWrite) {
            val regionArray = getByteArrayAtRegion(address)
            val index = address and region.difference
            regionArray[index] = value.and(0xFFu)
            //println("Writing Val: ${value.toHexString()} to Addr: ${address.toHexString()} at Idx: $index of $region with size: ${regionArray.size}")
        } else {
            throw IllegalAccessException("You cannot write to this area")
        }
    }

    companion object {


    }
}



