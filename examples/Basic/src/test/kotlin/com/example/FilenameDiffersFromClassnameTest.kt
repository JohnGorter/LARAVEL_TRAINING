package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FilenameDiffersFromClassnameTest {

    @Test
    fun testMultipleClassesInOneFile() {
        assertEquals(42, Answer().answer())
        assertEquals(21, HalfAnswer().answer())
    }
}