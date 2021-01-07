package com.vonley.extensions


fun UInt.toHexString(): String {
    return """0x${("%x".format(this.toInt())).toUpperCase()}"""
}

fun UInt.toBinaryString(): String {
    return """0b${(Integer.toBinaryString(this.toInt())).toUpperCase()}"""
}

fun Int.toBinaryString(): String {
    return "0b${Integer.toBinaryString(this)}"
}

fun Int.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase()}"""
}

fun UShort.toHexString(): String {
    return """0x${("%x".format(this.toInt())).toUpperCase().padStart(4, '0')} """
}

fun UShort.toBinaryString(): String {
    return """0b${Integer.toBinaryString(this.toInt() and 0xFFFF).toUpperCase()}"""
}

fun Short.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase().padStart(4, '0')} """
}

fun Short.toBinaryString(): String {
    return "0b${Integer.toBinaryString(this.toInt() and 0xFFFF).toUpperCase()}"
}

fun UByte.toHexString(): String {
    return """0x${("%x".format(this.toInt())).toUpperCase().padStart(2, '0')}"""
}

fun UByte.toBinaryString(): String {
    return """0b${(Integer.toBinaryString(this.toInt() and 0xFF)).toUpperCase()}"""
}

fun Byte.toHexString(): String {
    return """0x${("%x".format(this)).toUpperCase().padStart(2, '0')}"""
}

fun Byte.toBinaryString(): String {
    return """0b${(Integer.toBinaryString(this.toInt() and 0xFF)).toUpperCase()}"""
}
