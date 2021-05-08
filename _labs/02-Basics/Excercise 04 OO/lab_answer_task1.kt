package task1

// Task 1. Create a User data class
data class User(val name:String, val lastname:String)
// End Task 1. 

// Task 1. Create an AddressBook domain class
class AddressBook(){
    val book = HashMap<String, String>()
    fun addUser(user:User) {
        book.putIfAbsent(user.name,user.lastname)
    }
    fun sort() {
        book.entries.sortedBy { it.key }.forEach { print("${it.key} ${it.value}") }
    }
    fun list() {
        for ((name, lastname) in book) { println("$name $lastname") }
    }
    fun filter(predicate:(i:Map.Entry<String, String>) -> Boolean) {
        book.forEach { 
            if (predicate(it)) println("${it.key} ${it.value}")
        }
    }
}
// End Task 1. 

fun main() {
    // Task 1. Use the address book
    val addressbook = AddressBook()
    // End Task 1. 
    print("\r\nwhat do you want?")
    var command = readLine()
    while(command != "quit") {
        when (command) {
            // Task 1. Use the address book
            "list" -> addressbook.list()
            "add" -> { 
                print("gimme a name?")
                val name = readLine()!!
                print("gimme a lastname?")
                val lastname = readLine()!!
                addressbook.addUser(User(name, lastname)) 
            }
            "sort" -> addressbook.sort()
            "filter" -> {
                print("What are you searching for?")
                val filter: String = readLine()!!
                addressbook.filter {
                    it.key == filter || it.value == filter
                }
            }
            // End Task 1.
        }
        print("\r\nwhat do you want?")
        command = readLine()
    }
}