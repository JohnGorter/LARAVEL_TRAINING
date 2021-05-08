package com.example

class Loops {
    fun simpleLoop(): Int {
        var total = 0
        for (item in 1..4) {
            total += item
        }
        return total
    }

    fun simpleLoopSteps(): Int {
        var total = 0
        for (item in 1..5 step 2) {
            total += item
        }
        return total
    }

    fun countDownLoop(): Int {
        var total = 0
        for (item in 3 downTo 1) {
            total += item
        }
        return total
    }

    fun exclusiveLoop(): Int {
        var total = 0
        for (item in 1 until 3) {
            total += item
        }
        return total
    }

    fun mapLoop(): Int {
        val map = HashMap<Char, Int>()
        map.put('a', 10)
        map.put('b', 4)
        map.put('c', 2)
        map.put('z', 5)

        var total = 0
        for (char in 'a'..'c') {
            total += map[char]!! // !! to convert Int? to Int
        }
        return total
    }

    fun loopWithIndex(names: Array<String>): Array<String> {
        var result = arrayOf<String>()
        for ((index, name) in names.withIndex()) {
            result += "Index $index has name $name"
        }
        return result
    }
}

