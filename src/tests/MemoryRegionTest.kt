package tests

import com.vonley.extensions.getRegion
import com.vonley.processor.MemoryManagementUnit
import junit.framework.TestCase
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class MemoryRegionTest : TestCase() {


    @Test
    fun testRegions() {
        assert(0x0060.getRegion() == MemoryManagementUnit.Region.ROM_BANK_0)
        assert(0x00FF.getRegion(true) == MemoryManagementUnit.Region.BOOT_ROM)
        assert(0x0100.getRegion(true) == MemoryManagementUnit.Region.ROM_BANK_0)
        assert(0x2000.getRegion(true) == MemoryManagementUnit.Region.ROM_BANK_0)
        assert(0x4000.getRegion(true) == MemoryManagementUnit.Region.ROM_BANK_1)
        assert(0x9D00.getRegion(true) == MemoryManagementUnit.Region.VIDEO_RAM)
        assert(0xA800.getRegion(true) == MemoryManagementUnit.Region.EXTERNAL_RAM)
        assert(0xC700.getRegion(true) == MemoryManagementUnit.Region.WORK_RAM)
        assert(0xEF00.getRegion(true) == MemoryManagementUnit.Region.ECHO_RAM)
        assert(0xFE70.getRegion(true) == MemoryManagementUnit.Region.OAM_SPRITE)
        assert(0xFF40.getRegion(true) == MemoryManagementUnit.Region.IO_REG)
        assert(0xFF90.getRegion(true) == MemoryManagementUnit.Region.ZERO_PAGE_RAM)
    }


    @Test
    fun testOutOfBoundsRegions() {
        assertThrows<ArrayIndexOutOfBoundsException> {
            0x10000.getRegion()
        }
    }



}