package com.vonley.extensions

import com.vonley.processor.MemoryManagementUnit

fun Int.getRegion(): MemoryManagementUnit.Region {
    when (this and 0xF000) {
        0x0000, 0x1000, 0x2000, 0x3000 -> {
            return MemoryManagementUnit.Region.ROM_BANK_0
        }
        0x4000, 0x5000, 0x600, 0x7000 -> {
            return MemoryManagementUnit.Region.ROM_BANK_1
        }
        0x8000, 0x9000 -> {
            return MemoryManagementUnit.Region.VIDEO_RAM
        }
        0xA000, 0xB000 -> {
            return MemoryManagementUnit.Region.EXTERNAL_RAM
        }
        0xC000, 0xD000, 0xE000 -> {
            return MemoryManagementUnit.Region.WORK_RAM
        }
        0xF000 -> {
            if (this in 0xF000..0xFDFF) {
                return MemoryManagementUnit.Region.WORK_RAM
            } else if (this in 0xFE00..0xFE9F) {
                return MemoryManagementUnit.Region.OAM_SPRITE
            }else if(this in 0xFF00..0xFF7F){
                return MemoryManagementUnit.Region.JOYPAD_REGISTER
            }else if(this in 0xFF80..0xFFFE){
                return MemoryManagementUnit.Region.ZERO_PAGE_RAM
            }
            //work out writing
            return MemoryManagementUnit.Region.UNKNOWN
        }
        else -> {
            throw IllegalAccessException("You're prohibited from writing to this region")
        }
    }
}

fun Int.toBinaryString(): String? {
    return Integer.toBinaryString(this)
}

fun Int.toHexString(): String? {
    return "0x${Integer.toHexString(this)}";
}