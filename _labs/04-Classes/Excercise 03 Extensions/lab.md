# Extensions
## lab time: 45 minutes

### Excercise 1. Write an extension method
You need to use a method to the String class that returns a new string with first and last character removed; this method is not already available in String class. Write an extension function to accomplish this task.

### Excercise 2. Write LINQ in kotlin as extension methods
Write two extension methods on the MutableList<R> type that allows the list to be filtered and items to be projected. The calling code should work as follows:

```
    var items = mutableListOf("John");
    items.add("john2")

    var testList = items.Where { it.startsWith("J") }
                        .Select { object { val name=it} }
                        
    for (r in testList) {
        print("${r.name}")
    }
```

Write the two extension methods (Where and Select).