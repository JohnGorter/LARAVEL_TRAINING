package com.example

class Functions {
    fun add(a: Int, b: Int) = a + b

    fun subtract(a: Int, b: Int): Int = a - b

    fun multiply(a: Int, b: Int): Int {
        return a*b
    }

    fun min(a: Int, b:Int): Int {
        return if (a < b) a else b
    }

    fun print(name: String, value:Int): String = "The name $name has value $value"

    fun printDefault(name: String = "John Doe", value:Int = 42): String = "The name $name has value $value"

}

fun String.firstFive(): String = substring(0,5)

val String.firstFive: String
        get() = substring(0,5)

infix fun String.containsInfix(substring: String) = contains(substring)