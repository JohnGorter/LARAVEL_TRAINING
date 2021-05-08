# Companion Objects
## lab time: 45 minutes

### Excercise 1. Write a companion object for Singleton object
Rewrite the following JAVA code to Kotlin coding, where you use companion object syntax

```
public class EventManager {
    private static EventManager instance;
    private EventManager() {}

    public static EventManager getManagerInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public boolean sendEvent(String eventName) {
        Log.d("Event Sent", eventName);
        return true;
    }
}
```

### Excercise 2. Rewrite the following code
The code below uses static fields to maintain id's in a People list. 
The code is written in Java. Rewrite this code to Kotlin and make use
of companion object syntax:
```
class Person {
    private static int nextId = 1;
    public int id;
    Person() {
        id = nextId++;
    }
}

public class Program {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            System.out.println("Person: " + new Person().id);
        }
    }
}
```