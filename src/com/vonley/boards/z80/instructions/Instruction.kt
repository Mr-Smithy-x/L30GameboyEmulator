package com.vonley.boards.z80.instructions

import com.vonley.boards.z80.CPU
import com.vonley.boards.z80.memory.MMU

//nn = signed byte
enum class Instruction(mnemonic: String, opcode: UInt, cycles: Int) : Execute {

    //2.)
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
    LD_C_B("LD C,C 48 4", 0x48u, 4) {
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
    LD_A_C_5("LD A,(C)", 0xF2u, 8){
        override fun execute(mmu: MMU, cpu: CPU) {
            TODO("Not yet implemented")
        }
    },

    //6.)
    LD_C_A_5("LD (C),A", 0xE2u, 8){
        override fun execute(mmu: MMU, cpu: CPU) {
            TODO("Not yet implemented")
        }
    },

    //7.)
    LD_A_HLD("LD A,(HLD)", 0x3Au, 8) {
        override fun execute(mmu: MMU, cpu: CPU) {
            TODO("Not yet implemented")
        }
    },
    //8.)
    LD_A_HLm("LD A,(HL-)", 0x3Au, 8) {
        override fun execute(mmu: MMU, cpu: CPU) {
            TODO("Not yet implemented")
        }
    },
    //9.)
    LDD_A_HL("LDD A,(HL)", 0x4Au, 8) {
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

interface Execute {
    fun execute(mmu: MMU, cpu: CPU)
}
