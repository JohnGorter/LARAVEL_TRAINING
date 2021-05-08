package com.example

import org.junit.Test
import kotlin.test.assertEquals

class SealedTest {

    @Test
    fun testSealed() {
        assertEquals("1", whichClass(Sealed.SubClass1()))
        assertEquals("2", whichClass(Sealed.SubClass2()))
    }
}