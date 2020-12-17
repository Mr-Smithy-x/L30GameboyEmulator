package com.vonley.boards.z80.instructions

import com.vonley.boards.z80.CPU
import com.vonley.boards.z80.memory.MMU

//nn = signed byte
class Instruction {
    //3.3.1
    enum class Load8Bit(mnemonic: String, opcode: UInt, cycles: Int) : Execute {

        //1.) LD_N_N
        LD_B_N("LD B,n 06 8", 0x06u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                //cpu.cpuRegister.n
            }
        },
        LD_C_N("LD C,n 0E 8", 0x0Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                //cpu.cpuRegister.n
            }
        },
        LD_D_N("LD D,n 16 8", 0x16u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                //cpu.cpuRegister.n
            }
        },
        LD_E_N("LD E,n 1E 8", 0x1Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                //cpu.cpuRegister.n
            }
        },
        LD_H_N("LD H,n 26 8", 0x26u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                //cpu.cpuRegister.n
            }
        },
        LD_L_N("LD L,n 2E 8", 0x2Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                //cpu.cpuRegister.n
            }
        },

        //2.)
        LD_A_A("LD A,A 7F 4", 0x7Fu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_B("LD A,B 78 4", 0x78u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_C("LD A,C 79 4", 0x79u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_D("LD A,D 7A 4", 0x7Au, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_E("LD A,E 7B 4", 0x7Bu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_H("LD A,H 7C 4", 0x7Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_L("LD A,L 7D 4", 0x7Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_HL("LD A,HL 7E 4", 0x7Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_B_B("LD B,B 40 4", 0x40u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_B_C("LD B,C 41 4", 0x41u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_B_D("LD B,D 42 4", 0x42u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_B_E("LD B,E 43 4", 0x43u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_B_H("LD B,H 44 4", 0x44u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_B_L("LD B,L 45 4", 0x45u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_B_HL("LD B,HL 46 4", 0x46u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_C_B("LD C,B 48 4", 0x48u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_C_C("LD C,C 49 4", 0x49u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_C_D("LD C,D 4A 4", 0x4Au, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_C_E("LD C,E 4B 4", 0x4Bu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_C_H("LD C,H 4C 4", 0x4Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_C_L("LD C,L 4D 4", 0x4Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_C_HL("LD C,HL 4E 4", 0x4Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_D_B("LD D,B 50 4", 0x50u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_D_C("LD D,C 51 4", 0x51u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_D_D("LD D,D 52 4", 0x52u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_D_E("LD D,E 53 4", 0x53u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_D_H("LD D,H 54 4", 0x54u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_D_L("LD D,L 55 4", 0x55u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_D_HL("LD D,HL 56 4", 0x56u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_E_B("LD E,B 58 4", 0x58u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_E_C("LD E,C 59 4", 0x59u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_E_D("LD E,D 5A 4", 0x5Au, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_E_E("LD E,E 5B 4", 0x5Bu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_E_H("LD E,H 5C 4", 0x5Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_E_L("LD E,L 5D 4", 0x5Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_E_HL("LD E,HL 5E 4", 0x5Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_H_B("LD H,B 60 4", 0x60u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_H_C("LD H,C 61 4", 0x61u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_H_D("LD H,D 62 4", 0x62u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_H_E("LD H,E 63 4", 0x63u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_H_H("LD H,H 64 4", 0x64u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_H_L("LD H,L 65 4", 0x65u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_H_HL("LD H,HL 66 4", 0x66u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_L_B("LD L,B 68 4", 0x68u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_L_C("LD L,C 69 4", 0x69u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_L_D("LD L,D 6A 4", 0x6Au, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_L_E("LD L,E 6B 4", 0x6Bu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_L_H("LD L,H 6C 4", 0x6Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_L_L("LD L,L 6D 4", 0x6Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_L_HL("LD L,HL 6E 4", 0x6Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_B("LD HL,B 70 4", 0x70u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_C("LD HL,C 71 4", 0x71u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_D("LD HL,D 72 4", 0x72u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_E("LD HL,E 73 4", 0x73u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_H("LD HL,H 74 4", 0x74u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_L("LD HL,L 75 4", 0x75u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_N("LD HL,N 76 4", 0x36u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //3

        //LD A,A-L
        LD_A_BC("LD A,BC 0A 4", 0x0Au, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_DE("LD A,DE 1A 4", 0x1Au, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //LD_A_HL
        LD_A_NN("LD A,NN FA 4", 0xFAu, 16) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_A_NUM("LD A,# 3E 4", 0x3Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //4.)
        //LD_A_A

        LD_B_A("LD A,# 3E 4", 0x47u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_C_A("LD A,# 3E 4", 0x4Fu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_D_A("LD A,# 3E 4", 0x57u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_E_A("LD A,# 3E 4", 0x5Fu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_H_A("LD A,# 3E 4", 0x67u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_L_A("LD A,# 3E 4", 0x6Fu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_BC_A("LD A,# 3E 4", 0x02u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_DE_A("LD A,# 3E 4", 0x12u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_A("LD A,# 3E 4", 0x77u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_NN_A("LD A,# 3E 4", 0xEAu, 16) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //5.)
        LD_A_C_5("LD A,(C)", 0xF2u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //6.)
        LD_C_A_5("LD (C),A", 0xE2u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //7.)
        LD_A_HLD("LD A,(HLD)", 0x3Au, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDD_A_HL.execute(mmu, cpu)
            }
        },

        //8.)
        LD_A_HLm("LD A,(HL-)", 0x3Au, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDD_A_HL.execute(mmu, cpu)
            }
        },

        //9.)
        LDD_A_HL("LDD A,(HL)", 0x3Au, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //10.)
        LD_HLD_A("LD (HLD),A", 0x32u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDD_HL_A.execute(mmu, cpu)
            }
        },

        //11.)
        LD_HLm_A("LD (HL-),A", 0x32u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDD_HL_A.execute(mmu, cpu)
            }
        },

        //12.)
        LDD_HL_A("LDD (HL),A", 0x32u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //13.)
        LD_A_HLI("LD A,(HLI)", 0x2Au, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDI_A_HL.execute(mmu, cpu)
            }
        },

        //14.)
        LD_A_HLp("LD A,(HL+)", 0x2Au, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDI_A_HL.execute(mmu, cpu)
            }
        },

        //15.)
        LDI_A_HL("LDI A,(HL)", 0x2Au, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //16.)
        LD_HLI_A("LD (HLI), A", 0x22u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDI_HL_A.execute(mmu, cpu)
            }
        },

        //17.)
        LD_HLp_A("LD (HL+), A", 0x22u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDI_HL_A.execute(mmu, cpu)
            }
        },

        //18.)
        LDI_HL_A("LDI (HL), A", 0x22u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //19.)
        LDH_N_A("LDH (N), A // LD (FF00+n), A", 0xE0u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //20.)
        LDH_A_N("LDH A, (N) // LD A, (FF00+n)", 0xF0u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        };

        companion object {
            fun getOp(byte: UByte) {

                when (byte) {
                    0x06u.toUByte() -> {
                    }
                }
            }
        }

    }

    //3.3.2 16bit Loads
    enum class Load16Bit(mnemonic: String, opcode: UInt, cycles: Int) : Execute {

        //3.3.2 16bit Loads

        //1.)
        LD_BC_NN("LD BC, NN", 0x01u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_DE_NN("LD DE, NN", 0x11u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_HL_NN("LD HL, NN", 0x21u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        LD_SP_NN("LD SP, NN", 0x31u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //2.)
        LD_SP_HL("LD SP,HL", 0xF9u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //3.)
        LD_HL_SPpN("LD HL,SP+n", 0xF8u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                LDHL_SP_N.execute(mmu, cpu)
            }
        },

        //4.)
        LDHL_SP_N("LDHL SP,N", 0xF8u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //5.)
        LD_NN_SP("LD (nn), SP", 0x08u, 20) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //6.)
        PUSH_AF("PUSH AF", 0xF5u, 16) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        PUSH_BC("PUSH BC", 0xC5u, 16) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        PUSH_DE("PUSH DE", 0xDEu, 16) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        PUSH_HL("PUSH HL", 0xE5u, 16) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //7.)
        POP_AF("POP AF", 0xF1u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        POP_BC("POP BC", 0xC1u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        POP_DE("POP DE", 0xD1u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        POP_HL("POP HL", 0xE1u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        };


        fun getOp(byte: UByte) {

            when (byte) {
                0x06u.toUByte() -> {
                }
            }
        }

    }

    //3.3.3
    enum class ALU8Bit(mnemonic: String, opcode: UInt, cycles: Int) : Execute {
        //1.)
        ADD_A_A("ADD A, A", 0x87u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_A_B("ADD A, B", 0x80u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_A_C("ADD A, C", 0x81u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_A_D("ADD A, D", 0x82u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_A_E("ADD A, E", 0x83u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_A_H("ADD A, H", 0x84u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_A_L("ADD A, L", 0x85u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_A_HL("ADD A, HL", 0x86u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_A_NUM("ADD A, #", 0xC6u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //2.)

        ADC_A_A("ADC A, A", 0x8Fu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_B("ADC A, B", 0x88u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_C("ADC A, C", 0x89u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_D("ADC A, D", 0x8Au, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_E("ADC A, E", 0x8Bu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_H("ADC A, H", 0x8Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_L("ADC A, L", 0x8Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_HL("ADC A, HL", 0x8Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADC_A_NUM("ADC A, #", 0xCEu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },


        //3.)
        SUB_A("SUB A", 0x97u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SUB_B("SUB B", 0x90u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SUB_C("SUB C", 0x91u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SUB_D("SUB D", 0x92u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SUB_E("SUB E", 0x93u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SUB_H("SUB H", 0x94u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SUB_L("SUB L", 0x95u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SUB_HL("SUB HL", 0x96u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SUB_NUM("SUB #", 0xD6u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },


        //4.) SBC A,n
        SBC_A_A("SBC A,A", 0x9Fu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_B("SBC A,B", 0x98u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_C("SBC A,C", 0x99u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_D("SBC A,D", 0x9Au, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_E("SBC A,E", 0x9Bu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_H("SBC A,H", 0x9Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_L("SBC A,L", 0x9Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SBC_A_HL("SBC A,HL", 0x9Eu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //5.) AND n

        AND_A("AND A", 0xA7u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        AND_B("AND B", 0xA0u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        AND_C("AND C", 0xA1u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        AND_D("AND D", 0xA2u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        AND_E("AND E", 0xA3u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        AND_H("AND H", 0xA4u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        AND_L("AND L", 0xA5u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        AND_HL("AND HL", 0xA6u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        AND_NUM("AND #", 0xE6u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //6.) OR

        OR_A("OR A", 0xB7u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        OR_B("OR B", 0xB0u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        OR_C("OR C", 0xB1u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        OR_D("OR D", 0xB2u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        OR_E("OR E", 0xB3u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        OR_H("OR H", 0xB4u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        OR_L("OR L", 0xB5u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        OR_HL("OR HL", 0xB6u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        OR_NUM("OR #", 0xF6u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },


        //7.) XOR n

        XOR_A("XOR A", 0xAFu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        XOR_B("XOR B", 0xA8u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        XOR_C("XOR C", 0xA9u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        XOR_D("XOR D", 0xAAu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        XOR_E("XOR E", 0xABu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        XOR_H("XOR H", 0xACu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        XOR_L("XOR L", 0xADu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        XOR_HL("XOR HL", 0xAEu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        XOR_NUM("XOR *", 0xEEu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //8.) CP n

        CP_A("CP A", 0xBFu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        CP_B("CP B", 0xB8u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        CP_C("CP C", 0xB9u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        CP_D("CP D", 0xBAu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        CP_E("CP E", 0xBBu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        CP_H("CP H", 0xBCu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        CP_L("CP L", 0xBDu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        CP_HL("CP HL", 0xBEu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        CP_NUM("CP #", 0xFEu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //9.) INC n

        INC_A("INC A", 0x3Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_B("INC B", 0x04u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_C("INC C", 0x0Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_D("INC D", 0x14u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_E("INC E", 0x1Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_H("INC H", 0x24u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_L("INC L", 0x2Cu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_HL("INC HL", 0x34u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //10.) DEC n

        DEC_A("DEC A", 0x3Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_B("DEC B", 0x05u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_C("DEC C", 0x0Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_D("DEC D", 0x15u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_E("DEC E", 0x1Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_H("DEC H", 0x25u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_L("DEC L", 0x2Du, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_HL("DEC HL", 0x35u, 12) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        };

        fun getOp(byte: UByte) {

            when (byte) {
                0x06u.toUByte() -> {
                }
            }
        }

    }

    //3.3.4
    enum class ALU16Bit(mnemonic: String, opcode: UInt, cycles: Int) : Execute {
        ADD_HL_BC("ADD HL, BC", 0x09u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_HL_DE("ADD HL, DE", 0x19u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_HL_HL("ADD HL, HL", 0x029u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        ADD_HL_SP("ADD HL, SP", 0x39u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },


        ADD_SP_NUM("ADD HL, BC", 0xE8u, 16) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },


        //3.) INC nn
        INC_BC("INC BC", 0x03u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_DE("INC DE", 0x13u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_HL("INC HL", 0x23u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        INC_SP("INC SP", 0x33u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },


        //4.) DEC nn
        DEC_BC("DEC BC", 0x0Bu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_DE("DEC DE", 0x1Bu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_HL("DEC HL", 0x2Bu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        DEC_SP("DEC SP", 0x3Bu, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        };

        fun getOp(byte: UByte) {

            when (byte) {
                0x06u.toUByte() -> {
                }
            }
        }

    }

    //3.3.5
    enum class SWAP(mnemonic: String, opcode: UInt, cycles: Int) : Execute {

        //1.)
        SWAP_A("SWAP A CB 37", 0xCB37u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SWAP_B("SWAP A CB 37", 0xCB30u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SWAP_C("SWAP A CB 37", 0xCB31u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SWAP_D("SWAP A CB 37", 0xCB32u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SWAP_E("SWAP A CB 37", 0xCB33u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SWAP_H("SWAP A CB 37", 0xCB34u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SWAP_L("SWAP A CB 37", 0xCB35u, 8) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        SWAP_HL("SWAP A CB 37", 0xCB37u, 16) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },


        ;

        companion object {
            fun getShort(short: UShort) {

            }
        }

    }

    //3.3.5
    enum class MISC(mnemonic: String, opcode: UInt, cycles: Int) : Execute {

        //2.) DAA
        DAA("DAA -/-", 0x27u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //3.)
        CPL("CPL -/-", 0x2Fu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //4.)
        CCF("CCF -/-", 0x3Fu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //5.)
        SCF("SCF -/-", 0x37u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //6.)
        NOP("NOP -/-", 0x00u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //7.)
        HALT("HALT -/-", 0x76u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //8.)
        STOP("STOP -/-", 0x1000u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //9.)
        DI("DI -/-", 0xF3u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        //9.)
        EI("EI -/-", 0xFBu, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },

        ;

    }

    //3.3.6
    enum class RS(mnemonic: String, opcode: UInt, cycles: Int): Execute {
        //1.)
        RLCA("RLCA -/-", 0x07u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }
        },
        //2.)
        RLA("RLA -/-", 0x17u, 4) {
            override fun execute(mmu: MMU, cpu: CPU) {
                TODO("Not yet implemented")
            }

        }
    }
}

interface Execute {
    fun execute(mmu: MMU, cpu: CPU)
}
