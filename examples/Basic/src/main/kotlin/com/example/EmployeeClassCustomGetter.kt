package com.example

class EmployeeCustomGetter(val firstName: String, var salary: Int) {
    val isHighSalary: Boolean
        get() {
            return salary > 3000
        }
    val isLowSalary: Boolean
        get() = salary < 2000
}