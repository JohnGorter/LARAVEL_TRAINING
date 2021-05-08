package com.example

import org.junit.Test
import kotlin.test.assertEquals

class DataClassTest {
    val dataClass = DataClass("Data value")
    val normalClass = NormalClass("Normal value")

    @Test
    fun testToString() {
        // toString() returns com.example.NormalClass@[object id]
        assertEquals("com.example.NormalClass", normalClass.toString().substringBefore("@"))
        assertEquals("DataClass(value=Data value)", dataClass.toString())
    }

    @Test
    fun testCopy() {
        //dataClass.value = "test" // Val cannot be reassigned
        val newDataClass = dataClass.copy(value="test")
        assertEquals("test", newDataClass.value)
    }

}