package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CollectionsTest {
    val collections = Collections();

    @Test
    fun retrieveLastFromAnimalsList() {
        assertEquals("Elephant", collections.createArrayListAnimals().last())
    }

    @Test
    fun retrieveMaxFromNumbersList() {
        assertEquals("7", collections.createArrayListNumbers().max())
    }

    @Test
    fun retrieveMinFromNumbersList() {
        assertEquals("1", collections.createArrayListNumbers().min())
    }
}