// Exercise 1. Rewrite the JAVA code to Kotlin
class EventManager {
    // dont make any instances of this class
    private constructor() {}

    companion object {
        private var instance: EventManager? = null
        val managerInstance: EventManager
            get() {
                if (instance == null) {
                    instance = EventManager()
                }
                return instance!! 
            }
    }

    fun sendEvent(eventName: String): Boolean {
        println("Event Sent $eventName")
        return true;
    }
}

// Excercise 2. Rewrite the JAVA code to Kotlin
class SomeClass() {
    val id: Int
    init {
       id = nextId++       
    }
    private companion object {
       var nextId = 1
    }
}

fun main() {
    // Excercise 1. call singleton
    EventManager.managerInstance.sendEvent("oeh")
    // Excercise 2. call singleton
    repeat(2) { 
        println("Person " + SomeClass().id)
    }
}