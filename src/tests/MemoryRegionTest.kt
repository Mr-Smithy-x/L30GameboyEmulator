package tests

import com.vonley.extensions.getRegion
import com.vonley.processor.memory.MMU
import junit.framework.TestCase
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class MemoryRegionTest : TestCase() {


    @Test
    fun testRegions() {
        assert(0x0060.getRegion() == MMU.Region.ROM_BANK_0)
        assert(0x00FF.getRegion(true) == MMU.Region.BOOT_ROM)
        assert(0x0100.getRegion(true) == MMU.Region.ROM_BANK_0)
        assert(0x2000.getRegion(true) == MMU.Region.ROM_BANK_0)
        assert(0x4000.getRegion(true) == MMU.Region.ROM_BANK_1)
        assert(0x9D00.getRegion(true) == MMU.Region.VIDEO_RAM)
        assert(0xA800.getRegion(true) == MMU.Region.EXTERNAL_RAM)
        assert(0xC700.getRegion(true) == MMU.Region.WORK_RAM)
        assert(0xEF00.getRegion(true) == MMU.Region.ECHO_RAM)
        assert(0xFE70.getRegion(true) == MMU.Region.OAM_SPRITE)
        assert(0xFF40.getRegion(true) == MMU.Region.IO_REG)
        assert(0xFF90.getRegion(true) == MMU.Region.ZERO_PAGE_RAM)
    }


    @Test
    fun testOutOfBoundsRegions() {
        assertThrows<ArrayIndexOutOfBoundsException> {
            0x10000.getRegion()
        }
    }



}