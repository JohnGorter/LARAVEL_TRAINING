package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InitTest {
    @Test
    fun testInit() {
        val init = Init("test")
        assertEquals("Value1: test Value2: test", init.result)
    }
}