package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CustomGetterSetterTest {
    val custom = CustomGetterSetter();

    @Test
    fun testCustomGetterSetter() {
        assertEquals("", custom.name)
        assertEquals(" get", custom.result)

        custom.name = "Joe"
        assertEquals(" get set to Joe", custom.result)
        assertEquals("Joe", custom.name)
        assertEquals(" get set to Joe get", custom.result)
    }

    @Test
    fun testPrivateSetter() {
        assertEquals("", custom.secure)
        //custom.secure = "" // Cannot assign to 'secure': the setter is private in 'CustomGetterSetter'
    }
}