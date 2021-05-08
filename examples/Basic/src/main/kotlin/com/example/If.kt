package com.example

class If {
    fun verifyNumber(number: Int): String {
        val result = if (number > 0) {
            "Positive"
        } else {
            "Negative"
        }
        return result
    }

    fun verifyNumberShort(number: Int): String =
        if (number > 0) "Positive" else "Negative"

}