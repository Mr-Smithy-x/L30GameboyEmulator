package com.vonley.boards.z80.instructions

import com.vonley.boards.z80.memory.MMU
import com.vonley.boards.z80.registers.CPURegister
import com.vonley.extensions.*

//nn = signed byte
/**
 * 8-bit registers: A, F, B, C, D, E, H, L
 * 16-bit registers: BC, DE, HL, PC, SP
 * byte: 8 bits: b
 * word: 16 bits: w
 * 8 bit addr: a
 * 16 bit addr: aa
 * d8 - 8 bit immediate data
 * d16 - 16 bit immediate data
 * a8 - 8bit unsigned data, which is added to $FF00 in certain instructions (replacement for in and out instructions)
 * a16 - 16 bit address
 * r8 - 8bit signed data
 */

@ExperimentalUnsignedTypes
class Instruction : HashMap<UShort, Execute>() {

    enum class OPS(
        override val mnemonic: String,
        override val opcode: UShort,
        override val cycles: Int,
        override val length: UShort = 1u,
        override val flagAffected: String = "----"
    ) : Execute {

        NOP("NOP -/-", 0x00u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                return cycles
            }
        },
        LD_BC_d16("LD BC, d16", 0x01u, 12, 3u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.bc = mmu.readShort(register.fetchIncLength(length))
                return cycles
            }
        },
        LD_BC_A("LD (BC),A", 0x02u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.bc, register.a)
                return cycles
            }
        },
        INC_BC("INC BC", 0x03u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.bc = register.bc.inc()
                return cycles
            }
        },
        INC_B("INC B", 0x04u, 4, flagAffected = "z0h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.b.checkHalfCarry(0x1u)
                register.b = register.b.inc()
                register.fr.z = register.b.isZero()
                register.fr.n = false
                return cycles
            }
        },
        DEC_B("DEC B", 0x05u, 4, flagAffected = "z1h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.b.checkHalfCarrySub(0x1u)
                register.b = register.b.dec()
                register.fr.z = register.b.isZero()
                register.fr.n = true
                return cycles
            }
        },
        LD_B_d8("LD B,d8", 0x06u, 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.b = mmu.readByte(register.fetchIncLength(length))
                return cycles
            }
        },
        RLCA("RLCA -/-", 0x07u, 4, flagAffected = "000c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                RLC_A.execute(mmu, register)
                register.fr.z = false
                return cycles
            }
        },
        LD_a16_SP("LD (a16), SP", 0x08u, 20, 3u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                mmu.writeShort(mmu.readShort(register.fetchIncLength(length)), register.sp)
                return cycles
            }
        },
        ADD_HL_BC("ADD HL, BC", 0x09u, 8, flagAffected = "-0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.n = false
                register.fr.c = register.hl.checkCarry(register.bc)
                register.fr.h = register.hl.checkHalfCarry(register.bc)
                register.hl = (register.hl + register.bc).and(0xFFFFu).toUShort()
                return cycles
            }
        },
        LD_A_BC("LD A,BC", 0x0Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = mmu.readByte(register.bc)
                return cycles
            }
        },
        DEC_BC("DEC BC", 0x0Bu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.bc = register.bc.dec()
                return cycles
            }
        },
        INC_C("INC C", 0x0Cu, 4, flagAffected = "z0h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.c.checkHalfCarry(0x1u)
                register.c = register.c.inc()
                register.fr.z = register.c.isZero()
                register.fr.n = false
                return cycles
            }
        },
        DEC_C("DEC C", 0x0Du, 4, flagAffected = "z1h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.c.checkHalfCarrySub(0x1u)
                register.c = register.c.dec()
                register.fr.z = register.c.isZero()
                register.fr.n = true
                return cycles
            }
        },
        LD_C_d8("LD C,d8", 0x0Eu, 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.c = mmu.readByte(register.fetchIncLength(length))
                return cycles
            }
        },
        RRCA("RRCA -/-", 0x0Fu, 4, flagAffected = "000c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                RRC_A.execute(mmu, register) //pc incremented in here
                register.fr.z = false
                return cycles
            }
        },
        RSTOP("STOP", 0x10u, 4, 2u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.addPC(length)
                return cycles
            }
        },
        LD_DE_d16("LD DE, d16", 0x11u, 12, 3u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.de = mmu.readShort(register.fetchIncLength(length))
                return cycles
            }
        },
        LD_DE_A("LD (DE), A", 0x12u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                mmu.writeByte(register.de, register.a)
                return cycles
            }
        },
        INC_DE("INC DE", 0x13u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.de = register.de.inc()
                return cycles
            }
        },
        INC_D("INC D", 0x14u, 4, flagAffected = "z0h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.d.checkHalfCarry(0x1u)
                register.d = register.d.inc()
                register.fr.z = register.d.isZero()
                register.fr.n = false
                return cycles
            }
        },
        DEC_D("DEC D", 0x15u, 4, flagAffected = "z1h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.d.checkHalfCarrySub(0x1u)
                register.d = register.d.dec()
                register.fr.z = register.d.isZero()
                register.fr.n = true
                return cycles
            }
        },
        LD_D_d8("LD D,d8", 0x16u, 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.d = mmu.readByte(register.fetchIncLength(length))
                return cycles
            }
        },
        RLA("RLA -/-", 0x17u, 4, flagAffected = "000c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                RL_A.execute(mmu, register) //pc incremented here
                register.fr.z = false
                return cycles
            }
        },
        JR_r8("JR r8", 0x18u, 12, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val value: UByte = mmu.readByte(register.fetchIncLength(length))
                register.pc = value.jump(register.pc)
                return cycles
            }
        },
        ADD_HL_DE("ADD HL,DE", 0x19u, 8, flagAffected = "-0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val value = ((register.hl + register.de) and 0xFFFFu).toUShort()
                register.fr.n = false
                register.fr.h = register.hl.checkHalfCarry(register.de)
                register.fr.c = register.hl.checkCarry(register.de)
                register.hl = value
                return cycles
            }
        },
        LD_A_DE("LD A,(DE)", 0x1Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = mmu.readByte(register.de)
                return cycles
            }
        },
        DEC_DE("DEC DE", 0x1Bu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.de = register.de.dec()
                return cycles
            }
        },
        INC_E("INC E", 0x1Cu, 4, flagAffected = "z0h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.e.checkHalfCarry(0x1u)
                register.e = register.e.inc()
                register.fr.z = register.e.isZero()
                register.fr.n = false
                return cycles
            }
        },
        DEC_E("DEC E", 0x1Du, 4, flagAffected = "z1h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.e.checkHalfCarrySub(0x1u)
                register.e = register.e.dec()
                register.fr.z = register.e.isZero()
                register.fr.n = true
                return cycles
            }
        },
        LD_E_d8("LD E,d8", 0x1Eu, 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.e = mmu.readByte(register.fetchIncLength(length))
                return cycles
            }
        },
        RRA("RRA -/-", 0x1Fu, 4, flagAffected = "000c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                RR_A.execute(mmu, register) //pc inc here
                return cycles
            }
        },

        JR_NZ_r8("JR NZ,r8", 0x20u, 12 / 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val readByte = mmu.readByte(register.fetchIncLength(length))
                if (!register.fr.z) {
                    register.pc = readByte.jump(register.pc)
                    return 12
                }
                return 8
            }
        },
        LD_HL_d16("LD HL, d16", 0x21u, 12, 3u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.hl = mmu.readShort(register.fetchIncLength(length))
                return cycles
            }
        },
        LD_HLI_A("LD (HLI), A", 0x22u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                return LDI_HL_A.execute(mmu, register)
            }
        },
        LD_HLp_A("LD (HL+), A", 0x22u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                return LDI_HL_A.execute(mmu, register)
            }
        },
        LDI_HL_A("LDI (HL), A", 0x22u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.a)
                register.incHL()
                return cycles
            }
        },
        INC_HL("INC HL", 0x23u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.incHL()
                return cycles
            }
        },
        INC_H("INC H", 0x24u, 4, flagAffected = "z0h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.h.checkHalfCarry(0x1u)
                register.h = register.h.inc()
                register.fr.z = register.h.isZero()
                register.fr.n = false
                return cycles
            }
        },
        DEC_H("DEC H", 0x25u, 4, flagAffected = "z1h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.h.checkHalfCarrySub(0x1u)
                register.h = register.h.dec()
                register.fr.z = register.h.isZero()
                register.fr.n = true
                return cycles
            }
        },
        LD_H_d8("LD H,d8", 0x26u, 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.h = mmu.readByte(register.fetchIncLength(length))
                return cycles
            }
        },
        DAA("DAA -/-", 0x27u, 4, flagAffected = "z-0c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.addPC(0x1u)
                var t = register.a
                if (register.fr.h || (t and 0x0Fu) > 0x09u) {
                    t = if (register.fr.n) {
                        t.minus(0x06u).toUByte()
                    } else {
                        t.plus(0x06u).toUByte()
                    }
                }
                if (register.fr.c || (t shr 4) > 0x09u) {
                    t = if (register.fr.n) {
                        t.minus(0x60u).toUByte()
                    } else {
                        register.fr.c = true
                        t.plus(0x60u).toUByte()
                    }
                }
                register.fr.z = t.isZero()
                register.fr.h = false
                register.a = register.a and 0xFFu
                return cycles
            }
        },
        JR_Z_r8("JR Z,r8", 0x28u, 12 / 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val readByte = mmu.readByte(register.fetchIncLength(length))
                if (register.fr.z) {
                    register.pc = readByte.jump(register.pc)
                    return 12
                }
                return 8
            }
        },
        ADD_HL_HL("ADD HL, HL", 0x29u, 8, flagAffected = "-0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val value = ((register.hl + register.hl) and 0xFFFFu).toUShort()
                register.fr.n = false
                register.fr.h = register.hl.checkHalfCarry(register.hl)
                register.fr.c = register.hl.checkCarry(register.hl)
                register.hl = value
                return cycles
            }
        },
        LD_A_HLI("LD A,(HLI)", 0x2Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                return LDI_A_HL.execute(mmu, register)
            }
        },
        LD_A_HLp("LD A,(HL+)", 0x2Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                return LDI_A_HL.execute(mmu, register)
            }
        },
        LDI_A_HL("LDI A,(HL)", 0x2Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = mmu.readByte(register.hl)
                register.incHL()
                return cycles
            }
        },
        DEC_HL("DEC HL", 0x2Bu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.hl = register.hl.dec()
                return cycles
            }
        },
        INC_L("INC L", 0x2Cu, 4, flagAffected = "z0h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.l.checkHalfCarryAdd(0x1u)
                register.l = register.l.inc()
                register.fr.z = register.l.isZero()
                register.fr.n = false
                return cycles
            }
        },
        DEC_L("DEC L", 0x2Du, 4, flagAffected = "z1h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.l.checkHalfCarrySub(0x1u)
                register.l = register.l.dec()
                register.fr.z = register.l.isZero()
                register.fr.n = true
                return cycles
            }
        },
        LD_L_d8("LD L,d8", 0x2Eu, 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.l = mmu.readByte(register.fetchIncLength(length))
                return cycles
            }
        },
        CPL("CPL -/-", 0x2Fu, 4, flagAffected = "-11-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a xor 0xFFu
                register.fr.n = true
                register.fr.h = true
                return cycles
            }
        },

        JR_NC_r8("JR NC,r8", 0x30u, 12 / 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val readByte = mmu.readByte(register.fetchIncLength(length))
                if (!register.fr.c) {
                    register.pc = readByte.jump(register.pc)
                    return 12
                }
                return 8
            }
        },
        LD_SP_d16("LD SP, d16", 0x31u, 12, 3u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.sp = mmu.readShort(register.fetchIncLength(length))
                return cycles
            }
        },
        LD_HLD_A("LD (HLD),A", 0x32u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                return LDD_HL_A.execute(mmu, register)
            }
        },
        LD_HLm_A("LD (HL-),A", 0x32u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                return LDD_HL_A.execute(mmu, register)
            }
        },
        LDD_HL_A("LDD (HL),A", 0x32u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.a)
                register.decHL()
                return cycles
            }
        },
        INC_SP("INC SP", 0x33u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.sp = register.sp.inc().and(0xFFFFu)
                return cycles
            }
        },
        INC_HL_a16("INC (HL)", 0x34u, 12, flagAffected = "z0h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                var readByte = mmu.readByte(register.hl)
                register.fr.n = false
                register.fr.h = readByte.checkHalfCarry(0x1u)
                readByte = readByte.inc()
                register.fr.z = readByte.isZero()
                mmu.writeByte(register.hl, readByte)
                return cycles
            }
        },
        DEC_HL_a16("DEC (HL)", 0x35u, 12, flagAffected = "z1h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                var readByte = mmu.readByte(register.hl)
                register.fr.n = true
                register.fr.h = readByte.checkHalfCarrySub(0x1u)
                readByte = readByte.dec()
                register.fr.z = readByte.isZero()
                mmu.writeByte(register.hl, readByte)
                return cycles
            }
        },
        LD_HL_d8("LD HL,d8", 0x36u, 12, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                mmu.writeByte(register.hl, mmu.readByte(register.fetchIncLength(length)))
                return cycles
            }
        },
        SCF("SCF -/-", 0x37u, 4, flagAffected = "-001") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = true
                return cycles
            }
        },
        JR_C_r8("JR C,r8", 0x38u, 12 / 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val readByte = mmu.readByte(register.fetchIncLength(length))
                if (register.fr.c) {
                    register.pc = readByte.jump(register.pc)
                    return 12
                }
                return 8
            }
        },
        ADD_HL_SP("ADD HL, SP", 0x39u, 8, flagAffected = "-0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val value = ((register.hl + register.sp) and 0xFFFFu).toUShort()
                register.fr.n = false
                register.fr.h = register.hl.checkHalfCarry(register.sp)
                register.fr.c = register.hl.checkCarry(register.sp)
                register.hl = value
                return cycles
            }
        },
        LD_A_HLD("LD A,(HLD)", 0x3Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                return LDD_A_HL.execute(mmu, register)
            }
        },
        LD_A_HLm("LD A,(HL-)", 0x3Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                return LDD_A_HL.execute(mmu, register)
            }
        },
        LDD_A_HL("LDD A,(HL)", 0x3Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = mmu.readByte(register.hl)
                register.decHL()
                return cycles
            }
        },
        DEC_SP("DEC SP", 0x3Bu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.sp = register.sp.dec()
                return cycles
            }
        },
        INC_A("INC A", 0x3Cu, 4, flagAffected = "z0h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.a.checkHalfCarryAdd(0x1u)
                register.a = register.a.inc()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },
        DEC_A("DEC A", 0x3Du, 4, flagAffected = "z1h-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.h = register.a.checkHalfCarrySub(0x1u)
                register.a = register.a.dec()
                register.fr.z = register.a.isZero()
                register.fr.n = true
                return cycles
            }
        },
        LD_A_d8("LD A,d8", 0x3Eu, 8, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.a = mmu.readByte(register.fetchIncLength(length))
                return cycles
            }
        },
        CCF("CCF -/-", 0x3Fu, 4, flagAffected = "-00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = !register.fr.c
                return cycles
            }
        },

        LD_B_B("LD B,B", 0x40u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.b = register.b
                return cycles
            }
        },
        LD_B_C("LD B,C", 0x41u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.b = register.c
                return cycles
            }
        },
        LD_B_D("LD B,D", 0x42u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.b = register.d
                return cycles
            }
        },
        LD_B_E("LD B,E", 0x43u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.b = register.e
                return cycles
            }
        },
        LD_B_H("LD B,H", 0x44u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.b = register.h
                return cycles
            }
        },
        LD_B_L("LD B,L", 0x45u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.b = register.l
                return cycles
            }
        },
        LD_B_HL("LD B,(HL)", 0x46u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.b = mmu.readByte(register.hl)
                return cycles
            }
        },
        LD_B_A("LD B,A", 0x47u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.b = register.a
                return cycles
            }
        },

        LD_C_B("LD C,B", 0x48u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.c = register.b
                return cycles
            }
        },
        LD_C_C("LD C,C", 0x49u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.c = register.c
                return cycles
            }
        },
        LD_C_D("LD C,D", 0x4Au, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.c = register.d
                return cycles
            }
        },
        LD_C_E("LD C,E", 0x4Bu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.c = register.e
                return cycles
            }
        },
        LD_C_H("LD C,H", 0x4Cu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.c = register.h
                return cycles
            }
        },
        LD_C_L("LD C,L", 0x4Du, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.c = register.l
                return cycles
            }
        },
        LD_C_HL("LD C,(HL)", 0x4Eu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.c = mmu.readByte(register.hl)
                return cycles
            }
        },
        LD_C_A("LD C,A", 0x4Fu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.c = register.a
                return cycles
            }
        },

        LD_D_B("LD D,B", 0x50u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.d = register.b
                return cycles
            }
        },
        LD_D_C("LD D,C", 0x51u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.d = register.c
                return cycles
            }
        },
        LD_D_D("LD D,D", 0x52u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.d = register.d
                return cycles
            }
        },
        LD_D_E("LD D,E", 0x53u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.d = register.e
                return cycles
            }
        },
        LD_D_H("LD D,H", 0x54u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.d = register.h
                return cycles
            }
        },
        LD_D_L("LD D,L", 0x55u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.d = register.l
                return cycles
            }
        },
        LD_D_HL("LD D,(HL)", 0x56u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.d = mmu.readByte(register.hl)
                return cycles
            }
        },
        LD_D_A("LD D,A", 0x57u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.d = register.a
                return cycles
            }
        },

        LD_E_B("LD E,B", 0x58u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.e = register.b
                return cycles
            }
        },
        LD_E_C("LD E,C", 0x59u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.e = register.c
                return cycles
            }
        },
        LD_E_D("LD E,D", 0x5Au, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.e = register.d
                return cycles
            }
        },
        LD_E_E("LD E,E", 0x5Bu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.e = register.e
                return cycles
            }
        },
        LD_E_H("LD E,H", 0x5Cu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.e = register.h
                return cycles
            }
        },
        LD_E_L("LD E,L", 0x5Du, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.e = register.l
                return cycles
            }
        },
        LD_E_HL("LD E,(HL)", 0x5Eu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.e = mmu.readByte(register.hl)
                return cycles
            }
        },
        LD_E_A("LD E,A", 0x5Fu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.e = register.a
                return cycles
            }
        },

        LD_H_B("LD H,B", 0x60u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.h = register.b
                return cycles
            }
        },
        LD_H_C("LD H,C", 0x61u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.h = register.c
                return cycles
            }
        },
        LD_H_D("LD H,D", 0x62u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.h = register.d
                return cycles
            }
        },
        LD_H_E("LD H,E", 0x63u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.h = register.e
                return cycles
            }
        },
        LD_H_H("LD H,H", 0x64u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.h = register.h
                return cycles
            }
        },
        LD_H_L("LD H,L", 0x65u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.h = register.l
                return cycles
            }
        },
        LD_H_HL("LD H,(HL)", 0x66u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.h = mmu.readByte(register.hl)
                return cycles
            }
        },
        LD_H_A("LD H,A", 0x67u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.h = register.a
                return cycles
            }
        },

        LD_L_B("LD L,B", 0x68u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.l = register.b
                return cycles
            }
        },
        LD_L_C("LD L,C", 0x69u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.l = register.c
                return cycles
            }
        },
        LD_L_D("LD L,D", 0x6Au, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.l = register.d
                return cycles
            }
        },
        LD_L_E("LD L,E", 0x6Bu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.l = register.e
                return cycles
            }
        },
        LD_L_H("LD L,H", 0x6Cu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.l = register.h
                return cycles
            }
        },
        LD_L_L("LD L,L", 0x6Du, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.l = register.l
                return cycles
            }
        },
        LD_L_HL("LD L,(HL)", 0x6Eu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.l = mmu.readByte(register.hl)
                return cycles
            }
        },
        LD_L_A("LD L,A", 0x6Fu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.l = register.a
                return cycles
            }
        },

        LD_HL_B("LD (HL),B", 0x70u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.b)
                return cycles
            }
        },
        LD_HL_C("LD (HL),C", 0x71u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.c)
                return cycles
            }
        },
        LD_HL_D("LD (HL),D", 0x72u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.d)
                return cycles
            }
        },
        LD_HL_E("LD (HL),E", 0x73u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.e)
                return cycles
            }
        },
        LD_HL_H("LD (HL),H", 0x74u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.h)
                return cycles
            }
        },
        LD_HL_L("LD (HL),L", 0x75u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.l)
                return cycles
            }
        },
        HALT("HALT -/-", 0x76u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                return cycles
            }
        },
        LD_HL_A("LD (HL),A", 0x77u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                mmu.writeByte(register.hl, register.a)
                return cycles
            }
        },

        LD_A_B("LD A,B", 0x78u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.b
                return cycles
            }
        },
        LD_A_C("LD A,C", 0x79u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.c
                return cycles
            }
        },
        LD_A_D("LD A,D", 0x7Au, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.d
                return cycles
            }
        },
        LD_A_E("LD A,E", 0x7Bu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.e
                return cycles
            }
        },
        LD_A_H("LD A,H", 0x7Cu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.h
                return cycles
            }
        },
        LD_A_L("LD A,L", 0x7Du, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.l
                return cycles
            }
        },
        LD_A_HL("LD A,(HL)", 0x7Eu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = mmu.readByte(register.hl)
                return cycles
            }
        },
        LD_A_A("LD A,A", 0x7Fu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a
                return cycles
            }
        },

        //H SET IF CARRY FROM BIT 3, C SET IF CARRY FROM BIT 7
        ADD_A_B("ADD A,B", 0x80u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val plus = register.a.plus(register.b)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },
        ADD_A_C("ADD A,C", 0x81u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val plus = register.a.plus(register.c)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },
        ADD_A_D("ADD A,D", 0x82u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val plus = register.a.plus(register.d)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },
        ADD_A_E("ADD A,E", 0x83u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val plus = register.a.plus(register.e)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },
        ADD_A_H("ADD A,H", 0x84u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val plus = register.a.plus(register.h)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },
        ADD_A_L("ADD A,L", 0x85u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val plus = register.a.plus(register.l)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },
        ADD_A_HL("ADD A,(HL)", 0x86u, 8, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val plus = register.a.plus(mmu.readByte(register.hl))
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },
        ADD_A_A("ADD A,A", 0x87u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val plus = register.a.plus(register.a)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
                return cycles
            }
        },

        ADC_A_B("ADC A,B", 0x88u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a + register.b + register.fr.c.asUByte
                var byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = false
                register.fr.h = register.a.checkHalfCarryAdd(register.b, register.fr.c)
                register.fr.c = register.a.checkCarry(register.b)
                register.a = byteVal
                return cycles
            }
        },
        ADC_A_C("ADC A,C", 0x89u, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a + register.c + register.fr.c.asUByte
                var byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = false
                register.fr.h = register.a.checkHalfCarryAdd(register.c, register.fr.c)
                register.fr.c = register.a.checkCarry(register.c)
                register.a = byteVal
                return cycles
            }
        },
        ADC_A_D("ADC A,D", 0x8Au, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a + register.d + register.fr.c.asUByte
                var byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = false
                register.fr.h = register.a.checkHalfCarryAdd(register.d, register.fr.c)
                register.fr.c = register.a.checkCarry(register.d)
                register.a = byteVal
                return cycles
            }
        },
        ADC_A_E("ADC A,E", 0x8Bu, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a + register.e + register.fr.c.asUByte
                var byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = false
                register.fr.h = register.a.checkHalfCarryAdd(register.e, register.fr.c)
                register.fr.c = register.a.checkCarry(register.e)
                register.a = byteVal
                return cycles
            }
        },
        ADC_A_H("ADC A,H", 0x8Cu, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a + register.h + register.fr.c.asUByte
                var byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = false
                register.fr.h = register.a.checkHalfCarryAdd(register.h, register.fr.c)
                register.fr.c = register.a.checkCarry(register.h)
                register.a = byteVal
                return cycles
            }
        },
        ADC_A_L("ADC A,L", 0x8Du, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a + register.l + register.fr.c.asUByte
                var byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = false
                register.fr.h = register.a.checkHalfCarryAdd(register.l, register.fr.c)
                register.fr.c = register.a.checkCarry(register.l)
                register.a = byteVal
                return cycles
            }
        },
        ADC_A_HL("ADC A,(HL)", 0x8Eu, 8, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val hlByte = mmu.readByte(register.hl)
                val t = register.a + hlByte + register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = false
                register.fr.h = register.a.checkHalfCarryAdd(hlByte, register.fr.c)
                register.fr.c = register.a.checkCarry(hlByte)
                register.a = byteVal
                return cycles
            }
        },
        ADC_A_A("ADC A,A", 0x8Fu, 4, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a + register.a + register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = false
                register.fr.h = register.a.checkHalfCarryAdd(register.a, register.fr.c)
                register.fr.c = register.a.checkCarry(register.a)
                register.a = byteVal
                return cycles
            }
        },

        SUB_A_B("SUB A,B", 0x90u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val minus = register.a.minus(register.b)
                register.fr.n = true
                if (register.b > register.a) {
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                return cycles
            }
        },
        SUB_A_C("SUB A,C", 0x91u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val minus = register.a.minus(register.c)
                register.fr.n = true
                if (register.c > register.a) {
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                return cycles
            }
        },
        SUB_A_D("SUB A,D", 0x92u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val minus = register.a.minus(register.d)
                register.fr.n = true
                if (register.d > register.a) {
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                return cycles
            }
        },
        SUB_A_E("SUB A,E", 0x93u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val minus = register.a.minus(register.e)
                register.fr.n = true
                if (register.e > register.a) {
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                return cycles
            }
        },
        SUB_A_H("SUB A,H", 0x94u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val minus = register.a.minus(register.h)
                register.fr.n = true
                if (register.h > register.a) {
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                return cycles
            }
        },
        SUB_A_L("SUB A,L", 0x95u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val minus = register.a.minus(register.l)
                register.fr.n = true
                if (register.l > register.a) {
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                return cycles
            }
        },
        SUB_A_HL("SUB A,(HL)", 0x96u, 8, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                var hlAddr = mmu.readByte(register.hl)
                val minus = register.a.minus(hlAddr)
                register.fr.n = true
                if (hlAddr > register.a) {
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                return cycles
            }
        },
        SUB_A_A("SUB A,A", 0x97u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val minus = register.a.minus(register.a)
                register.fr.n = true
                if (register.a > register.a) {
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                return cycles
            }
        },

        SBC_A_B("SBC A,B", 0x98u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a - register.b - register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySBC(register.b, register.fr.c)
                register.fr.c = register.a.checkCarrySBC(register.b, register.fr.c)
                register.a = byteVal
                return cycles
            }
        },
        SBC_A_C("SBC A,C", 0x99u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a - register.c - register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySBC(register.c, register.fr.c)
                register.fr.c = register.a.checkCarrySBC(register.c, register.fr.c)
                register.a = byteVal
                return cycles
            }
        },
        SBC_A_D("SBC A,D", 0x9Au, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a - register.d - register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySBC(register.d, register.fr.c)
                register.fr.c = register.a.checkCarrySBC(register.d, register.fr.c)
                register.a = byteVal
                return cycles
            }
        },
        SBC_A_E("SBC A,E", 0x9Bu, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a - register.e - register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySBC(register.e, register.fr.c)
                register.fr.c = register.a.checkCarrySBC(register.e, register.fr.c)
                register.a = byteVal
                return cycles
            }
        },
        SBC_A_H("SBC A,H", 0x9Cu, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a - register.h - register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySBC(register.h, register.fr.c)
                register.fr.c = register.a.checkCarrySBC(register.h, register.fr.c)
                register.a = byteVal
                return cycles
            }
        },
        SBC_A_L("SBC A,L", 0x9Du, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a - register.l - register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySBC(register.l, register.fr.c)
                register.fr.c = register.a.checkCarrySBC(register.l, register.fr.c)
                register.a = byteVal
                return cycles
            }
        },
        SBC_A_HL("SBC A,(HL)", 0x9Eu, 8, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val hlByte = mmu.readByte(register.hl)
                val t = register.a - hlByte - register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySBC(hlByte, register.fr.c)
                register.fr.c = register.a.checkCarrySBC(hlByte, register.fr.c)
                register.a = byteVal
                return cycles
            }
        },
        SBC_A_A("SBC A,A", 0x9Fu, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val t = register.a - register.a - register.fr.c.asUByte
                val byteVal = (t and 0xFFu).toUByte()
                register.fr.z = byteVal.isZero()
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySBC(register.a, register.fr.c)
                register.fr.c = register.a.checkCarrySBC(register.a, register.fr.c)
                register.a = byteVal
                return cycles
            }
        },

        AND_A_B("AND A,B", 0xA0u, 4, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.and(register.b)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
                return cycles
            }
        },
        AND_A_C("AND A,C", 0xA1u, 4, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.and(register.c)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
                return cycles
            }
        },
        AND_A_D("AND A,D", 0xA2u, 4, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.and(register.d)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
                return cycles
            }
        },
        AND_A_E("AND A,E", 0xA3u, 4, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.and(register.e)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
                return cycles
            }
        },
        AND_A_H("AND A,H", 0xA4u, 4, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.and(register.h)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
                return cycles
            }
        },
        AND_A_L("AND A,L", 0xA5u, 4, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.and(register.l)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
                return cycles
            }
        },
        AND_A_HL("AND A,(HL)", 0xA6u, 8, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.and(mmu.readByte(register.hl))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
                return cycles
            }
        },
        AND_A_A("AND A,A", 0xA7u, 4, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.and(register.a)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
                return cycles
            }
        },

        XOR_A_B("XOR A,B", 0xA8u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.xor(register.b)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        XOR_A_C("XOR A,C", 0xA9u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.xor(register.c)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        XOR_A_D("XOR A,D", 0xAAu, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.xor(register.d)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        XOR_A_E("XOR A,E", 0xABu, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.xor(register.e)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        XOR_A_H("XOR A,H", 0xACu, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.xor(register.h)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        XOR_A_L("XOR A,L", 0xADu, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.xor(register.l)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        XOR_A_HL("XOR A,(HL)", 0xAEu, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.xor(mmu.readByte(register.hl))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        XOR_A_A("XOR A,A", 0xAFu, 4, flagAffected = "1000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.xor(register.a)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },

        OR_A_B("OR A,B", 0xB0u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.or(register.b)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        OR_A_C("OR A,C", 0xB1u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.or(register.c)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        OR_A_D("OR A,D", 0xB2u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.or(register.d)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        OR_A_E("OR A,E", 0xB3u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.or(register.e)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        OR_A_H("OR A,H", 0xB4u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.or(register.h)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        OR_A_L("OR A,L", 0xB5u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.or(register.l)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        OR_A_HL("OR A,(HL)", 0xB6u, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.or(mmu.readByte(register.hl))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        OR_A_A("OR A, A", 0xB7u, 4, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.a = register.a.or(register.a)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },

        CP_B("CP B", 0xB8u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.z = register.a == register.b
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySub(register.b)
                register.fr.c = register.a.checkCarrySub(register.b)
                return cycles
            }
        },
        CP_C("CP C", 0xB9u, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.z = register.a == register.c
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySub(register.c)
                register.fr.c = register.a.checkCarrySub(register.c)
                return cycles
            }
        },
        CP_D("CP D", 0xBAu, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.z = register.a == register.d
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySub(register.d)
                register.fr.c = register.a.checkCarrySub(register.d)
                return cycles
            }
        },
        CP_E("CP E", 0xBBu, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.z = register.a == register.e
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySub(register.e)
                register.fr.c = register.a.checkCarrySub(register.e)
                return cycles
            }
        },
        CP_H("CP H", 0xBCu, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.z = register.a == register.h
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySub(register.h)
                register.fr.c = register.a.checkCarrySub(register.h)
                return cycles
            }
        },
        CP_L("CP L", 0xBDu, 4, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.z = register.a == register.l
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySub(register.l)
                register.fr.c = register.a.checkCarrySub(register.l)
                return cycles
            }
        },
        CP_HL("CP (HL)", 0xBEu, 8, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                var hlByte = mmu.readByte(register.hl)
                register.fr.z = register.a == hlByte
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySub(hlByte)
                register.fr.c = register.a.checkCarrySub(hlByte)
                return cycles
            }
        },
        CP_A("CP A", 0xBFu, 4, flagAffected = "11hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                register.fr.z = true
                register.fr.n = true
                register.fr.h = register.a.checkHalfCarrySub(register.a)
                register.fr.c = register.a.checkCarrySub(register.a)
                return cycles
            }
        },

        RET_NZ("RET NZ", 0xC0u, 20 / 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        POP_BC("POP BC", 0xC1u, 12, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.bc = mmu.readShort(register.sp)
                register.addSP(0x2u)
                return cycles
            }
        },
        JP_NZ_a16("JP NZ, a16", 0xC2u, 16 / 12, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        JP_a16("JP a16", 0xC3u, 12, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        CALL_NZ_a16("CALL NZ, a16", 0xC4u, 24 / 12, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        PUSH_BC("PUSH BC", 0xC5u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.bc)
                return cycles
            }
        },
        ADD_A_d8("ADD A, d8", 0xC6u, 8, 2u, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val plus = register.a.plus(mmu.readByte(opcode.inc()))
                register.a = plus.and(0xFFu).toUByte()


                register.fr.n = false
                register.fr.z = register.a.isZero()
                return cycles
            }
        },
        RST_00H("RST 00H", 0xC7u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RET_Z("RET Z", 0xC8u, 20 / 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RET("RET -/-", 0xC9u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.addSP(0x2u)
                register.pc = mmu.readShort(register.sp)
                return cycles
            }
        },
        JP_Z_a16("JP Z, a16", 0xCAu, 16 / 12, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        PREFIX("PREFIX", 0xCBu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        CALL_Z_a16("CALL Z, a16", 0xCCu, 24 / 12, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        CALL_a16("CALL a16", 0xCDu, 24, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                mmu.writeShort(register.sp, opcode.plus(length).toUShort())
                register.minusSP(0x2u)
                register.pc = mmu.readShort(register.pc.plus(0x1u).toUShort())
                return cycles
            }
        },
        ADC_A_d8("ADC A, d8", 0xCEu, 8, 2u, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RST_08H("RST 08H", 0xCFu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RET_NC("RET NC", 0xD0u, 20 / 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        POP_DE("POP DE", 0xD1u, 12, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.de = mmu.readShort(register.sp)
                register.addSP(0x2u)
                return cycles
            }
        },
        JP_NC_a16("JP NC, a16", 0xD2u, 16 / 12, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        CALL_NC_a16("CALL NC, a16", 0xD4u, 24 / 12, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        PUSH_DE("PUSH DE", 0xD5u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.de)
                return cycles
            }
        },

        SUB_A_d8("SUB A, d8", 0xD6u, 8, 2u, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sub = register.a.minus(mmu.readByte(opcode.inc()))
                register.a = sub.and(0xFFu).toUByte()
                return cycles
            }
        },
        RST_10H("RST 10H", 0xD7u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RET_C("RET C", 0xD8u, 20 / 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RETI("RETI -/-", 0xD9u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        JP_C_a16("JP C, a16", 0xDAu, 16 / 12, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        CALL_C_a16("CALL C, a16", 0xDCu, 24 / 12, 3u, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SBC_A_d8("SBC A, d8", 0xDEu, 8, 2u, flagAffected = "z0hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.de)
                return cycles
            }
        },
        RST_18H("RST 18H", 0xDFu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        LDH_a8_A("LDH (a8), A // LD (FF00+a8), A", 0xE0u, 12, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val addr: UShort = 0xFF00u.plus(mmu.readByte(opcode.inc())).and(0xFFFFu).toUShort()
                mmu.writeByte(addr, register.a)
                return cycles
            }
        },
        POP_HL("POP HL", 0xE1u, 12, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.hl = mmu.readShort(register.sp)
                register.addSP(0x2u)
                return cycles
            }
        },
        LD_Ca_A("LD (C),A", 0xE2u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val addr: UShort = 0xFF00u.plus(register.c).and(0xFFFFu).toUShort()
                mmu.writeByte(addr, register.a)
                return cycles
            }
        },
        PUSH_HL("PUSH HL", 0xE5u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.hl)
                return cycles
            }
        },
        AND_A_d8("AND A, d8", 0xE6u, 8, 2u, flagAffected = "z010") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.a = register.a.and(mmu.readByte(opcode.inc()))
                return cycles
            }
        },
        RST_20H("RST 20H", 0xE7u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        //00hc
        ADD_SP_r8("ADD SP, r8", 0xE8u, 16, 2u, flagAffected = "00hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        JP_HL("JP HL", 0xE9u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        LD_a16_A("LD (a16),A", 0xEAu, 16, 3u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                mmu.writeByte(mmu.readShort(opcode.inc()), register.a)
                return cycles
            }
        },
        XOR_A_d8("XOR A, d8", 0xEEu, 8, 2u, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.a = register.a.xor(mmu.readByte(opcode.inc()))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        RST_28H("RST 28H", 0xEFu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        LDH_A_a8("LDH A,(a8) // LD A,(FF00+n)", 0xF0u, 12, 2u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.a = mmu.readByte(0xFF00u.plus(mmu.readByte(opcode.inc())).and(0xFFFFu).toUShort())
                return cycles
            }
        },
        POP_AF("POP AF", 0xF1u, 12, flagAffected = "znhc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.af = mmu.readShort(register.sp)
                register.addSP(0x2u)
                return cycles
            }
        },
        LD_A_Ca("LD A,(C)", 0xF2u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.a = mmu.readByte((0xFF00u).plus(register.c).and(0xFFFFu).toUShort())
                return cycles
            }
        },
        DI("DI -/-", 0xF3u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        PUSH_AF("PUSH AF", 0xF5u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.af)
                return cycles
            }
        },
        OR_A_d8("OR A, d8", 0xF6u, 8, 2u, flagAffected = "z0000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.a = register.a.or(mmu.readByte(opcode.inc()))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
                return cycles
            }
        },
        RST_30H("RST 30H", 0xF7u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        LD_HL_SPr8("LD HL,SP+r8", 0xF8u, 12, 2u, "00hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                LDHL_SP_r8.execute(mmu, register)
                return cycles
            }
        },
        LDHL_SP_r8("LD HL,SP+r8", 0xF8u, 12, 2u, "00hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sp_n = mmu.readByte(opcode.inc()).minus(128u)
                register.hl = register.sp.plus(sp_n).and(0xFFFFu).toUShort()
                return cycles
            }
        },
        LD_SP_HL("LD SP,HL", 0xF9u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.sp = register.hl
                return cycles
            }
        },
        LD_A_a16("LD A,a16", 0xFAu, 16, 3u, "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.a = mmu.readByte(mmu.readShort(opcode.inc()))
                return cycles
            }
        },
        EI("EI -/-", 0xFBu, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        CP_d8("CP d8", 0xFEu, 8, 2u, flagAffected = "z1hc") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RST_38H("RST 38H", 0xFFu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RLC_B("RLC B", 0xCB00u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlcA = register.b.rlc
                register.b = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
                return cycles
            }
        },
        RLC_C("RLC C", 0xCB01u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlcA = register.c.rlc
                register.c = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
                return cycles
            }
        },
        RLC_D("RLC D", 0xCB02u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlcA = register.d.rlc
                register.d = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
                return cycles
            }
        },
        RLC_E("RLC E", 0xCB03u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlcA = register.e.rlc
                register.e = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
                return cycles
            }
        },
        RLC_H("RLC H", 0xCB04u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlcA = register.h.rlc
                register.h = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
                return cycles
            }
        },
        RLC_L("RLC L", 0xCB05u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlcA = register.l.rlc
                register.l = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
                return cycles
            }
        },
        RLC_HL("RLC (HL)", 0xCB06u, 16, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlcA = mmu.readByte(register.hl).rlc
                mmu.writeByte(register.hl, rlcA.result)
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
                return cycles
            }
        },
        RLC_A("RLC A", 0xCB07u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val rlcA = register.a.rlc
                register.a = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
                return cycles
            }
        },

        RRC_B("RRC B", 0xCB08u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrcB = register.b.rrc
                register.b = rrcB.result
                register.fr.z = rrcB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcB.carry.asBoolean
                return cycles
            }
        },
        RRC_C("RRC C", 0xCB09u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrcC = register.c.rrc
                register.c = rrcC.result
                register.fr.z = rrcC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcC.carry.asBoolean
                return cycles
            }
        },
        RRC_D("RRC D", 0xCB0Au, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrcD = register.d.rrc
                register.d = rrcD.result
                register.fr.z = rrcD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcD.carry.asBoolean
                return cycles
            }
        },
        RRC_E("RRC E", 0xCB0Bu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrcE = register.e.rrc
                register.e = rrcE.result
                register.fr.z = rrcE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcE.carry.asBoolean
                return cycles
            }
        },
        RRC_H("RRC H", 0xCB0Cu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrcH = register.h.rrc
                register.h = rrcH.result
                register.fr.z = rrcH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcH.carry.asBoolean
                return cycles
            }
        },
        RRC_L("RRC L", 0xCB0Du, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrcL = register.l.rrc
                register.l = rrcL.result
                register.fr.z = rrcL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcL.carry.asBoolean
                return cycles
            }
        },
        RRC_HL("RRC (HL)", 0xCB0Eu, 16, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrcHL = mmu.readByte(register.hl).rrc
                mmu.writeByte(register.hl, rrcHL.result)
                register.fr.z = rrcHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcHL.carry.asBoolean
                return cycles
            }
        },
        RRC_A("RRC A", 0xCB0Fu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val rrcA = register.a.rrc
                register.a = rrcA.result
                register.fr.z = rrcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcA.carry.asBoolean
                return cycles
            }
        },

        RL_B("RL B", 0xCB10u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlB = register.b.rl(register.fr.c.asUByte)
                register.b = rlB.result
                register.fr.z = rlB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlB.carry.asBoolean
                return cycles
            }
        },
        RL_C("RL C", 0xCB11u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlC = register.c.rl(register.fr.c.asUByte)
                register.c = rlC.result
                register.fr.z = rlC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlC.carry.asBoolean
                return cycles
            }
        },
        RL_D("RL D", 0xCB12u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlD = register.d.rl(register.fr.c.asUByte)
                register.d = rlD.result
                register.fr.z = rlD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlD.carry.asBoolean
                return cycles
            }
        },
        RL_E("RL E", 0xCB13u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlE = register.e.rl(register.fr.c.asUByte)
                register.e = rlE.result
                register.fr.z = rlE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlE.carry.asBoolean
                return cycles
            }
        },
        RL_H("RL H", 0xCB14u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlH = register.h.rl(register.fr.c.asUByte)
                register.h = rlH.result
                register.fr.z = rlH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlH.carry.asBoolean
                return cycles
            }
        },
        RL_L("RL L", 0xCB15u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlL = register.l.rl(register.fr.c.asUByte)
                register.l = rlL.result
                register.fr.z = rlL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlL.carry.asBoolean
                return cycles
            }
        },
        RL_HL("RL (HL)", 0xCB16u, 16, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rlHL = mmu.readByte(register.hl).rl(register.fr.c.asUByte)
                mmu.writeByte(register.hl, rlHL.carry)
                register.fr.z = rlHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlHL.carry.asBoolean
                return cycles
            }
        },
        RL_A("RL A", 0xCB17u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val rlA = register.a.rl(register.fr.c.asUByte)
                register.a = rlA.result
                register.fr.z = rlA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlA.carry.asBoolean
                return cycles
            }
        },

        RR_B("RR B", 0xCB18u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrB = register.b.rr(register.fr.c.asUByte)
                register.b = rrB.result
                register.fr.z = rrB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrB.carry.asBoolean
                return cycles
            }
        },
        RR_C("RR C", 0xCB19u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrC = register.c.rr(register.fr.c.asUByte)
                register.c = rrC.result
                register.fr.z = rrC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrC.carry.asBoolean
                return cycles
            }
        },
        RR_D("RR D", 0xCB1Au, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrD = register.d.rr(register.fr.c.asUByte)
                register.d = rrD.result
                register.fr.z = rrD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrD.carry.asBoolean
                return cycles
            }
        },
        RR_E("RR E", 0xCB1Bu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrE = register.e.rr(register.fr.c.asUByte)
                register.e = rrE.result
                register.fr.z = rrE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrE.carry.asBoolean
                return cycles
            }
        },
        RR_H("RR H", 0xCB1Cu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrH = register.h.rr(register.fr.c.asUByte)
                register.h = rrH.result
                register.fr.z = rrH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrH.carry.asBoolean
                return cycles
            }
        },
        RR_L("RR L", 0xCB1Du, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrL = register.l.rr(register.fr.c.asUByte)
                register.l = rrL.result
                register.fr.z = rrL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrL.carry.asBoolean
                return cycles
            }
        },
        RR_HL("RR (HL)", 0xCB1Eu, 16, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val rrHL = mmu.readByte(register.hl).rr(register.fr.c.asUByte)
                mmu.writeByte(register.hl, rrHL.result)
                register.fr.z = rrHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrHL.carry.asBoolean
                return cycles
            }
        },
        RR_A("RR A", 0xCB1Fu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                register.incPC()
                val rrA = register.a.rr(register.fr.c.asUByte)
                register.a = rrA.result
                register.fr.z = rrA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrA.carry.asBoolean
                return cycles
            }
        },

        SLA_B("SLA B", 0xCB20u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val slaB = register.b.sla
                register.b = slaB.result
                register.fr.z = slaB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaB.carry.asBoolean
                return cycles
            }
        },
        SLA_C("SLA C", 0xCB21u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val slaC = register.c.sla
                register.c = slaC.result
                register.fr.z = slaC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaC.carry.asBoolean
                return cycles
            }
        },
        SLA_D("SLA D", 0xCB22u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val slaD = register.d.sla
                register.d = slaD.result
                register.fr.z = slaD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaD.carry.asBoolean
                return cycles
            }
        },
        SLA_E("SLA E", 0xCB23u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val slaE = register.e.sla
                register.e = slaE.result
                register.fr.z = slaE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaE.carry.asBoolean
                return cycles
            }
        },
        SLA_H("SLA H", 0xCB24u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val slaH = register.h.sla
                register.h = slaH.result
                register.fr.z = slaH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaH.carry.asBoolean
                return cycles
            }
        },
        SLA_L("SLA L", 0xCB25u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val slaL = register.l.sla
                register.l = slaL.result
                register.fr.z = slaL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaL.carry.asBoolean
                return cycles
            }
        },
        SLA_HL("SLA (HL)", 0xCB26u, 16, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val slaHL = mmu.readByte(register.hl).sla
                mmu.writeByte(register.hl, slaHL.result)
                register.fr.z = slaHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaHL.carry.asBoolean
                return cycles
            }
        },
        SLA_A("SLA A", 0xCB27u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val slaA = register.a.sla
                register.a = slaA.result
                register.fr.z = slaA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaA.carry.asBoolean
                return cycles
            }
        },

        SRA_B("SRA B", 0xCB28u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sraB = register.b.sra
                register.b = sraB.result
                register.fr.z = sraB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraB.carry.asBoolean
                return cycles
            }
        },
        SRA_C("SRA C", 0xCB29u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sraC = register.c.sra
                register.c = sraC.result
                register.fr.z = sraC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraC.carry.asBoolean
                return cycles
            }
        },
        SRA_D("SRA D", 0xCB2Au, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sraD = register.d.sra
                register.d = sraD.result
                register.fr.z = sraD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraD.carry.asBoolean
                return cycles
            }
        },
        SRA_E("SRA E", 0xCB2Bu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sraE = register.e.sra
                register.e = sraE.result
                register.fr.z = sraE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraE.carry.asBoolean
                return cycles
            }
        },
        SRA_H("SRA H", 0xCB2Cu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sraH = register.h.sra
                register.h = sraH.result
                register.fr.z = sraH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraH.carry.asBoolean
                return cycles
            }
        },
        SRA_L("SRA L", 0xCB2Du, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sraL = register.l.sra
                register.l = sraL.result
                register.fr.z = sraL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraL.carry.asBoolean
                return cycles
            }
        },
        SRA_HL("SRA (HL)", 0xCB2Eu, 16, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sraHL = mmu.readByte(register.hl).sra
                mmu.writeByte(register.hl, sraHL.result)
                register.fr.z = sraHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraHL.carry.asBoolean
                return cycles
            }
        },
        SRA_A("SRA A", 0xCB2Fu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                val sraA = register.a.sra
                register.a = sraA.result
                register.fr.z = sraA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraA.carry.asBoolean
                return cycles
            }
        },

        SWAP_B("SWAP B", 0xCB30u, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SWAP_C("SWAP C", 0xCB31u, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SWAP_D("SWAP D", 0xCB32u, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SWAP_E("SWAP E", 0xCB33u, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SWAP_H("SWAP H", 0xCB34u, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SWAP_L("SWAP L", 0xCB35u, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SWAP_HL("SWAP (HL)", 0xCB36u, 16, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SWAP_A("SWAP A", 0xCB37u, 8, flagAffected = "z000") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        SRL_B("SRL B", 0xCB38u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SRL_C("SRL C", 0xCB39u, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SRL_D("SRL D", 0xCB3Au, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SRL_E("SRL E", 0xCB3Bu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SRL_H("SRL H", 0xCB3Cu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SRL_L("SRL L", 0xCB3Du, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SRL_HL("SRL (HL)", 0xCB3Eu, 16, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SRL_A("SRL A", 0xCB3Fu, 8, flagAffected = "z00c") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        BIT_0_B("BIT 0, B", 0xCB40u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_0_C("BIT 0, C", 0xCB41u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_0_D("BIT 0, D", 0xCB42u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_0_E("BIT 0, E", 0xCB43u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_0_H("BIT 0, H", 0xCB44u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_0_L("BIT 0, L", 0xCB45u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_0_HL("BIT 0, (HL)", 0xCB46u, 16, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_0_A("BIT 0, A", 0xCB47u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        BIT_1_B("BIT 1, B", 0xCB48u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_1_C("BIT 1, C", 0xCB49u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_1_D("BIT 1, D", 0xCB4Au, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_1_E("BIT 1, E", 0xCB4Bu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_1_H("BIT 1, H", 0xCB4Cu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_1_L("BIT 1, L", 0xCB4Du, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_1_HL("BIT 1, (HL)", 0xCB4Eu, 16, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_1_A("BIT 1, A", 0xCB4Fu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        BIT_2_B("BIT 2, B", 0xCB50u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_2_C("BIT 2, C", 0xCB51u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_2_D("BIT 2, D", 0xCB52u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_2_E("BIT 2, E", 0xCB53u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_2_H("BIT 2, H", 0xCB54u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_2_L("BIT 2, L", 0xCB55u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_2_HL("BIT 2, (HL)", 0xCB56u, 16, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_2_A("BIT 2, A", 0xCB57u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        BIT_3_B("BIT 3, B", 0xCB58u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_3_C("BIT 3, C", 0xCB59u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_3_D("BIT 3, D", 0xCB5Au, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_3_E("BIT 3, E", 0xCB5Bu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_3_H("BIT 3, H", 0xCB5Cu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_3_L("BIT 3, L", 0xCB5Du, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_3_HL("BIT 3, (HL)", 0xCB5Eu, 16, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_3_A("BIT 3, A", 0xCB5Fu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        BIT_4_B("BIT 4, B", 0xCB60u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_4_C("BIT 4, C", 0xCB61u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_4_D("BIT 4, D", 0xCB62u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_4_E("BIT 4, E", 0xCB63u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_4_H("BIT 4, H", 0xCB64u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_4_L("BIT 4, L", 0xCB65u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_4_HL("BIT 4, (HL)", 0xCB66u, 16, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_4_A("BIT 4, A", 0xCB67u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        BIT_5_B("BIT 5, B", 0xCB68u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_5_C("BIT 5, C", 0xCB69u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_5_D("BIT 5, D", 0xCB6Au, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_5_E("BIT 5, E", 0xCB6Bu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_5_H("BIT 5, H", 0xCB6Cu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_5_L("BIT 5, L", 0xCB6Du, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_5_HL("BIT 5, (HL)", 0xCB6Eu, 16, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_5_A("BIT 5, A", 0xCB6Fu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },


        BIT_6_B("BIT 6, B", 0xCB70u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_6_C("BIT 6, C", 0xCB71u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_6_D("BIT 6, D", 0xCB72u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_6_E("BIT 6, E", 0xCB73u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_6_H("BIT 6, H", 0xCB74u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_6_L("BIT 6, L", 0xCB75u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_6_HL("BIT 6, (HL)", 0xCB76u, 16, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_6_A("BIT 6, A", 0xCB77u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        BIT_7_B("BIT 7, B", 0xCB78u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_7_C("BIT 7, C", 0xCB79u, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_7_D("BIT 7, D", 0xCB7Au, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_7_E("BIT 7, E", 0xCB7Bu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_7_H("BIT 7, H", 0xCB7Cu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_7_L("BIT 7, L", 0xCB7Du, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_7_HL("BIT 7, (HL)", 0xCB7Eu, 16, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        BIT_7_A("BIT 7, A", 0xCB7Fu, 8, flagAffected = "z01-") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },


        RES_0_B("RES 0, B", 0xCB80u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_0_C("RES 0, C", 0xCB81u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_0_D("RES 0, D", 0xCB82u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_0_E("RES 0, E", 0xCB83u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_0_H("RES 0, H", 0xCB84u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_0_L("RES 0, L", 0xCB85u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_0_HL("RES 0, (HL)", 0xCB86u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_0_A("RES 0, A", 0xCB87u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RES_1_B("RES 1, B", 0xCB88u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_1_C("RES 1, C", 0xCB89u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_1_D("RES 1, D", 0xCB8Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_1_E("RES 1, E", 0xCB8Bu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_1_H("RES 1, H", 0xCB8Cu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_1_L("RES 1, L", 0xCB8Du, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_1_HL("RES 1, (HL)", 0xCB8Eu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_1_A("RES 1, A", 0xCB8Fu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RES_2_B("RES 2, B", 0xCB90u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_2_C("RES 2, C", 0xCB91u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_2_D("RES 2, D", 0xCB92u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_2_E("RES 2, E", 0xCB93u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_2_H("RES 2, H", 0xCB94u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_2_L("RES 2, L", 0xCB95u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_2_HL("RES 2, (HL)", 0xCB96u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_2_A("RES 2, A", 0xCB97u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RES_3_B("RES 3, B", 0xCB98u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_3_C("RES 3, C", 0xCB99u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_3_D("RES 3, D", 0xCB9Au, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_3_E("RES 3, E", 0xCB9Bu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_3_H("RES 3, H", 0xCB9Cu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_3_L("RES 3, L", 0xCB9Du, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_3_HL("RES 3, (HL)", 0xCB9Eu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_3_A("RES 3, A", 0xCB9Fu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RES_4_B("RES 4, B", 0xCBA0u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_4_C("RES 4, C", 0xCBA1u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_4_D("RES 4, D", 0xCBA2u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_4_E("RES 4, E", 0xCBA3u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_4_H("RES 4, H", 0xCBA4u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_4_L("RES 4, L", 0xCBA5u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_4_HL("RES 4, (HL)", 0xCBA6u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_4_A("RES 4, A", 0xCBA7u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RES_5_B("RES 5, B", 0xCBA8u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_5_C("RES 5, C", 0xCBA9u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_5_D("RES 5, D", 0xCBAAu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_5_E("RES 5, E", 0xCBABu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_5_H("RES 5, H", 0xCBACu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_5_L("RES 5, L", 0xCBADu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_5_HL("RES 5, (HL)", 0xCBAEu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_5_A("RES 5, A", 0xCBAFu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RES_6_B("RES 6, B", 0xCBB0u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_6_C("RES 6, C", 0xCBB1u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_6_D("RES 6, D", 0xCBB2u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_6_E("RES 6, E", 0xCBB3u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_6_H("RES 6, H", 0xCBB4u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_6_L("RES 6, L", 0xCBB5u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_6_HL("RES 6, (HL)", 0xCBB6u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_6_A("RES 6, A", 0xCBB7u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        RES_7_B("RES 7, B", 0xCBB8u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_7_C("RES 7, C", 0xCBB9u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_7_D("RES 7, D", 0xCBBAu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_7_E("RES 7, E", 0xCBBBu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_7_H("RES 7, H", 0xCBBCu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_7_L("RES 7, L", 0xCBBDu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_7_HL("RES 7, (HL)", 0xCBBEu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        RES_7_A("RES 7, A", 0xCBBFu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },


        SET_0_B("SET 0, B", 0xCBC0u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_0_C("SET 0, C", 0xCBC1u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_0_D("SET 0, D", 0xCBC2u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_0_E("SET 0, E", 0xCBC3u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_0_H("SET 0, H", 0xCBC4u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_0_L("SET 0, L", 0xCBC5u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_0_HL("SET 0, (HL)", 0xCBC6u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_0_A("SET 0, A", 0xCBC7u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        SET_1_B("SET 1, B", 0xCBC8u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_1_C("SET 1, C", 0xCBC9u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_1_D("SET 1, D", 0xCBCAu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_1_E("SET 1, E", 0xCBCBu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_1_H("SET 1, H", 0xCBCCu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_1_L("SET 1, L", 0xCBCDu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_1_HL("SET 1, (HL)", 0xCBCEu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_1_A("SET 1, A", 0xCBCFu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        SET_2_B("SET 2, B", 0xCBD0u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_2_C("SET 2, C", 0xCBD1u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_2_D("SET 2, D", 0xCBD2u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_2_E("SET 2, E", 0xCBD3u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_2_H("SET 2, H", 0xCBD4u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_2_L("SET 2, L", 0xCBD5u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_2_HL("SET 2, (HL)", 0xCBD6u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_2_A("SET 2, A", 0xCBD7u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        SET_3_B("SET 3, B", 0xCBD8u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_3_C("SET 3, C", 0xCBD9u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_3_D("SET 3, D", 0xCBDAu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_3_E("SET 3, E", 0xCBDBu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_3_H("SET 3, H", 0xCBDCu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_3_L("SET 3, L", 0xCBDDu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_3_HL("SET 3, (HL)", 0xCBDEu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_3_A("SET 3, A", 0xCBDFu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        SET_4_B("SET 4, B", 0xCBE0u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_4_C("SET 4, C", 0xCBE1u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_4_D("SET 4, D", 0xCBE2u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_4_E("SET 4, E", 0xCBE3u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_4_H("SET 4, H", 0xCBE4u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_4_L("SET 4, L", 0xCBE5u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_4_HL("SET 4, (HL)", 0xCBE6u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_4_A("SET 4, A", 0xCBE7u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        SET_5_B("SET 5, B", 0xCBE8u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_5_C("SET 5, C", 0xCBE9u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_5_D("SET 5, D", 0xCBEAu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_5_E("SET 5, E", 0xCBEBu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_5_H("SET 5, H", 0xCBECu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_5_L("SET 5, L", 0xCBEDu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_5_HL("SET 5, (HL)", 0xCBEEu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_5_A("SET 5, A", 0xCBEFu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        SET_6_B("SET 6, B", 0xCBF0u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_6_C("SET 6, C", 0xCBF1u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_6_D("SET 6, D", 0xCBF2u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_6_E("SET 6, E", 0xCBF3u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_6_H("SET 6, H", 0xCBF4u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_6_L("SET 6, L", 0xCBF5u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_6_HL("SET 6, (HL)", 0xCBF6u, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_6_A("SET 6, A", 0xCBF7u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        SET_7_B("SET 7, B", 0xCBF8u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_7_C("SET 7, C", 0xCBF9u, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_7_D("SET 7, D", 0xCBFAu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_7_E("SET 7, E", 0xCBFBu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_7_H("SET 7, H", 0xCBFCu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_7_L("SET 7, L", 0xCBFDu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_7_HL("SET 7, (HL)", 0xCBFEu, 16, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },
        SET_7_A("SET 7, A", 0xCBFFu, 8, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

        STOP("STOP -/-", 0x1000u, 4, flagAffected = "----") {
            override fun execute(mmu: MMU, register: CPURegister): Int {
                TODO("Not yet implemented")
                return cycles
            }
        },

    }

    init {
        OPS.values().forEach {
            if (!containsKey(it.opcode)) {
                put(it.opcode, it)
            }
        }
    }

}

interface Execute {
    fun execute(mmu: MMU, register: CPURegister): Int = cycles
    val cycles: Int
    val length: UShort
    val opcode: UShort
    val mnemonic: String
    val flagAffected: String
}
