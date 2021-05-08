package com.example

import org.junit.jupiter.api.Test

class DestructingDeclarationTest {

    @Test
    fun testDestructingDeclaration() {
        val person = PersonDataClass("Peter", 39)
        val(firstName, age) = person
    }
}
