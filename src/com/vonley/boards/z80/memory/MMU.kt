package com.vonley.boards.z80.memory

import com.vonley.contracts.UShortDifferenceImpl
import com.vonley.extensions.getRegion
import com.vonley.extensions.shl
import com.vonley.extensions.shr
import com.vonley.extensions.toHexString


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
        protected val range: UIntRange = 0xF0000u..0xFF000u
    ) : UShortDifferenceImpl {
        BOOT_ROM(true, true, 0x0000u..0x00FFu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        ROM_BANK_0(true, false, 0x0000u..0x3FFFu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        ROM_BANK_1(true, false, 0x4000u..0x7FFFu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        VIDEO_RAM(true, true, 0x8000u..0x9FFFu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        EXTERNAL_RAM(true, true, 0xA000u..0xBFFFu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        WORK_RAM(true, true, 0xC000u..0xDFFFu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        ECHO_RAM(true, false, 0xE000u..0xFDFFu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        OAM_SPRITE(true, true, 0xFE00u..0xFE9Fu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        UNUSABLE(true, false, 0xFEA0u..0xFEFFu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        },
        IO_REG(true, true, 0xFF00u..0xFF7Fu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        }, //Joy Pad
        ZERO_PAGE_RAM(true, true, 0xFF80u..0xFFFEu) {
            override val endInclusive: UShort
                get() = range.last.and(0xFFFFu).toUShort()
            override val start: UShort
                get() = range.first.and(0xFFFFu).toUShort()
        }; //H Ram

        companion object {
            fun parse(value: UShort, bootRomEnabled: Boolean = false): Region {
                val word: UShort = value
                return when {
                    bootRomEnabled && word in BOOT_ROM -> BOOT_ROM
                    word in ROM_BANK_0 -> ROM_BANK_0
                    word in ROM_BANK_1 -> ROM_BANK_1
                    word in VIDEO_RAM -> VIDEO_RAM
                    word in EXTERNAL_RAM -> EXTERNAL_RAM
                    word in WORK_RAM -> WORK_RAM
                    word in ECHO_RAM -> ECHO_RAM
                    word in OAM_SPRITE -> OAM_SPRITE
                    word in UNUSABLE -> UNUSABLE
                    word in IO_REG -> IO_REG
                    word in ZERO_PAGE_RAM -> ZERO_PAGE_RAM
                    else -> {
                        throw ArrayIndexOutOfBoundsException("You're out of bounds")
                    }
                }
            }
        }
    }


    /**
     * Returns the region to return at where we're righting to the MMU
     */
    private fun getByteArrayAtRegion(address: UShort): UByteArray {
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

    fun readByte(address: UShort): UByte {
        val region = address.getRegion()
        val regionArray = getByteArrayAtRegion(address)
        val index = address and region.difference
        val byte = regionArray[index.toInt()]
        return byte.and(0xFFu)
    }

    fun writeShort(address: UShort, value: UShort) {
        val left = ((value shr 8) and 0xFFu).toUByte()
        val right = (value and 0xFFu).toUByte()
        writeByte(address, left)
        writeByte(address.inc(), right)
    }

    fun readShort(address: UShort): UShort {
        val lo = readByte(address)
        val hi = readByte(address.inc())

        return (((lo and 0xFFu).toUShort() shl 8) or (hi and 0xFFu).toUShort()).and(0xFFFFu);
    }


    fun writeByte(address: UShort, value: UByte) {
        val region = address.getRegion()
        if (region.canWrite) {
            val regionArray = getByteArrayAtRegion(address)
            val index = address and region.difference
            regionArray[index.toInt()] = value.and(0xFFu)
        } else {
            throw IllegalAccessException("You cannot write to this area")
        }
    }

    fun reset() {

    }

    companion object {

    }
}



