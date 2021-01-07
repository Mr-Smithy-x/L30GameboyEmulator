package com.vonley.extensions

val Boolean.asUByte: UByte
    get() {
        return if (this) 0b0000_0001u.toUByte() else 0b0000_0000.toUByte()
    }

val UByte.asBoolean: Boolean
    get() {
        return this and 0b0000_0001u > 0b0u.toUByte()
    }