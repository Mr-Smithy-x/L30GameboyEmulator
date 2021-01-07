package com.vonley.boards.z80.components

import java.io.File

class Rom constructor(val data: UByteArray) {

    enum class CGB(val byte: UByte) {
        NONE(0x0u),
        SUPPORT(0x80u),
        ONLY(0xC0u);

        companion object {
            fun parse(value: UByte): CGB = values().find { c -> c.byte == value } ?: NONE
        }

    }

    enum class SGB(val byte: UByte) {
        NONE(0x0u),
        ONLY(0x03u);

        companion object {
            fun parse(value: UByte): SGB = values().find { c -> c.byte == value } ?: NONE
        }

    }

    enum class CARTRIDGE(val byte: UByte) {
        ROM_ONLY(0x00u),
        MBC1(0x01u),
        MBC1_RAM(0x02u),
        MBC1_RAM_BATTERY(0x03u),
        MBC2(0x05u),
        MBC2_BATTERY(0x06u),
        ROM_RAM(0x08u),
        ROM_RAM_BATTERY(0x09u),
        MMM01(0x0Bu),
        MMM01_RAM(0x0Cu),
        MMM01_RAM_BATTERY(0x0Du),
        MBC3_TIMER_BATTERY(0x0Fu),
        MBC3_TIMER_RAM_BATTERY(0x10u),
        MBC3(0x11u),
        MBC3_RAM(0x12u),
        MBC3_RAM_BATTERY(0x13u),
        MBC5(0x19u),
        MBC5_RAM(0x1Au),
        MBC5_RAM_BATTERY(0x1Bu),
        MBC5_RUMBLE(0x1Cu),
        MBC5_RUMBLE_RAM(0x1Du),
        MBC5_RUMBLE_RAM_BATTERY(0x1Eu),
        MBC6(0x20u),
        MBC7_SENSOR_RUMBLE_RAM_BATTERY(0x22u),
        POCKET_CAMERA(0xFCu),
        BANDAI_TAMA5(0xFDu),
        HUC3(0xFEu),
        HUC1_RAM_BATTERY(0xFFu);

        companion object {
            fun parse(value: UByte): CARTRIDGE? {
                return values().find { c -> c.byte == value }
            }
        }

    }

    enum class PUBLISHER(val publisher: String, val byte: UByte) {
        NONE("NONE", 0x00u),
        NINTENDO_RND1("Nintendo Research & Development", 0x01u),
        CAPCOM("Capcom", 0x08u),
        EA("Electronic Arts", 0x13u),
        HUDSON_SOFT("Hudson Soft", 0x18u),
        B_AI("B-AI", 0x19u),
        KSS("kss", 0x20u),
        POW("pow", 0x22u),
        PCM("PCM Complete", 0x24u),
        SANX("San-X", 0x25u),
        KEMCO_JAPAN("Kemco Japan", 0x28u),
        SETA("Seta", 0x29u),
        VIACOM("Viacom", 0x31u),
        BANDAI("Bandai", 0x32u),
        OCEAN("Ocean/Acclaim", 0x33u),
        KONAMI("Konami", 0x34u),
        HECTOR("Hector", 0x35u),
        TAITO("Taito", 0x37u),
        HUDSON("Hudson", 0x38u),
        BANPRESTO("Banpresto", 0x39u),
        UBISOFT("Ubi Soft", 0x41u),
        ATLUS("Atlus", 0x42u),
        MALIBU("Malibu", 0x44u),
        ANGEL("Angel", 0x46u),
        BULLET_PROOF("Bullet-Proof", 0x47u),
        IREM("Irem", 0x49u),
        ABSOLUTE("Absolute", 0x50u),
        ACCLAIM("Acclaim", 0x51u),
        Activision("Activision", 0x52u),
        AMERICANSAMMY("American Sammy", 0x53u),
        KONAMI2("American Sammy", 0x54u),
        HITECHENTERTAINMENT("Hi Tech Entertainment", 0x55u),
        LJN("LJN", 0x56u),
        MATCHBOX("Matchbox", 0x57u),
        MATTEL("Mattel", 0x58u),
        MILTON_BRADLEY("Milton Bradley", 0x59u),
        TITUS("Titus", 0x60u),
        VIRGIN("Virgin", 0x61u),
        LUCASARTS("Lucas Arts", 0x64u),
        OCEAN2("Ocean", 0x67u),
        EA2("Electronic Arts", 0x69u),
        INFOGRAMES("Infogrames", 0x70u),
        INTERPLAY("Interplay", 0x71u),
        BRODERBUND("Broderbund", 0x72u),
        SCULPTURED("Sculptured", 0x73u),
        SCI("Sci", 0x75u),
        THQ("THQ", 0x78u),
        ACCOLADE("Accolade", 0x79u),
        MISAWA("Misawa", 0x80u),
        LOZC("Lozc", 0x83u),
        TOKUMASHOTEN("Tokuma Shoten Intermedia", 0x86u),
        TSUKUDA("Tsukuda Original", 0x87u),
        CHUNSOFT("Chunsoft", 0x91u),
        VIDEOSYSTEM("Video System", 0x92u),
        OCEAN3("Ocean/Acclaim", 0x93u),
        VARIE("Varie", 0x95u),
        YONEZAWA("Yonezawa's Pal", 0x96u),
        KANEKO("Kaneko", 0x97u),
        PACKINSOFT("Pack in soft", 0x99u),
        KONAMIYOGIOH("Konami (Yu-Gi-Oh!)", 0xA4u);

        companion object {

            fun parse(byte: UInt): PUBLISHER = parse((byte and 0xFFu).toUByte())

            fun parse(byte: UByte): PUBLISHER = values().find { p -> p.byte == byte } ?: NONE

            fun parse(byte: UByteArray): PUBLISHER {
                return NONE
            }
        }

        override fun toString(): String {
            return this.publisher
        }

    }

    val title: String = data.copyOfRange(0x134, 0x143).toByteArray().decodeToString()
    val validHeader: Boolean = (0x134..0x14c).sumOf { i -> -(data[i].toInt() + 1) } and 0xFF == data[0x14D].toInt() and 0xFF
    val cgbMode: CGB = CGB.parse(data[0x143])
    val sgbMode: SGB = SGB.parse(data[0x146])
    val cartridge: CARTRIDGE? = CARTRIDGE.parse(data[0x147])
    val publisher: PUBLISHER =
        if (cgbMode == CGB.NONE) PUBLISHER.parse(data[0x14B]) else PUBLISHER.parse(data.copyOfRange(0x144, 0x145))

    companion object {


        //0104-0133 - Nintendo Logo
        val nintendoLogo = intArrayOf(
            0xCE, 0xED, 0x66, 0x66, 0xCC, 0x0D, 0x00, 0x0B, 0x03, 0x73, 0x00, 0x83, 0x00, 0x0C, 0x00, 0x0D,
            0x00, 0x08, 0x11, 0x1F, 0x88, 0x89, 0x00, 0x0E, 0xDC, 0xCC, 0x6E, 0xE6, 0xDD, 0xDD, 0xD9, 0x99,
            0xBB, 0xBB, 0x67, 0x63, 0x6E, 0x0E, 0xEC, 0xCC, 0xDD, 0xDC, 0x99, 0x9F, 0xBB, 0xB9, 0x33, 0x3E
        )

        //0134-0143 = Title


        //Entry Point
        //0100-0103
        fun load(path: String): Rom {
            return load(File(path))
        }

        fun load(file: File): Rom {
            return Rom(file.readBytes().asUByteArray())
        }
    }
}
