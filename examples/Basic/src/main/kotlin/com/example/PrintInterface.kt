package com.example

interface PrintInterface {
    var number: Int
    fun print(value: String): String
    fun defaultPrint(value:String): String = "default print $value"
}
