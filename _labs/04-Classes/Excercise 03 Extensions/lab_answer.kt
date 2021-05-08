fun String.removeFirstLastChar(): String =  this.substring(1, this.length - 1)

fun <T> List<T>.Where(pred:(item:T) -> Boolean) : MutableList<T> {
    var retval = mutableListOf<T>()
    for (i:T in this) { 
        if (pred(i)) retval.add(i)
    }
    return retval
}

fun <T, R> MutableList<T>.Select(pred:(item:T) -> R) : MutableList<R> {
    var retval = mutableListOf<R>()
    for (i:T in this) { 
        retval.add(pred(i))
    }
    return retval
}

fun main() {
    val myString= "Hello Everyone"
    val result = myString.removeFirstLastChar()
    println("First character is: $result")

    var items = mutableListOf("John");
    items.add("john2")

    var testList = items.Where { it.startsWith("J") }
                        .Select { object { val name=it} }
                        
    for (r in testList) {
        print("${r.name}")
    }
}