package com.example

class Variables {
    fun valVSvar() {
        var value1 = 3
        val value2 = 5

        value1 = 11
        //value2 = 52

        val boolean: Boolean
        if (value1 < 4) {
            boolean = true
        } else {
            boolean = false
        }
    }
}