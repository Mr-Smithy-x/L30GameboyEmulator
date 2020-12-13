package com.vonley.contracts

interface IntDifferenceImpl : ClosedRange<Int> {
   val difference: Int
      get() = endInclusive - start
}