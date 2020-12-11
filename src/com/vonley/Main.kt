package com.vonley

import com.vonley.registers.CPU

class Main {


    companion object {
        @JvmStatic
        fun main(string: Array<String>) {
            println("Gameboy Emulator Project. Wish me luck")
            print(CPU.hl)
        }
    }

}