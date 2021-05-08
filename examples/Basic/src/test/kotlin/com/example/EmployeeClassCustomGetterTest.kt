package test.kotlin.com.example

import com.example.Employee
import com.example.EmployeeCustomGetter
import com.example.Properties
import com.example.hello
import main.kotlin.com.example.main
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class EmployeeClassCustomGetterTest {

    @Test
    fun testEmployee() {
        val employee = EmployeeCustomGetter("Peter", 5000)
        assertTrue(employee.isHighSalary)
        employee.salary = 3000
        assertFalse(employee.isHighSalary)
        assertFalse(employee.isLowSalary)
        employee.salary = 1000
        assertTrue(employee.isLowSalary)
    }
}