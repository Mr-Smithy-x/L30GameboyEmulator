package com.vonley.boards.z80.components

import com.vonley.boards.z80.memory.MMU
import com.vonley.boards.z80.registers.CPURegister
import com.vonley.extensions.*
import kotlin.properties.ObservableProperty

class GPU(private val mmu: MMU, private val cpuRegister: CPURegister) {

    //0xFF40
    val lcdControlRegister = LCDControlRegister()
    //0xFF41
    val lcdStatusRegister = LCDStatusRegister()
    //FF47
    val lcdMonoChromePalette = LCDMonoChromePalette()

    //LCD Interrupts
    var vBlankInterrupt: UInt = 0x0u //40
    var hBlankInterrupt: UInt = 0x0u //48

    var lcdc: UByte = 0x0u //LY Compare//FF44 range: 0 - 153
    var lyCompare: UByte = 0x0u //ly compare //LYC //FF45 //0 - 153

    class LCDScroll {
        //FF42
        var y: UByte = 0x0u
        //FF43
        var x: UByte = 0x0u
    }

    class LCDWindow {
        //FF4A
        var y: UByte = 0x0u
        //FF4B - windows x position -7
        var x: UByte = 0x0u
    }

    class LCDControlRegister {
        //0xFF40
        var byte: UByte
            get() {
                var register: UByte = 0x0u
                register = register or (lcdDisplayEnable.asUByte shl 7)
                register = register or (windowTileMapDisplaySelect.asUByte shl 6)
                register = register or (windowDisplayEnable.asUByte shl 5)
                register = register or (bgWindowTileDataSelect.asUByte shl 4)
                register = register or (bgTIleMapDisplaySelect.asUByte shl 3)
                register = register or (objSpriteSize.asUByte shl 2)
                register = register or (objSpriteDisplayEnable.asUByte shl 1)
                register = register or (bgWindowDisplayPriority.asUByte shl 0)
                return register
            }
            set(value) {
                lcdDisplayEnable = (value shr 7).asBoolean
                windowTileMapDisplaySelect = (value shr 6).asBoolean
                windowDisplayEnable = (value shr 5).asBoolean
                bgWindowTileDataSelect = (value shr 4).asBoolean
                bgTIleMapDisplaySelect = (value shr 3).asBoolean
                objSpriteSize = (value shr 2).asBoolean
                objSpriteDisplayEnable = (value shr 1).asBoolean
                bgWindowDisplayPriority = (value shr 0).asBoolean
            }

        //bit 7-0
        var lcdDisplayEnable: Boolean = false
        var windowTileMapDisplaySelect: Boolean = false
        var windowDisplayEnable: Boolean = false
        var bgWindowTileDataSelect: Boolean = false
        var bgTIleMapDisplaySelect: Boolean = false
        var objSpriteSize: Boolean = false
        var objSpriteDisplayEnable: Boolean = false
        var bgWindowDisplayPriority: Boolean = false

    }

    class LCDStatusRegister {

        var byte: UByte
            get() {
                var register: UByte = 0x0u
                register = register or (lycInterrupt.asUByte shl 6)
                register = register or (oamInterrupt.asUByte shl 5)
                register = register or (vBlankInterrupt.asUByte shl 4)
                register = register or (hBlankInterrupt.asUByte shl 3)
                register = register or (coincidenceFlag.asUByte shl 2)
                register = register or modeFlag
                return register
            }
            set(value) {
                lycInterrupt = (value shr 6 and 0b1u).asBoolean
                oamInterrupt = (value shr 5 and 0b1u).asBoolean
                vBlankInterrupt = (value shr 4 and 0b1u).asBoolean
                hBlankInterrupt = (value shr 3 and 0b1u).asBoolean
                coincidenceFlag = (value shr 2 and 0b1u).asBoolean
                modeFlag = (value shr 6 and 0x3u)
            }

        var lycInterrupt: Boolean = true //lyc interrupt
        var oamInterrupt: Boolean = true //oam interrupt
        var vBlankInterrupt: Boolean = true //v-blank interrupt
        var hBlankInterrupt: Boolean = true //h-blank interrupt
        var coincidenceFlag = false //0: LYC<>LY, 1:LYC=LY
            private set

        //0 = h-blank, 1=vblank, 2: search oam,
        //3 transfer data to lcd driver
        var modeFlag: UByte = 0x0u
            private set(value) {
                field = value and 0x3u
            }

        //TODO: Use Mode?
        enum class MODE(modeByte: UByte) {
            HBLANK(0x0u),
            VBLANK(0x1u),
            OAM(0x2u),
            TRANSFERRING(0x3u),
        }
    }

    class LCDMonoChromePalette {

        var color3: LCDColor = LCDColor.BLACK
        var color2: LCDColor = LCDColor.BLACK
        var color1: LCDColor = LCDColor.BLACK
        var color0: LCDColor = LCDColor.BLACK

        var byte: UByte
            get() {
                var register: UByte = 0x0u
                register = register or (color3.colorByte shl 6)
                register = register or (color2.colorByte shl 4)
                register = register or (color1.colorByte shl 2)
                register = register or (color0.colorByte)
                return register
            }
            set(value) {
                color3 = (value shr 6).lcdColor
                color2 = (value shr 4).lcdColor
                color1 = (value shr 2).lcdColor
                color0 = (value).lcdColor
            }


        enum class LCDColor(val colorByte: UByte) {
            WHITE(0x0u),
            LIGHT(0x1u),
            DARK(0x2u),
            BLACK(0x3u);
        }
    }

}

