package com.example

class Print : PrintInterface {
    override var number = 0
    override fun print(value: String): String {
        println("This is the $value")
        number++
        return "hello $value"
    }

}