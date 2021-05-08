package com.example

class LocalFunction {
    fun saveEmployee(employee: EmployeeOptional): String {
        fun validateField(value: Any?, fieldName: String): String {
            if (null == value) {
                println("failed $fieldName is empty ")
                return "failed $fieldName is empty "
            }
            return ""
        }
        var result = validateField(employee.salary, "salary")
        result += validateField(employee.firstName, "firstname")
        return if (result.isNotEmpty()) result else "success"
    }
}