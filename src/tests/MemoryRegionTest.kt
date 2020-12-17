package tests

import com.vonley.extensions.getRegion
import com.vonley.boards.z80.memory.MMU
import junit.framework.TestCase
import org.junit.Test

class MemoryRegionTest : TestCase() {

    @Test
    fun testRegions() {
        assert((0x0060u).toUShort().getRegion() == MMU.Region.ROM_BANK_0)
        assert((0x00FFu).toUShort().getRegion(true) == MMU.Region.BOOT_ROM)
        assert((0x0100u).toUShort().getRegion(true) == MMU.Region.ROM_BANK_0)
        assert((0x2000u).toUShort().getRegion(true) == MMU.Region.ROM_BANK_0)
        assert((0x4000u).toUShort().getRegion(true) == MMU.Region.ROM_BANK_1)
        assert((0x9D00u).toUShort().getRegion(true) == MMU.Region.VIDEO_RAM)
        assert((0xA800u).toUShort().getRegion(true) == MMU.Region.EXTERNAL_RAM)
        assert((0xC700u).toUShort().getRegion(true) == MMU.Region.WORK_RAM)
        assert((0xEF00u).toUShort().getRegion(true) == MMU.Region.ECHO_RAM)
        assert((0xFE70u).toUShort().getRegion(true) == MMU.Region.OAM_SPRITE)
        assert((0xFF40u).toUShort().getRegion(true) == MMU.Region.IO_REG)
        assert((0xFF90u).toUShort().getRegion(true) == MMU.Region.ZERO_PAGE_RAM)
    }

    @Test
    fun testOutOfBoundsRegions() {
        val overflow: UShort = (0x10000u).toUShort()
        assert(0u.toUShort() == overflow)
    }

}