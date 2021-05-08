package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SecondaryConstructorTest {
    @Test
    fun testSecondaryConstructor() {
        val secondaryConstructor = SecondaryConstructor("one")
        assertEquals("init one", secondaryConstructor.result)
    }
}