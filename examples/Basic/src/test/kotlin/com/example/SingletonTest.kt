package com.example

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SingletonTest {
    @Test
    fun testSingleton() {
        assertEquals(21, Singleton.halfAmount())
        //val singleton1 = Singleton() // Expression 'Singleton' of type 'Singleton' cannot be invoked as a function. The function 'invoke()' is not found
    }
}