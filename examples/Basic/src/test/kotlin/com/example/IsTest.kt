package com.example

import kotlin.test.Test
import kotlin.test.assertEquals

class IsTest {

    val isTest = Is()

    @Test
    fun testSmartcast() {
        assertEquals("world", isTest.smartcast("hello world"))
        assertEquals("21", isTest.smartcast(42))

    }
}