package com.vonley.extensions

val Boolean.asUByte: UByte
    get() {
        return if (this) 0b1u.toUByte() else 0b0.toUByte()
    }

val UByte.asBoolean: Boolean
    get() {
        return this > 0b0u.toUByte()
    }