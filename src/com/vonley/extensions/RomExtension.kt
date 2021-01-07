package com.vonley.extensions

import com.vonley.boards.z80.components.Rom

val Rom.romBank0: UByteArray
    get(){
        return this.data.copyOfRange(0x0000, 0x4000)
    }

val Rom.romBank1: UByteArray
    get(){
        return this.data.copyOfRange(0x4000, 0x8000)
    }

val Rom.bootRom: UByteArray
    get(){
        return this.data.copyOfRange(0x00, 0x100)
    }

