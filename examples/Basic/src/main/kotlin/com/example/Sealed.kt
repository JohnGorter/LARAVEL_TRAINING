package com.example

sealed class Sealed {
    class SubClass1(): Sealed()
    class SubClass2(): Sealed()
}

fun whichClass(claz: Sealed): String =
    when(claz) {
        is Sealed.SubClass1 -> "1"
        is Sealed.SubClass2 -> "2"
    }
