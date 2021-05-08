package com.example

class SecondaryConstructor {
    var result = ""
    init {
        result += "init "
    }

    constructor(value: String) {
        result += value
    }
}