package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FunctionsTest {
    val functions = Functions()

    @Test
    fun testAdd() {
        assertEquals(5, functions.add(1,4));
    }

    @Test
    fun testSubstract() {
        assertEquals(2, functions.subtract(3,1));
    }

    @Test
    fun testMultiply() {
        assertEquals(6, functions.multiply(2,3));
    }

    @Test
    fun testMin() {
        assertEquals(2, functions.min(2,3));
        assertEquals(2, functions.min(3,2));
    }

    @Test
    fun testPrint() {
        assertEquals("The name Hans has value 10", functions.print(name="Hans", value=10))
    }

    @Test
    fun testPrintDefault() {
        assertEquals("The name John Doe has value 42", functions.printDefault())
        assertEquals("The name Peter has value 42", functions.printDefault(name="Peter"))
        assertEquals("The name John Doe has value 3", functions.printDefault(value=3))
        assertEquals("The name Peter has value 3", functions.printDefault(name="Peter", value=3))
    }

    @Test
    fun testExtensionFunction() {
        assertEquals("Hello", "Hello world".firstFive())
    }

    @Test
    fun testExtensionProperty() {
        assertEquals("Hello", "Hello world".firstFive)
    }

    @Test
    fun testInfix() {
        assertTrue("Hello world" containsInfix "Hello")
        assertTrue("Hello world" containsInfix "world")
        assertFalse("Hello world" containsInfix "something")
    }

    @Test
    fun testDestructingDeclaration() {
        val (animal, amount) = "deer" to 4
        assertEquals("deer", animal)
        assertEquals(4, amount)
    }
}