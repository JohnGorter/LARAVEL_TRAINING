package com.example


class Collections {

    fun createHashSet(): HashSet<String> =
            hashSetOf("Deer", "Monkey", "Elephant")

    fun createArrayListAnimals(): ArrayList<String> =
            arrayListOf("Deer", "Monkey", "Elephant")

    fun createArrayListNumbers(): ArrayList<String> =
            arrayListOf("5", "7", "1")

    fun createHashMap(): HashMap<Int, String> =
            hashMapOf(1 to "Deer", 2 to "Monkey", 3 to "Elephant")

}
