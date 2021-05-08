# Sealed classes
## lab time: 45 minutes

### Excercise 1. Write a sealed class
Write a sealed class that reflects the months of the year. For each month, write a nested (or file level) class that holds the name and the numberOfDays of that month.
The client calling code would then work as follows:

```
 ... your classes here ...

 fun main() {
        val jan = Month.January(31)
        val feb = Month.February("Feb")
    }
```

### Excercise 2. Write the consuming code
Write a pattern matching function that uses sealed classes to print the details of the sealed class instances. The definition of the function is given below:
```
 fun eval(month: Month) = when (month) {
        ... your code here ...
    }

 fun main() {
        val jan = Month.January(31)
        val feb = Month.February("Feb")

        eval(jan)
        eval(feb)
    }
```