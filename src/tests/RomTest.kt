package tests

import com.vonley.processor.Rom
import junit.framework.TestCase
import java.io.File
import java.io.FileNotFoundException

class RomTest : TestCase() {

    lateinit var rom: Rom
    override fun setUp() {
        super.setUp()
        val file = File("gb/test.gb")
        if(file.exists()) {
            rom = Rom.load(file)
        }else{
            throw FileNotFoundException("Doesnt exist")
        }

    }

    fun testReadTitle(){
        println(rom.title)
        println(rom.validHeader)
        println(rom.cgbMode)
        println(rom.publisher)
    }

}