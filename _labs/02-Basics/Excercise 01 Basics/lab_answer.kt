package task1

fun main() {
    val addressbook = HashMap<String, String>()
    while (true) {
        print("gimme a name?")
        val name = readLine()
        print("gimme a lastname?")
        val lastname = readLine()
        if (name != null && lastname != null)
            addressbook.putIfAbsent(name,lastname)
        print("$addressbook")
    }
}
