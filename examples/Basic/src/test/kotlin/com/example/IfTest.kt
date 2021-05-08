package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class IfTest {

    val ifTest = If()

    @Test
    fun testVerifyNumber() {
        assertEquals("Positive", ifTest.verifyNumber(5))
        assertEquals("Negative", ifTest.verifyNumber(-23))
    }

    @Test
    fun testVerifyNumberShort() {
        assertEquals("Positive", ifTest.verifyNumberShort(5))
        assertEquals("Negative", ifTest.verifyNumberShort(-23))
    }
}