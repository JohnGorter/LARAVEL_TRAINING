package com.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LocalFunctionTest {

    @Test
    fun testSaveEmployee() {
        val localFunction = LocalFunction()
        assertEquals("success", localFunction.saveEmployee(EmployeeOptional("Peter", 25)))
        assertEquals("failed salary is empty ", localFunction.saveEmployee(EmployeeOptional("Peter")))
        assertEquals("failed firstname is empty ", localFunction.saveEmployee(EmployeeOptional(salary = 25)))
        assertEquals("failed salary is empty failed firstname is empty ", localFunction.saveEmployee(EmployeeOptional()))
    }
}