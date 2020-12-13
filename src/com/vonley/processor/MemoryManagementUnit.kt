package com.vonley.processor


/*
Start	End	Description	Notes
0000	3FFF	16KB ROM bank 00	From cartridge, usually a fixed bank
4000	7FFF	16KB ROM Bank 01~NN	From cartridge, switchable bank via MB (if any)
8000	9FFF	8KB Video RAM (VRAM)	Only bank 0 in Non-CGB mode Switchable bank 0/1 in CGB mode
A000	BFFF	8KB External RAM	In cartridge, switchable bank if any
C000	CFFF	4KB Work RAM (WRAM) bank 0
D000	DFFF	4KB Work RAM (WRAM) bank 1~N	Only bank 1 in Non-CGB mode Switchable bank 1~7 in CGB mode
E000	FDFF	Mirror of C000~DDFF (ECHO RAM)	Nintendo says use of this area is prohibited.
FE00	FE9F	Sprite attribute table (OAM)
FEA0	FEFF	Not Usable	Nintendo says use of this area is prohibited
FF00	FF7F	I/O Registers
FF80	FFFE	High RAM (HRAM)
FFFF	FFFF	Interrupts Enable Register (IE)
 */
//XXXX XXXS = X doesnt care, S is the select mode for MBC1
//16Mbit Rom / 8kbyte RAM or 4MBit Rom/32KByte Ram
//if S = 0 16/8 is selected. If S = 1 4/32 is selected

object MemoryMap {

    //Entry Point
    //0100-0103

    //0104-0133
    val nintendoLogo = intArrayOf(0xCE, 0xED, 0x66, 0x66, 0xCC, 0x0D, 0x00, 0x0B, 0x03, 0x73, 0x00, 0x83, 0x00, 0x0C, 0x00, 0x0D,
        0x00, 0x08, 0x11, 0x1F, 0x88, 0x89, 0x00, 0x0E, 0xDC, 0xCC, 0x6E, 0xE6, 0xDD, 0xDD, 0xD9, 0x99,
        0xBB, 0xBB, 0x67, 0x63, 0x6E, 0x0E, 0xEC, 0xCC, 0xDD, 0xDC, 0x99, 0x9F, 0xBB, 0xB9, 0x33, 0x3E)

    //0134-0143
}



