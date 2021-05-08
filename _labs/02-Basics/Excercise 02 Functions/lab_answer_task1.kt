package task1

fun main() {
    val addressbook = HashMap<String, String>()

    fun listBook() {
      for ((name, lastname) in addressbook) { print("$name $lastname") }
    }
    
    fun addUser() {
        print("gimme a name?")
        val name = readLine()
        print("gimme a lastname?")
        val lastname = readLine()
        if (name != null && lastname != null) 
            addressbook.putIfAbsent(name,lastname)
    }

    print("what do you want?")
    var command = readLine()
    while(command != "quit") {
        when (command) {
            "list" -> listBook()
            "add" -> addUser()
        }
        print("what do you want?")
        command = readLine()
    }
}