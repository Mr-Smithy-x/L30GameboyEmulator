package com.vonley.boards.z80.components

import com.vonley.boards.z80.memory.MMU
import com.vonley.boards.z80.registers.CPURegister
import com.vonley.extensions.asBoolean
import com.vonley.extensions.asUByte
import com.vonley.extensions.shl
import com.vonley.extensions.shr

class GPU(private val mmu: MMU, private val cpuRegister: CPURegister) {

    //0xFF40
    var lcdControlRegister: UByte
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
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }

    //region Setters
    //bit 7
    var lcdDisplayEnable: Boolean = false
        set(value) {
            field = value
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }

    //bit 6
    var windowTileMapDisplaySelect: Boolean = false
        set(value) {
            field = value
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }

    //bit 5
    var windowDisplayEnable: Boolean = false
        set(value) {
            field = value
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }

    //bit 4
    var bgWindowTileDataSelect: Boolean = false
        set(value) {
            field = value
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }

    //bit 3
    var bgTIleMapDisplaySelect: Boolean = false
        set(value) {
            field = value
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }

    //bit 2
    var objSpriteSize: Boolean = false
        set(value) {
            field = value
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }

    //bit 1
    var objSpriteDisplayEnable: Boolean = false
        set(value) {
            field = value
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }

    //bit 0
    var bgWindowDisplayPriority: Boolean = false
        set(value) {
            field = value
            mmu.writeByte(0xFF40u, lcdControlRegister)
        }
    //endregion

    //0xFF41
    var lcdStatusRegister: UByte
        get() {

            return 0u
        }
        set(value) {}

    var lyc: Boolean = true //lyc interrupt
    var mode_2: Boolean = true //oam interrupt
    var mode_1: Boolean = true //v-blank interrupt
    var mode_0: Boolean = true //h-blank interrupt
    var coincidence_flag = false //0: LYC<>LY, 1:LYC=LY
    //0 = h-blank, 1=vblank, 2: search oam,
    //3 transfer data to lcd driver
    var mode_flag: UByte = 0x0u


    //LCD Interrupts
    //40
    var vblank_interrupt: UInt = 0x0u
    //48
    var hblank_interrupt: UInt = 0x0u


    //LCD Position and Scrolling
    //FF42
    var scrollY: UByte = 0x0u
    //FF43
    var scrollX: UByte = 0x0u


    //LY
    //FF44
    //0 - 153
    var lcdc: UByte = 0x0u

    //LYC
    //FF45
    //0 - 153
    var lyc: UByte = 0x0u //ly compare

    //FF4A
    var windowY: UByte = 0x0u
    //FF4B - windows x position -7
    var windowX: UByte = 0x0u

}