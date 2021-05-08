# Null Safety
## lab time: 30 minutes

Given the following data classes: 
```
data class Person(val country: Country?)
data class Country(val code: String?)

fun main() {
    val p: Person? = Person(Country("ENG"))
    print (p.country.code)
    val res = p.country.code
    print(res == "ENG")
}
```

Compile the program from the listing above. 
Check the compile errors and try to fix them using lessons learned

### Excercise 1. Check for null in conditions
Write code to check for null using conditions before printing the country code to the console.

### Excercise 2. Use safe calls
Write code to check for null using safe calls before printing the country code to the console.

### Excercise 3. Use !! operator
Write code to check for null using the !! operator before printing the country code to the console.

### Excercise 4. Use Safe casts
Write code to check for null using safe casts before printing the country code to the console.
