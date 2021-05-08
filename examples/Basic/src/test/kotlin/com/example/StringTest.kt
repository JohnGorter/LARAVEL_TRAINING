package com.example

import kotlin.test.Test
import kotlin.test.assertEquals

class StringTest {

    @Test
    fun testSplitDot() {
        val result = "hello.world".split(".")
        assertEquals(2, result.size)
        assertEquals("hello", result[0])
        assertEquals("world", result[1])
    }

    @Test
    fun testSplitMultipleDelimiters(){
        val result = "hello.world-today".split(".", "-")
        assertEquals(3, result.size)
        assertEquals("hello", result[0])
        assertEquals("world", result[1])
        assertEquals("today", result[2])
    }

    @Test
    fun testSplitRegex(){
        val result = "Monday Tuesday Wednesday".split("\\s".toRegex())
        assertEquals(3, result.size)
        assertEquals("Monday", result[0])
        assertEquals("Tuesday", result[1])
        assertEquals("Wednesday", result[2])
    }

    @Test
    fun testSplitRegexTripleQuotes(){
        val result = "Monday Tuesday Wednesday".split("""\s""".toRegex())
        assertEquals(3, result.size)
        assertEquals("Monday", result[0])
        assertEquals("Tuesday", result[1])
        assertEquals("Wednesday", result[2])
    }

    @Test
    fun testMultiLineString() {
        val text = """
            This is
            a
            long text
        """.trimIndent()
        assertEquals("This is\na\nlong text", text)
    }
}