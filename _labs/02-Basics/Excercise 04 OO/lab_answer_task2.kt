package task2 

// Task 2. Add a Sort function as an extension method to the HashMap
fun HashMap<String, String>.Sort() {
    entries.sortedBy { it.key }.forEach { print("${it.key} ${it.value}") }
}
// End Task 2.
// Task 2. Add a Filter function as an extension method to the HashMap
fun HashMap<String, String>.Filter(predicate:(i:Map.Entry<String, String>) -> Boolean) {
    forEach { 
        if (predicate(it)) println("${it.key} ${it.value}")
    }
}
// End Task 2.

data class User(val name:String, val lastname:String)

class AddressBook(){
    val book = HashMap<String, String>()
    fun addUser(user:User) {
        book.putIfAbsent(user.name,user.lastname)
    }
    fun sort() {
        // Task 2. change calling code 
        book.Sort()
        // End Task 2.
    }
    fun list() {
        for ((name, lastname) in book) { println("$name $lastname") }
    }
    fun filter(predicate:(i:Map.Entry<String, String>) -> Boolean) {
        // Task 2. change calling code 
        book.Filter(predicate)
        // End Task 2.
    }
}

fun main() {
    val addressbook = AddressBook()
    print("\r\nwhat do you want?")
    var command = readLine()
    while(command != "quit") {
        when (command) {
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
        }
        print("\r\nwhat do you want?")
        command = readLine()
    }
}