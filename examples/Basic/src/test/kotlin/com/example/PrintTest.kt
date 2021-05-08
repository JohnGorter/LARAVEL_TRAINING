package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PrintTest {
    val print = Print()

    @Test
    fun testPrint() {
        assertEquals(0, print.number)
        assertEquals("hello Test 1", print.print("Test 1"))
        assertEquals(1, print.number)
    }

    @Test
    fun testDefaultPrint() {
        assertEquals("default print Test default", print.defaultPrint("Test default"))
    }
}

