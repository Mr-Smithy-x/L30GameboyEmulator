package tests

import com.vonley.boards.z80.components.GPU
import com.vonley.boards.z80.memory.MMU
import com.vonley.boards.z80.registers.CPURegister
import junit.framework.TestCase

class GPUTests: TestCase() {

    lateinit var gpu: GPU

    override fun setUp() {
        super.setUp()
        gpu = GPU(MMU(), CPURegister())
        gpu.lcdMonoChromePalette.byte = 0b10_00_11_01u
    }

    fun assertLCDMonoChromePalettesValue(){
        assert(gpu.lcdMonoChromePalette.color3 == GPU.LCDMonoChromePalette.LCDColor.BLACK)
        assert(gpu.lcdMonoChromePalette.color2 == GPU.LCDMonoChromePalette.LCDColor.DARK)
        assert(gpu.lcdMonoChromePalette.color1 == GPU.LCDMonoChromePalette.LCDColor.LIGHT)
        assert(gpu.lcdMonoChromePalette.color0 == GPU.LCDMonoChromePalette.LCDColor.WHITE)
    }



    fun testLCDBits(){

    }

}