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

    //bit 7
    var lcdDisplayEnable: Boolean = false
    //bit 6
    var windowTileMapDisplaySelect: Boolean = false
    //bit 5
    var windowDisplayEnable: Boolean = false
    //bit 4
    var bgWindowTileDataSelect: Boolean = false
    //bit 3
    var bgTIleMapDisplaySelect: Boolean = false
    //bit 2
    var objSpriteSize: Boolean = false
    //bit 1
    var objSpriteDisplayEnable: Boolean = false
    //bit 0
    var bgWindowDisplayPriority: Boolean = false


}