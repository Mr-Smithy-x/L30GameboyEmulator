package com.vonley.processor


//MBC1
//XXXX XXXS = X doesnt care, S is the select mode for MBC1
//16Mbit Rom / 8kbyte RAM or 4MBit Rom/32KByte Ram
//if S = 0 16/8 is selected. If S = 1 4/32 is selected

//if 16/8 mode
//writing XXXXXXBB = X doesnt care B = Bank Select bits in 4000-5FFF area

//MBC2
class MMU {

    enum class Region(
        val read: Boolean = false,
        val write: Boolean = false,
        protected val range: IntRange = -1..-1
    ) : ClosedRange<Int> {
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
                    value in IO_REG -> IO_REG
                    value in ZERO_PAGE_RAM -> ZERO_PAGE_RAM
                    else -> {
                        throw ArrayIndexOutOfBoundsException("You're out of bounds")
                    }
                }
            }
        }
    }

    //Start	End	    Description	Notes
    //0000	3FFF	16KB ROM bank 00	From cartridge, usually a fixed bank
    var rombank0 = ByteArray(0x4000)

    //4000	7FFF	16KB ROM Bank 01~NN	From cartridge, switchable bank via MB (if any)
    var rombank1 = ByteArray(0x4000)

    //8000	9FFF	8KB Video RAM (VRAM)	Only bank 0 in Non-CGB mode Switchable bank 0/1 in CGB mode
    var vram = ByteArray(0x2000)

    //A000	BFFF	8KB External RAM	In cartridge, switchable bank if any
    private var eram = ByteArray(0x2000)

    //C000	CFFF	4KB Work RAM (WRAM) bank 0
    //D000	DFFF	4KB Work RAM (WRAM) bank 1~N	Only bank 1 in Non-CGB mode Switchable bank 1~7 in CGB mode
    var wrambank = ByteArray(0x2000)

    //E000	FDFF	Mirror of C000~DDFF (ECHO RAM)	Nintendo says use of this area is prohibited.
    var echoram = ByteArray(0x1E00)//or 1DFF?

    //FE00	FE9F	Sprite attribute table (OAM)
    var oam = ByteArray(0x60)

    //FF00	FF7F	I/O Registers
    var ioregs = ByteArray(0x80)

    //FEA0	FEFF	Not Usable	Nintendo says use of this area is prohibited
    private var prohibited = ByteArray(0x60)

    //FF80	FFFE	High RAM (HRAM)
    var hram = ByteArray(0x7F)

    //FFFF	FFFF	Interrupts Enable Register (IE)
    var interupt = 0


    //Entry Point
    //0100-0103

    //0104-0133
    val nintendoLogo = intArrayOf(
        0xCE, 0xED, 0x66, 0x66, 0xCC, 0x0D, 0x00, 0x0B, 0x03, 0x73, 0x00, 0x83, 0x00, 0x0C, 0x00, 0x0D,
        0x00, 0x08, 0x11, 0x1F, 0x88, 0x89, 0x00, 0x0E, 0xDC, 0xCC, 0x6E, 0xE6, 0xDD, 0xDD, 0xD9, 0x99,
        0xBB, 0xBB, 0x67, 0x63, 0x6E, 0x0E, 0xEC, 0xCC, 0xDD, 0xDC, 0x99, 0x9F, 0xBB, 0xB9, 0x33, 0x3E
    )

    //0134-0143
}



