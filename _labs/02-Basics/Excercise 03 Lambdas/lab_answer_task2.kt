package task2

fun main() {
    val addressbook = HashMap<String, String>()
    //  Task 2. filterbook
    fun filterBook(predicate:(i:Map.Entry<String, String>) -> Boolean) {
        addressbook.forEach { 
            if (predicate(it)) println("${it.key} ${it.value}")
        }
    }
    // End Task 2.
    fun sortBook() {
        addressbook.entries.sortedBy { it.key }.forEach { print("${it.key} ${it.value}") }
    }
    fun listBook() {
      for ((name, lastname) in addressbook) { println("$name $lastname") }
    }
    fun addUser() {
        print("gimme a name?")
        val name = readLine()
        print("gimme a lastname?")
        val lastname = readLine()
        if (name != null && lastname != null) 
            addressbook.putIfAbsent(name,lastname)
    }

    print("\r\nwhat do you want?")
    var command = readLine()
    while(command != "quit") {
        when (command) {
            "list" -> listBook()
            "add" -> addUser()
            "sort" -> sortBook()
            // Task 2. Call the filter code
            "filter" -> {
                print("What are you searching for?")
                val filter: String = readLine()!!
                filterBook {
                    it.key == filter || it.value == filter
                }
            }
            // End Task 2.
        }
        print("\r\nwhat do you want?")
        command = readLine()
    }
}