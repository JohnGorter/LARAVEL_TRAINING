package com.example

class Is {
    fun smartcast(variable: Any): String {
        var result = ""
        if (variable is String) {
            result += variable.substring(6, 11)
        }
        if (variable is Int) {
            result += variable.div(2)
        }
        return result
    }
}