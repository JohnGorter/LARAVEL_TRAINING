package com.example

import java.util.*

class When {
    fun rangeCheck(value: Int): Boolean =
            when (value) {
                in 1..3 -> true
                else -> false
            }

    fun whatDay(number: Int): String =
            when (number) {
                in 1..5 -> "Workday"
                !in 1..5 -> "Weekend"
                else -> "Something went wrong"
            }

    fun typeCheck(value: Any): Boolean =
            when (value) {
                is Int -> true
                else -> {
                    println("No boolean")
                    false
                }
            }

    fun ifElseIf(number: Int): String =
            when {
                number > 0 -> "Positive"
                number % 2 == 0 -> "Zero"
                number < 0 -> "Negative"
                else -> "Something went wrong"
            }
}

