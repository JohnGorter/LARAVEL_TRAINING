package com.example

class Init(value: String) {
    var result = "";

    val value1 = "Value1: $value"
    init {
        result += value1
        println(result)
    }

    var value2 = " Value2: $value"
    init {
        result += value2
        println(result)
    }
}