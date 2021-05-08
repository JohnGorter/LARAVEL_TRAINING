package task1

fun main() {
    val addressbook = HashMap<String, String>()
    // Task 1. Add the sorting code
    fun sortBook() {
        addressbook.entries.sortedBy { it.key }.forEach { print("${it.key} ${it.value}") }
    }
    // End Task 1.
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
            // Task 1. Call the sort
            "sort" -> sortBook() 
            // End Task 1.
        }
        print("\r\nwhat do you want?")
        command = readLine()
    }
}