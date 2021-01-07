package com.vonley.contracts

interface UShortDifferenceImpl : ClosedRange<UShort> {
   val difference: UShort
      get() = (endInclusive - start).toUShort()

   /**
    * Checks whether the specified [value] belongs to the range.
    */
   override operator fun contains(value: UShort): Boolean = value >= start && value <= endInclusive
}