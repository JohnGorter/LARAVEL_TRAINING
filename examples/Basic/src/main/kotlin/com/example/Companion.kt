package com.example

class OuterClass {
    companion object CompanionObject {
        fun hello(): String = "Hello"
    }
}

class OuterClassWithoutCompanionObjectName {
    companion object {
        fun hello(): String = "Hello"
    }
}

fun OuterClass.CompanionObject.goodbye(): String = "Goodbye"