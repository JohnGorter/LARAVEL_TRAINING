package com.example

class CustomGetterSetter {
    var result = ""

    var name = ""
        set(value: String) {
            result += " set to $value"
            field = value
        }
        get() {
            result += " get"
            return field
        }

    var secure = ""
        private set
}