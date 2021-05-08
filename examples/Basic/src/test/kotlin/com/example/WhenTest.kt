package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WhenTest {

    val whenTest = When();

    @Test
    fun testRangeCheck() {
        assertTrue(whenTest.rangeCheck(1))
        assertFalse( whenTest.rangeCheck(-1))
    }

    @Test
    fun testRangeNotCheck() {
        assertEquals("Workday", whenTest.whatDay(1))
        assertEquals("Workday", whenTest.whatDay(5))
        assertEquals("Weekend", whenTest.whatDay(6))
        assertEquals("Weekend", whenTest.whatDay(7))
        assertEquals("Weekend", whenTest.whatDay(8))


    }

    @Test
    fun testTypeCheck() {
        assertTrue(whenTest.typeCheck(14))
        assertFalse(whenTest.typeCheck("test"))
    }


}