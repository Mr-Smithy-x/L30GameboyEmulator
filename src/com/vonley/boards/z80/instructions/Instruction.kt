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
    //3.3.1.1
    enum class OPS(
        override val mnemonic: String,
        override val opcode: UShort,
        override val cycles: Int,
        override val length: UShort = 1u
    ) : Execute {

        NOP("NOP -/-", 0x00u, 4, 1u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                //register.pc = register.pc.inc()
            }
        },
        LD_BC_d16("LD BC, d16", 0x01u, 12, 3u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.bc = mmu.readShort(opcode.inc())
            }
        },
        LD_BC_A("LD (BC),A", 0x02u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                //TODO Is this valid?
                mmu.writeByte(register.bc, register.a)
            }
        },
        INC_BC("INC BC", 0x03u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.bc = register.bc.inc()
            }
        },

        //z0h-
        INC_B("INC B", 0x04u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = register.b.inc()
                register.fr.z = (register.b == 0x00u.toUByte())
                register.fr.n = true
                TODO("Something to h flag")
            }
        },

        //z1h-
        DEC_B("DEC B", 0x05u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b.dec()
                register.fr.z = (register.b == 0x00u.toUByte())
                register.fr.n = false
                TODO("Something to h flag")
            }
        },
        LD_B_d8("LD B,d8", 0x06u, 8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = mmu.readByte(opcode.inc())
            }
        },
        RLCA("RLCA -/-", 0x07u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                RLC_A.execute(mmu, register)
            }
        },
        LD_a16_SP("LD (a16), SP", 0x08u, 20, 3u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeShort(mmu.readShort(opcode.inc()), register.sp)
            }
        },

        //-0hc
        ADD_HL_BC("ADD HL, BC", 0x09u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.fr.n = true
                TODO("Do something to h & c flags")
            }
        },
        LD_A_BC("LD A,BC", 0x0Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte(register.bc)
            }
        },
        DEC_BC("DEC BC", 0x0Bu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        //z0h-
        INC_C("INC C", 0x0Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = register.c.inc()
                register.fr.z = (register.c == 0x00u.toUByte())
                register.fr.n = false
                TODO("Do something to z & h flags")
            }
        },

        //z1h-
        DEC_C("DEC C", 0x0Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.fr.n = true
                TODO("Do something to z & h flags")
            }
        },
        LD_C_d8("LD C,d8", 0x0Eu, 8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = mmu.readByte((opcode).inc())
            }
        },

        //000c
        RRCA("RRCA -/-", 0x0Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                RRC_A.execute(mmu, register)
            }
        },

        RSTOP("STOP", 0x10u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_DE_d16("LD DE, d16", 0x11u, 12, 3u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.de = mmu.readShort(opcode)
            }
        },
        LD_DE_A("LD DE,A", 0x12u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.de, register.a)
            }
        },
        INC_DE("INC DE", 0x13u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.de = register.de.inc()
            }
        },
        INC_D("INC D", 0x14u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = register.d.inc()
                register.fr.z = (register.d == 0x00u.toUByte())
                register.fr.n = false
                TODO("h flag")
            }
        },
        DEC_D("DEC D", 0x15u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_D_d8("LD D,d8", 0x16u, 8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = mmu.readByte(opcode.inc())
            }
        },
        RLA("RLA -/-", 0x17u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                RL_A.execute(mmu, register)
            }
        },
        JR_r8("JR r8", 0x18u, 12, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADD_HL_DE("ADD HL,DE", 0x19u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_A_DE("LD A,DE", 0x1Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte(register.de)
            }
        },
        DEC_DE("DEC DE", 0x1Bu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        INC_E("INC E", 0x1Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = register.e.inc()
                register.fr.z = (register.e == 0x00u.toUByte())
                register.fr.n = false
                TODO("h flag")
            }
        },
        DEC_E("DEC E", 0x1Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_E_d8("LD E,d8", 0x1Eu, 8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = mmu.readByte(opcode.inc())
            }
        },
        RRA("RRA -/-", 0x1Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                RR_A.execute(mmu, register)
            }
        },

        JR_NZ_r8("JR NZ,r8", 0x20u, 12/8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_d16("LD HL, d16", 0x21u, 12, 3u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.hl = mmu.readShort(opcode.inc())
            }
        },
        LD_HLI_A("LD (HLI), A", 0x22u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDI_HL_A.execute(mmu, register)
            }
        },
        LD_HLp_A("LD (HL+), A", 0x22u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDI_HL_A.execute(mmu, register)
            }
        },
        LDI_HL_A("LDI (HL), A", 0x22u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.a)
                register.incHL()
            }
        },
        INC_HL("INC HL", 0x23u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.hl = register.hl.inc()
            }
        },
        INC_H("INC H", 0x24u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = register.h.inc()
                register.fr.z = (register.h == 0x00u.toUByte())
                register.fr.n = false
                TODO("h flag")
            }
        },
        DEC_H("DEC H", 0x25u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_H_d8("LD H,d8", 0x26u, 8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = mmu.readByte(opcode.inc())
            }
        },
        DAA("DAA -/-", 0x27u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        JR_Z_r8("JR Z,r8", 0x28u, 12/8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADD_HL_HL("ADD HL, HL", 0x29u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_A_HLI("LD A,(HLI)", 0x2Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDI_A_HL.execute(mmu, register)
            }
        },
        LD_A_HLp("LD A,(HL+)", 0x2Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDI_A_HL.execute(mmu, register)
            }
        },
        LDI_A_HL("LDI A,(HL)", 0x2Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte(register.hl)
                register.incHL()
            }
        },
        DEC_HL("DEC HL", 0x2Bu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.hl = register.hl.dec()
            }
        },
        INC_L("INC L", 0x2Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = register.l.inc()
                register.fr.z = (register.l == 0x00u.toUByte())
                register.fr.n = false
                TODO("h flag")
            }
        },
        DEC_L("DEC L", 0x2Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_L_d8("LD L,d8", 0x2Eu, 8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = mmu.readByte(opcode.inc())
            }
        },
        CPL("CPL -/-", 0x2Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.inv()
            }
        },

        JR_NC_r8("JR NC,r8", 0x30u, 12/8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_SP_d16("LD SP, d16", 0x31u, 12, 3u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.sp = mmu.readShort(opcode.inc())
            }
        },
        LD_HLD_A("LD (HLD),A", 0x32u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDD_HL_A.execute(mmu, register)
            }
        },
        LD_HLm_A("LD (HL-),A", 0x32u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDD_HL_A.execute(mmu, register)
            }
        },
        LDD_HL_A("LDD (HL),A", 0x32u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.a)
                register.decHL()
            }
        },
        INC_SP("INC SP", 0x33u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.sp = register.sp.inc()
            }
        },
        INC_HL_a16("INC HL", 0x34u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        DEC_HL_a16("DEC (HL)", 0x35u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_d8("LD HL,d8", 0x36u, 12, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, mmu.readByte(opcode.inc()))
            }
        },
        SCF("SCF -/-", 0x37u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        JR_C_r8("JR C,r8", 0x38u, 12/8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADD_HL_SP("ADD HL, SP", 0x39u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_A_HLD("LD A,(HLD)", 0x3Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDD_A_HL.execute(mmu, register)
            }
        },
        LD_A_HLm("LD A,(HL-)", 0x3Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDD_A_HL.execute(mmu, register)
            }
        },
        LDD_A_HL("LDD A,(HL)", 0x3Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte(register.hl)
                register.decHL()
            }
        },
        DEC_SP("DEC SP", 0x3Bu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        //z0h-
        INC_A("INC A", 0x3Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.inc()
                register.fr.z = (register.a == 0x00u.toUByte())
                register.fr.n = false
                TODO("h flag")
            }
        },

        //z1h-
        DEC_A("DEC A", 0x3Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_A_d8("LD A, d8", 0x3Eu, 8, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte(opcode.inc())
            }
        },

        //-00c
        CCF("CCF -/-", 0x3Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        LD_B_B("LD B,B", 0x40u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = register.b
            }
        },
        LD_B_C("LD B,C", 0x41u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = register.c
            }
        },
        LD_B_D("LD B,D", 0x42u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = register.d
            }
        },
        LD_B_E("LD B,E", 0x43u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = register.e
            }
        },
        LD_B_H("LD B,H", 0x44u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = register.h
            }
        },
        LD_B_L("LD B,L", 0x45u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = register.l
            }
        },
        LD_B_HL("LD B,HL", 0x46u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = mmu.readByte(register.hl)
            }
        },
        LD_B_A("LD B,A", 0x47u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.b = register.a
            }
        },

        LD_C_B("LD C,B", 0x48u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = register.b
            }
        },
        LD_C_C("LD C,C", 0x49u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = register.c
            }
        },
        LD_C_D("LD C,D", 0x4Au, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = register.d
            }
        },
        LD_C_E("LD C,E", 0x4Bu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = register.e
            }
        },
        LD_C_H("LD C,H", 0x4Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = register.h
            }
        },
        LD_C_L("LD C,L", 0x4Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = register.l
            }
        },
        LD_C_HL("LD C,HL", 0x4Eu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = mmu.readByte(register.hl)
            }
        },
        LD_C_A("LD C,A", 0x4Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.c = register.a
            }
        },

        LD_D_B("LD D,B", 0x50u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = register.b
            }
        },
        LD_D_C("LD D,C", 0x51u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = register.c
            }
        },
        LD_D_D("LD D,D", 0x52u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = register.d
            }
        },
        LD_D_E("LD D,E", 0x53u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = register.e
            }
        },
        LD_D_H("LD D,H", 0x54u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = register.h
            }
        },
        LD_D_L("LD D,L", 0x55u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = register.l
            }
        },
        LD_D_HL("LD D,HL", 0x56u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.d = mmu.readByte(register.hl)
            }
        },
        LD_E_B("LD E,B", 0x58u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = register.b
            }
        },
        LD_D_A("LD D,A", 0x57u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        LD_E_C("LD E,C", 0x59u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = register.c
            }
        },
        LD_E_D("LD E,D", 0x5Au, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = register.d
            }
        },
        LD_E_E("LD E,E", 0x5Bu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = register.e
            }
        },
        LD_E_H("LD E,H", 0x5Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = register.h
            }
        },
        LD_E_L("LD E,L", 0x5Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = register.l
            }
        },
        LD_E_HL("LD E,HL", 0x5Eu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = mmu.readByte(register.hl)
            }
        },
        LD_E_A("LD E,A", 0x5Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.e = register.a
            }
        },

        LD_H_B("LD H,B", 0x60u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = register.b
            }
        },
        LD_H_C("LD H,C", 0x61u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = register.c
            }
        },
        LD_H_D("LD H,D", 0x62u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = register.d
            }
        },
        LD_H_E("LD H,E", 0x63u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = register.e
            }
        },
        LD_H_H("LD H,H", 0x64u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = register.h
            }
        },
        LD_H_L("LD H,L", 0x65u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = register.l
            }
        },
        LD_H_HL("LD H,HL", 0x66u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.h = mmu.readByte(register.hl)
            }
        },
        LD_H_A("LD H,A", 0x67u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        LD_L_B("LD L,B", 0x68u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = register.b
            }
        },
        LD_L_C("LD L,C", 0x69u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = register.c
            }
        },
        LD_L_D("LD L,D", 0x6Au, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = register.d
            }
        },
        LD_L_E("LD L,E", 0x6Bu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = register.e
            }
        },
        LD_L_H("LD L,H", 0x6Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = register.h
            }
        },
        LD_L_L("LD L,L", 0x6Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = register.l
            }
        },
        LD_L_HL("LD L,HL", 0x6Eu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = mmu.readByte(register.hl)
            }
        },
        LD_L_A("LD L,A", 0x6Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.l = register.a
            }
        },

        LD_HL_B("LD (HL),B", 0x70u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.b)
            }
        },
        LD_HL_C("LD (HL),C", 0x71u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.c)
            }
        },
        LD_HL_D("LD (HL),D", 0x72u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.d)
            }
        },
        LD_HL_E("LD (HL),E", 0x73u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.e)
            }
        },
        LD_HL_H("LD (HL),H", 0x74u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.h)
            }
        },
        LD_HL_L("LD (HL),L", 0x75u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.l)
            }
        },
        HALT("HALT -/-", 0x76u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_A("LD (HL),A", 0x77u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(register.hl, register.a)
            }
        },

        LD_A_B("LD A,B", 0x78u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.b
            }
        },
        LD_A_C("LD A,C", 0x79u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.c
            }
        },
        LD_A_D("LD A,D", 0x7Au, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.d
            }
        },
        LD_A_E("LD A,E", 0x7Bu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.e
            }
        },
        LD_A_H("LD A,H", 0x7Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.h
            }
        },
        LD_A_L("LD A,L", 0x7Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.l
            }
        },
        LD_A_HL("LD A,HL", 0x7Eu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte(register.hl)
            }
        },
        LD_A_A("LD A,A", 0x7Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a
            }
        },
        //H SET IF CARRY FROM BIT 3, C SET IF CARRY FROM BIT 7
        ADD_A_B("ADD A, B", 0x80u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(register.b)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
            }
        },
        ADD_A_C("ADD A, C", 0x81u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(register.c)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
            }
        },
        ADD_A_D("ADD A, D", 0x82u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(register.d)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
            }
        },
        ADD_A_E("ADD A, E", 0x83u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(register.e)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
            }
        },
        ADD_A_H("ADD A, H", 0x84u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(register.h)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
            }
        },
        ADD_A_L("ADD A, L", 0x85u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(register.l)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
            }
        },
        ADD_A_HL("ADD A, (HL)", 0x86u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(mmu.readByte(register.hl))
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
            }
        },
        ADD_A_A("ADD A, A", 0x87u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(register.a)
                register.a = plus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
                register.fr.n = false
            }
        },

        ADC_A_B("ADC A, B", 0x88u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_C("ADC A, C", 0x89u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_D("ADC A, D", 0x8Au, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_E("ADC A, E", 0x8Bu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_H("ADC A, H", 0x8Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_L("ADC A, L", 0x8Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_HL("ADC A, (HL)", 0x8Eu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_A("ADC A, A", 0x8Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        SUB_A_B("SUB A, B", 0x90u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val minus = register.a.minus(register.b)
                register.fr.n = true
                if(register.b > register.a){
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
            }
        },
        SUB_A_C("SUB A, C", 0x91u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val minus = register.a.minus(register.c)
                register.fr.n = true
                if(register.c > register.a){
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
            }
        },
        SUB_A_D("SUB A, D", 0x92u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val minus = register.a.minus(register.d)
                register.fr.n = true
                if(register.d > register.a){
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
            }
        },
        SUB_A_E("SUB A, E", 0x93u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val minus = register.a.minus(register.e)
                register.fr.n = true
                if(register.e > register.a){
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
            }
        },
        SUB_A_H("SUB A, H", 0x94u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val minus = register.a.minus(register.h)
                register.fr.n = true
                if(register.h > register.a){
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
            }
        },
        SUB_A_L("SUB A, L", 0x95u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val minus = register.a.minus(register.l)
                register.fr.n = true
                if(register.l > register.a){
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
            }
        },
        SUB_A_HL("SUB A, (HL)", 0x96u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                var hlAddr = mmu.readByte(register.hl)
                val minus = register.a.minus(hlAddr)
                register.fr.n = true
                if(hlAddr > register.a){
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
            }
        },
        SUB_A_A("SUB A, A", 0x97u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val minus = register.a.minus(register.a)
                register.fr.n = true
                if(register.a > register.a){
                    register.fr.c = true
                }
                register.a = minus.and(0xFFu).toUByte()
                register.fr.z = register.a.isZero()
            }
        },

        SBC_A_B("SBC A,B", 0x98u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_C("SBC A,C", 0x99u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_D("SBC A,D", 0x9Au, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_E("SBC A,E", 0x9Bu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_H("SBC A,H", 0x9Cu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_L("SBC A,L", 0x9Du, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_HL("SBC A, (HL)", 0x9Eu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_A("SBC A,A", 0x9Fu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        AND_A_B("AND A, B", 0xA0u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(register.b)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
            }
        },
        AND_A_C("AND A, C", 0xA1u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(register.c)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
            }
        },
        AND_A_D("AND A, D", 0xA2u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(register.d)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
            }
        },
        AND_A_E("AND A, E", 0xA3u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(register.e)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
            }
        },
        AND_A_H("AND A, H", 0xA4u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(register.h)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
            }
        },
        AND_A_L("AND A, L", 0xA5u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(register.l)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
            }
        },
        AND_A_HL("AND A, (HL)", 0xA6u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(mmu.readByte(register.hl))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
            }
        },
        AND_A_A("AND A, A", 0xA7u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(register.a)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = true
                register.fr.c = false
            }
        },
        XOR_A_B("XOR A, B", 0xA8u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(register.b)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        XOR_A_C("XOR A, C", 0xA9u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(register.c)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        XOR_A_D("XOR A, D", 0xAAu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(register.d)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        XOR_A_E("XOR A, E", 0xABu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(register.e)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        XOR_A_H("XOR A, H", 0xACu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(register.h)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        XOR_A_L("XOR A, L", 0xADu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(register.l)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        XOR_A_HL("XOR A, (HL)", 0xAEu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(mmu.readByte(register.hl))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        XOR_A_A("XOR A, A", 0xAFu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(register.a)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        OR_A_B("OR A, B", 0xB0u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(register.b)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        OR_A_C("OR A, C", 0xB1u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(register.c)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        OR_A_D("OR A, D", 0xB2u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(register.d)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        OR_A_E("OR A, E", 0xB3u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(register.e)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        OR_A_H("OR A, H", 0xB4u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(register.h)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        OR_A_L("OR A, L", 0xB5u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(register.l)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        OR_A_HL("OR A, (HL)", 0xB6u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(mmu.readByte(register.hl))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        OR_A_A("OR A, A", 0xB7u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(register.a)
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        CP_B("CP B", 0xB8u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CP_C("CP C", 0xB9u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CP_D("CP D", 0xBAu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CP_E("CP E", 0xBBu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CP_H("CP H", 0xBCu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CP_L("CP L", 0xBDu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CP_HL("CP HL", 0xBEu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CP_A("CP A", 0xBFu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        RET_NZ("RET NZ", 0xC0u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        POP_BC("POP BC", 0xC1u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.bc = mmu.readShort(register.sp)
                register.addSP(0x2u)
            }
        },
        JP_NZ_NN("JP NZ, nn", 0xC2u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        JP_NN("JP nn", 0xC3u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CALL_NZ_NUM("CALL NZ, nn", 0xC4u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        PUSH_BC("PUSH BC", 0xC5u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.bc)
            }
        },
        ADD_A_d8("ADD A, d8", 0xC6u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val plus = register.a.plus(mmu.readByte(opcode.inc()))
                register.a = plus.and(0xFFu).toUByte()


                register.fr.n = false
                register.fr.z = register.a.isZero()
            }
        },
        RST_00H("RST 00H", 0xC7u, 32) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RET_Z("RET Z", 0xC8u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RET("RET -/-", 0xC9u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        JP_Z_NN("JP Z, nn", 0xCAu, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CALL_N_NUM("CALL N, nn", 0xCCu, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CALL_NN("CALL nn", 0xCDu, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_NUM("ADC A, #", 0xCEu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RST_08H("RST 08H", 0xCFu, 32) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        RET_NC("RET NC", 0xD0u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        POP_DE("POP DE", 0xD1u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.de = mmu.readShort(register.sp)
                register.addSP(0x2u)
            }
        },
        JP_NC_NN("JP NC, nn", 0xD2u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CALL_NC_NUM("CALL NC, nn", 0xD4u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SUB_A_d8("SUB A, d8", 0xD6u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sub = register.a.minus(mmu.readByte(opcode.inc()))
                register.a = sub.and(0xFFu).toUByte()
            }
        },
        RST_10H("RST 10H", 0xD7u, 32) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RET_C("RET C", 0xD8u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RETI("RETI -/-", 0xD9u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        JP_C_NN("JP C, nn", 0xDAu, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CALL_C_NUM("CALL C, nn", 0xDCu, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        PUSH_DE("PUSH DE", 0xDEu, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.de)
            }
        },
        RST_18H("RST 18H", 0xDFu, 32) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        LDH_a8_A("LDH (a8), A // LD (FF00+a8), A", 0xE0u, 12, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val addr: UShort = 0xFF00u.plus(mmu.readByte(opcode.inc())).and(0xFFFFu).toUShort()
                mmu.writeByte(addr, register.a)
            }
        },
        POP_HL("POP HL", 0xE1u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.hl = mmu.readShort(register.sp)
                register.addSP(0x2u)
            }
        },
        LD_Ca_A("LD (C),A", 0xE2u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val addr: UShort = 0xFF00u.plus(register.c).and(0xFFFFu).toUShort()
                mmu.writeByte(addr, register.a)
            }
        },
        PUSH_HL("PUSH HL", 0xE5u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.hl)
            }
        },
        AND_A_d8("AND A, d8", 0xE6u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.and(mmu.readByte(opcode.inc()))
            }
        },
        RST_20H("RST 20H", 0xE7u, 32) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        //00hc
        ADD_SP_r8("ADD SP, r8", 0xE8u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        JP_HL("JP HL", 0xE9u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_a16_A("LD a16,A", 0xEAu, 16, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                mmu.writeByte(mmu.readShort(opcode.inc()), register.a)
            }
        },
        XOR_A_d8("XOR A, d8", 0xEEu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.xor(mmu.readByte(opcode.inc()))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        RST_28H("RST 28H", 0xEFu, 32) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        LDH_A_a8("LDH A,(a8) // LD A,(FF00+n)", 0xF0u, 12, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte(0xFF00u.plus(mmu.readByte(opcode.inc())).and(0xFFFFu).toUShort())
            }
        },
        POP_AF("POP AF", 0xF1u, 12) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.af = mmu.readShort(register.sp)
                register.addSP(0x2u)
            }
        },
        LD_A_Ca("LD A,(C)", 0xF2u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte((0xFF00u).plus(register.c).and(0xFFFFu).toUShort())
            }
        },
        DI("DI -/-", 0xF3u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        PUSH_AF("PUSH AF", 0xF5u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.addSP(0x2u)
                mmu.writeShort(register.sp, register.af)
            }
        },
        OR_A_d8("OR A, d8", 0xF6u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = register.a.or(mmu.readByte(opcode.inc()))
                register.fr.z = register.a.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = false
            }
        },
        RST_30H("RST 30H", 0xF7u, 32) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_SPr8("LD HL,SP+r8", 0xF8u, 16, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                LDHL_SP_r8.execute(mmu, register)
            }
        },
        LDHL_SP_r8("LD HL,SP+r8", 0xF8u, 16, 2u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sp_n = mmu.readByte(opcode.inc()).minus(128u)
                register.hl = register.sp.plus(sp_n).and(0xFFFFu).toUShort()
            }
        },
        LD_SP_HL("LD SP,HL", 0xF9u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.sp = register.hl
            }
        },
        LD_A_a16("LD A,a16", 0xFAu, 16, 3u) {
            override fun execute(mmu: MMU, register: CPURegister) {
                register.a = mmu.readByte(mmu.readShort(opcode.inc()))
            }
        },
        EI("EI -/-", 0xFBu, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        CP_d8("CP d8", 0xFEu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RST_38H("RST 38H", 0xFFu, 32) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        RLC_B("RLC B", 0xCB00u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlcA = register.b.rlc
                register.b = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
            }
        },
        RLC_C("RLC C", 0xCB01u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlcA = register.c.rlc
                register.c = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
            }
        },
        RLC_D("RLC D", 0xCB02u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlcA = register.d.rlc
                register.d = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
            }
        },
        RLC_E("RLC E", 0xCB03u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlcA = register.e.rlc
                register.e = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
            }
        },
        RLC_H("RLC H", 0xCB04u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlcA = register.h.rlc
                register.h = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
            }
        },
        RLC_L("RLC L", 0xCB05u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlcA = register.l.rlc
                register.l = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
            }
        },
        RLC_HL("RLC HL", 0xCB06u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlcA = mmu.readByte(register.hl).rlc
                mmu.writeByte(register.hl, rlcA.result)
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
            }
        },
        RLC_A("RLC A", 0xCB07u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlcA = register.a.rlc
                register.a = rlcA.result
                register.fr.z = rlcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlcA.carry.asBoolean
            }
        },

        RRC_B("RRC B", 0xCB08u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrcB = register.b.rrc
                register.b = rrcB.result
                register.fr.z = rrcB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcB.carry.asBoolean
            }
        },
        RRC_C("RRC C", 0xCB09u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrcC = register.c.rrc
                register.c = rrcC.result
                register.fr.z = rrcC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcC.carry.asBoolean
            }
        },
        RRC_D("RRC D", 0xCB0Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrcD = register.d.rrc
                register.d = rrcD.result
                register.fr.z = rrcD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcD.carry.asBoolean
            }
        },
        RRC_E("RRC E", 0xCB0Bu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrcE = register.e.rrc
                register.e = rrcE.result
                register.fr.z = rrcE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcE.carry.asBoolean
            }
        },
        RRC_H("RRC H", 0xCB0Cu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrcH = register.h.rrc
                register.h = rrcH.result
                register.fr.z = rrcH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcH.carry.asBoolean
            }
        },
        RRC_L("RRC L", 0xCB0Du, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrcL = register.l.rrc
                register.l = rrcL.result
                register.fr.z = rrcL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcL.carry.asBoolean
            }
        },
        RRC_HL("RRC HL", 0xCB0Eu, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrcHL = mmu.readByte(register.hl).rrc
                mmu.writeByte(register.hl, rrcHL.result)
                register.fr.z = rrcHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcHL.carry.asBoolean
            }
        },
        RRC_A("RRC A", 0xCB0Fu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrcA = register.a.rrc
                register.a = rrcA.result
                register.fr.z = rrcA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrcA.carry.asBoolean
            }
        },

        RL_B("RL B", 0xCB10u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlB = register.b.rl(register.fr.c.asUByte)
                register.b = rlB.result
                register.fr.z = rlB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlB.carry.asBoolean
            }
        },
        RL_C("RL C", 0xCB11u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlC = register.c.rl(register.fr.c.asUByte)
                register.c = rlC.result
                register.fr.z = rlC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlC.carry.asBoolean
            }
        },
        RL_D("RL D", 0xCB12u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlD = register.d.rl(register.fr.c.asUByte)
                register.d = rlD.result
                register.fr.z = rlD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlD.carry.asBoolean
            }
        },
        RL_E("RL E", 0xCB13u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlE = register.e.rl(register.fr.c.asUByte)
                register.e = rlE.result
                register.fr.z = rlE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlE.carry.asBoolean
            }
        },
        RL_H("RL H", 0xCB14u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlH = register.h.rl(register.fr.c.asUByte)
                register.h = rlH.result
                register.fr.z = rlH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlH.carry.asBoolean
            }
        },
        RL_L("RL L", 0xCB15u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlL = register.l.rl(register.fr.c.asUByte)
                register.l = rlL.result
                register.fr.z = rlL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlL.carry.asBoolean
            }
        },
        RL_HL("RL HL", 0xCB16u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlHL = mmu.readByte(register.hl).rl(register.fr.c.asUByte)
                mmu.writeByte(register.hl, rlHL.carry)
                register.fr.z = rlHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlHL.carry.asBoolean
            }
        },
        RL_A("RL A", 0xCB17u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rlA = register.a.rl(register.fr.c.asUByte)
                register.a = rlA.result
                register.fr.z = rlA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rlA.carry.asBoolean
            }
        },

        RR_B("RR B", 0xCB18u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrB = register.b.rr(register.fr.c.asUByte)
                register.b = rrB.result
                register.fr.z = rrB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrB.carry.asBoolean
            }
        },
        RR_C("RR C", 0xCB19u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrC = register.c.rr(register.fr.c.asUByte)
                register.c = rrC.result
                register.fr.z = rrC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrC.carry.asBoolean
            }
        },
        RR_D("RR D", 0xCB1Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrD = register.d.rr(register.fr.c.asUByte)
                register.d = rrD.result
                register.fr.z = rrD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrD.carry.asBoolean
            }
        },
        RR_E("RR E", 0xCB1Bu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrE = register.e.rr(register.fr.c.asUByte)
                register.e = rrE.result
                register.fr.z = rrE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrE.carry.asBoolean
            }
        },
        RR_H("RR H", 0xCB1Cu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrH = register.h.rr(register.fr.c.asUByte)
                register.h = rrH.result
                register.fr.z = rrH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrH.carry.asBoolean
            }
        },
        RR_L("RR L", 0xCB1Du, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrL = register.l.rr(register.fr.c.asUByte)
                register.l = rrL.result
                register.fr.z = rrL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrL.carry.asBoolean
            }
        },
        RR_HL("RR HL", 0xCB1Eu, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrHL = mmu.readByte(register.hl).rr(register.fr.c.asUByte)
                mmu.writeByte(register.hl, rrHL.result)
                register.fr.z = rrHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrHL.carry.asBoolean
            }
        },
        RR_A("RR A", 0xCB1Fu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val rrA = register.a.rr(register.fr.c.asUByte)
                register.a = rrA.result
                register.fr.z = rrA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = rrA.carry.asBoolean
            }
        },

        SLA_B("SLA B", 0xCB20u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val slaB = register.b.sla
                register.b = slaB.result
                register.fr.z = slaB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaB.carry.asBoolean
            }
        },
        SLA_C("SLA C", 0xCB21u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val slaC = register.c.sla
                register.c = slaC.result
                register.fr.z = slaC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaC.carry.asBoolean
            }
        },
        SLA_D("SLA D", 0xCB22u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val slaD = register.d.sla
                register.d = slaD.result
                register.fr.z = slaD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaD.carry.asBoolean
            }
        },
        SLA_E("SLA E", 0xCB23u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val slaE = register.e.sla
                register.e = slaE.result
                register.fr.z = slaE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaE.carry.asBoolean
            }
        },
        SLA_H("SLA H", 0xCB24u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val slaH = register.h.sla
                register.h = slaH.result
                register.fr.z = slaH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaH.carry.asBoolean
            }
        },
        SLA_L("SLA L", 0xCB25u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val slaL = register.l.sla
                register.l = slaL.result
                register.fr.z = slaL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaL.carry.asBoolean
            }
        },
        SLA_HL("SLA HL", 0xCB26u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val slaHL = mmu.readByte(register.hl).sla
                mmu.writeByte(register.hl, slaHL.result)
                register.fr.z = slaHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaHL.carry.asBoolean
            }
        },
        SLA_A("SLA A", 0xCB27u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val slaA = register.a.sla
                register.a = slaA.result
                register.fr.z = slaA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = slaA.carry.asBoolean
            }
        },

        SRA_B("SRA B", 0xCB28u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sraB = register.b.sra
                register.b = sraB.result
                register.fr.z = sraB.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraB.carry.asBoolean
            }
        },
        SRA_C("SRA C", 0xCB29u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sraC = register.c.sra
                register.c = sraC.result
                register.fr.z = sraC.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraC.carry.asBoolean
            }
        },
        SRA_D("SRA D", 0xCB2Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sraD = register.d.sra
                register.d = sraD.result
                register.fr.z = sraD.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraD.carry.asBoolean
            }
        },
        SRA_E("SRA E", 0xCB2Bu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sraE = register.e.sra
                register.e = sraE.result
                register.fr.z = sraE.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraE.carry.asBoolean
            }
        },
        SRA_H("SRA H", 0xCB2Cu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sraH = register.h.sra
                register.h = sraH.result
                register.fr.z = sraH.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraH.carry.asBoolean
            }
        },
        SRA_L("SRA L", 0xCB2Du, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sraL = register.l.sra
                register.l = sraL.result
                register.fr.z = sraL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraL.carry.asBoolean
            }
        },
        SRA_HL("SRA HL", 0xCB2Eu, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sraHL = mmu.readByte(register.hl).sra
                mmu.writeByte(register.hl, sraHL.result)
                register.fr.z = sraHL.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraHL.carry.asBoolean
            }
        },
        SRA_A("SRA A", 0xCB2Fu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                val sraA = register.a.sra
                register.a = sraA.result
                register.fr.z = sraA.result.isZero()
                register.fr.n = false
                register.fr.h = false
                register.fr.c = sraA.carry.asBoolean
            }
        },

        SWAP_B("SWAP B CB 37", 0xCB30u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SWAP_C("SWAP C CB 37", 0xCB31u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SWAP_D("SWAP D CB 37", 0xCB32u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SWAP_E("SWAP E CB 37", 0xCB33u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SWAP_H("SWAP H CB 37", 0xCB34u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SWAP_L("SWAP L CB 37", 0xCB35u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SWAP_HL("SWAP HL CB 37", 0xCB36u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SWAP_A("SWAP A CB 37", 0xCB37u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SRL_B("SRL B", 0xCB38u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SRL_C("SRL C", 0xCB39u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SRL_D("SRL D", 0xCB3Au, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SRL_E("SRL E", 0xCB3Bu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SRL_H("SRL H", 0xCB3Cu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SRL_L("SRL L", 0xCB3Du, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SRL_HL("SRL HL", 0xCB3Eu, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SRL_A("SRL A", 0xCB3Fu, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        BIT_B_B("BIT b, B", 0xCB40u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        BIT_B_C("BIT b, C", 0xCB41u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        BIT_B_D("BIT b, D", 0xCB42u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        BIT_B_E("BIT b, E", 0xCB43u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        BIT_B_H("BIT b, H", 0xCB44u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        BIT_B_L("BIT b, L", 0xCB45u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        BIT_B_HL("BIT b, HL", 0xCB46u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        BIT_B_A("BIT b, A", 0xCB47u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        RES_B_B("RES b, B", 0xCB80u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RES_B_C("RES b, C", 0xCB81u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RES_B_D("RES b, D", 0xCB82u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RES_B_E("RES b, E", 0xCB83u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RES_B_H("RES b, H", 0xCB84u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RES_B_L("RES b, L", 0xCB85u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RES_B_HL("RES b, HL", 0xCB86u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        RES_B_A("RES b, A", 0xCB87u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },


        SET_B_B("SET b, B", 0xCBC0u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SET_B_C("SET b, C", 0xCBC1u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SET_B_D("SET b, D", 0xCBC2u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SET_B_E("SET b, E", 0xCBC3u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SET_B_H("SET b, H", 0xCBC4u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SET_B_L("SET b, L", 0xCBC5u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SET_B_HL("SET b, HL", 0xCBC6u, 16) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },
        SET_B_A("SET b, A", 0xCBC7u, 8) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

        STOP("STOP -/-", 0x1000u, 4) {
            override fun execute(mmu: MMU, register: CPURegister) {
                TODO("Not yet implemented")
            }
        },

    }

    init {

        OPS.values().forEach {
            if (!containsKey(it.opcode)) {
                put(it.opcode, it)
            } else {
                //println("DUPLICATE WE FUCKED UP mnemonic: ${it.mnemonic} ${it.opcode.toHexString()}")
            }
        }
    }

}

interface Execute {
    fun execute(mmu: MMU, register: CPURegister)
    val cycles: Int
    val length: UShort
    val opcode: UShort
    val mnemonic: String
}
