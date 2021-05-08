package test.kotlin.com.example

import com.example.Employee
import com.example.Properties
import com.example.hello
import main.kotlin.com.example.main
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class EmployeeClassTest {

    @Test
    fun testEmployee() {
        val employee = Employee("Peter", 2500)
        assertEquals("Peter", employee.firstName)
        assertEquals(2500, employee.salary)
        employee.salary = 2600
        //employee.firstName = "John" // Val cannot be reassigned
    }
}