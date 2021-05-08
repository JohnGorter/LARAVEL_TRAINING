package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InnerClassTest {
    @Test
    fun testInnerClass() {
        val outerClass = Outer()
        assertEquals("Hello world", outerClass.helloWorld())
        assertEquals("Nested: Hello world", outerClass.Nested().helloWorldNested())
    }
}