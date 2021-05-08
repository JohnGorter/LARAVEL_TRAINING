package com.example

class Outer {
    fun helloWorld(): String = "Hello world"

    inner class Nested() {
        val outer = this@Outer
        fun helloWorldNested(): String = "Nested: " + helloWorld()
    }
}