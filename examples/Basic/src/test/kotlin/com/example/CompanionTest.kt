package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CompanionTest {
    @Test
    fun testCompanion() {
        assertEquals("Hello world", OuterClass.CompanionObject.hello())
    }

    @Test
    fun testCompanionWithoutCompanionKeyword() {
        assertEquals("Hello world", OuterClassWithoutCompanionObjectName.Companion.hello())
    }

    @Test
    fun testCompanionExtensionFunction() {
        assertEquals("Goodbye", OuterClass.CompanionObject.goodbye())
    }
}