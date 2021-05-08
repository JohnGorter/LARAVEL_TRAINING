package com.example

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LambdaTest {
    @Test
    fun testBasicLambda() {
        val product = { x: Int, y: Int -> x * y }
        assertEquals(6, product(2, 3))
    }

    @Test
    fun testLambdaMultipleStatements() {
        val product = { x: Int, y: Int ->
            println("$x * $y = " + x * y)
            x * y
        }
        assertEquals(6, product(2, 3))
    }

    val employees = listOf(
            Employee("Peter", 3000),
            Employee("Anna", 3500),
            Employee("Sara", 3200)
    )

    @Test
    fun testEmployeeWithHighestSalary() {
        // '!!' because It should give a result
        //val employeeWithHighestSalary = employees.maxBy { it.salary }!!
        //val employeeWithHighestSalary = employees.maxBy { e: Employee -> e.salary }!!
        //val employeeWithHighestSalary = employees.maxBy() { it.salary }!!
        val employeeWithHighestSalary = employees.maxBy(Employee::salary)!!
        assertEquals("Ann", employeeWithHighestSalary.firstName)
        assertEquals(3500, employeeWithHighestSalary.salary)
    }


    @Test
    fun testLambdaWithVariables() {
        fun concatEmployeeNames(employees: List<Employee>, prefix: String) =
                employees.forEach { println("$prefix " + it.firstName) }

        concatEmployeeNames(employees, "Name: ")
    }

    @Test
    fun testLambdaWithLocalNonFinalVariable() {
        fun concatEmployeeNames(employees: List<Employee>, prefix: String) {
            var count = 0;
            employees.forEach {
                count++;
                println("$prefix " + it.firstName)
            }
            println("Number of employees: $count")
        }

        concatEmployeeNames(employees, "Name: ")
    }

    @Test
    fun testLambdaFilter() {
        val resultList = employees.filter { it.firstName.contains("a") }
        assertEquals(2, resultList.size)
        assertEquals("Anna", resultList[0].firstName)
        assertEquals("Sara", resultList[1].firstName)
    }

    @Test
    fun testLambdaFilterAndMap() {
        val resultList = employees.filter { it.firstName.contains("a") }.map { it.salary + 100 }
        assertEquals(2, resultList.size)
        assertEquals(3600, resultList[0])
        assertEquals(3300, resultList[1])

        val resultListNames = employees.filter { it.firstName.contains("a") }.map(Employee::firstName)
        assertEquals(2, resultList.size)
        assertEquals("Anna", resultListNames[0])
        assertEquals("Sara", resultListNames[1])
    }

    @Test
    fun testFunctions() {
        val positiveIncome = { e: Employee -> e.salary > 0 }
        assertTrue(employees.all(positiveIncome))
        assertEquals(3, employees.count(positiveIncome))
        assertEquals("Peter", employees.find(positiveIncome)!!.firstName)

        val highIncome = { e: Employee -> e.salary > 3400 }
        assertTrue(employees.any(highIncome))
        assertFalse(employees.all(highIncome))
    }

    @Test
    fun testGroupBy() {
        val employeesGroup = employees.groupBy { it.firstName.last() }
        assertEquals("Peter", employeesGroup.get('r')!![0].firstName)

        assertEquals("Anna", employeesGroup.get('a')!![0].firstName)
        assertEquals("Sara", employeesGroup.get('a')!![1].firstName)
    }

    @Test
    fun testFlatten() {
        val integers1 = listOf(1, 2, 3)
        val integers2 = listOf(6, 4, 8)
        val combinedLists = listOf(integers1, integers2)

        val listOfValues = combinedLists.flatten()

        assertEquals(6, listOfValues.size)
        assertEquals(1, listOfValues[0])
        assertEquals(2, listOfValues[1])
        assertEquals(3, listOfValues[2])
        assertEquals(6, listOfValues[3])
        assertEquals(4, listOfValues[4])
        assertEquals(8, listOfValues[5])


//        val department1 = Department(listOf(Employee("Peter", 3400), Employee("Ann", 3600)))
//        val department2 = Department(listOf(Employee("Susan", 3000)))
//        val departments = listOf(department1, department2)
//
//        val employees = departments.flatMap { customer -> customer.em }

    }

    @Test
    fun testFlatMap() {
        val integers1 = listOf(1, 2, 3)
        val integers2 = listOf(6, 4, 8)
        val combinedLists = listOf(integers1, integers2)

        val employees = listOf(Employee("Peter", 3400), Employee("Ann", 3600))

        val employeeNames = employees.flatMap { it -> listOf(it.firstName) }
        assertEquals(2, employeeNames.size)
        assertEquals("Peter", employeeNames[0])
        assertEquals("Ann", employeeNames[1])


        val department1 = Department(listOf(Employee("Peter", 3400), Employee("Ann", 3600)))
        val department2 = Department(listOf(Employee("Susan", 3000)))
        val departments = listOf(department1, department2)

        val employeeNames2 = departments.flatMap { it -> listOf(it.employees.flatMap { it -> listOf(it.firstName) }) }
                //assertEquals(3, employeeNames2.size)
        println(employeeNames2[0])
        println(employeeNames2[1])
//        assertEquals("Peter", employeeNames2[0])
//        assertEquals("Ann", employeeNames2[1])
//        assertEquals("Susan", employeeNames2[2])

    }


}