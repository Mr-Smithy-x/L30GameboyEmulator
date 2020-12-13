package com.vonley.processor


//MBC1
//XXXX XXXS = X doesnt care, S is the select mode for MBC1
//16Mbit Rom / 8kbyte RAM or 4MBit Rom/32KByte Ram
//if S = 0 16/8 is selected. If S = 1 4/32 is selected

//if 16/8 mode
//writing XXXXXXBB = X doesnt care B = Bank Select bits in 4000-5FFF area


//MBC2



class MemoryManagementUnit {

    //Entry Point
    //0100-0103

    //0104-0133
    val nintendoLogo = intArrayOf(0xCE, 0xED, 0x66, 0x66, 0xCC, 0x0D, 0x00, 0x0B, 0x03, 0x73, 0x00, 0x83, 0x00, 0x0C, 0x00, 0x0D,
        0x00, 0x08, 0x11, 0x1F, 0x88, 0x89, 0x00, 0x0E, 0xDC, 0xCC, 0x6E, 0xE6, 0xDD, 0xDD, 0xD9, 0x99,
        0xBB, 0xBB, 0x67, 0x63, 0x6E, 0x0E, 0xEC, 0xCC, 0xDD, 0xDC, 0x99, 0x9F, 0xBB, 0xB9, 0x33, 0x3E)

    //0134-0143

    //0000	3FFF	16KB ROM bank 00	From cartridge, usually a fixed bank
    var rombank0 = ByteArray(0x4000)

    //4000	7FFF	16KB ROM Bank 01~NN	From cartridge, switchable bank via MB (if any)
    var rombank1 = ByteArray(0x4000)

    //8000	9FFF	8KB Video RAM (VRAM)	Only bank 0 in Non-CGB mode Switchable bank 0/1 in CGB mode
    var vram = ByteArray(0x2000)

    //A000	BFFF	8KB External RAM	In cartridge, switchable bank if any
    private var eram = ByteArray(0x2000)

    //C000	CFFF	4KB Work RAM (WRAM) bank 0
    var wrambank0 = ByteArray(0x1000)

    //D000	DFFF	4KB Work RAM (WRAM) bank 1~N	Only bank 1 in Non-CGB mode Switchable bank 1~7 in CGB mode
    var wrambank1 = ByteArray(0x1000)

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


    /*
Start	End	Description	Notes
FEA0	FEFF	Not Usable	Nintendo says use of this area is prohibited
 */
}



