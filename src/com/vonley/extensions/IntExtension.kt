package com.vonley.extensions

import com.vonley.processor.MemoryManagementUnit

fun Int.getRegion(bootRomEnabled: Boolean = false): MemoryManagementUnit.Region {
    return MemoryManagementUnit.Region.parse(this, bootRomEnabled)
}

fun Int.toBinaryString(): String {
    return "0b${Integer.toBinaryString(this)}"
}

fun Int.toHexString(): String {
    return "0x${Integer.toHexString(this)}"
}