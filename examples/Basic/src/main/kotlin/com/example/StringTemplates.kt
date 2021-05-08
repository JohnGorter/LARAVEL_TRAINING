package com.example

class StringTemplates {
    fun helloName(name: String): String {
        return "Hello $name"
    }

    fun helloNames(names: Array<String>): ArrayList<String> {
        val helloNames: ArrayList<String> = arrayListOf<String>()
        var index = 0
        while (index < names.size) {
            helloNames += "Hello ${names[index]} with index: $index"
            index++
        }
        return helloNames
    }
}