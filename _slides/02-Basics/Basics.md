## Basics


--
### Functions

```Kotlin
fun main(args: Array<String>) {
    println("Helloworld")
}
```

--
### Remarks
* ';' not needed
* println instead of System.out.println
	Wrapper in the Kotlin standard library
* No special Array type like in Java

--
### Helloworld with an expression body
```Kotlin
fun main(args: Array<String>) = println("Helloworld short")
```

--
### Function return values in an expression body
```Kotlin
fun add(a: Int, b: Int) = a + b
```

--
### Specify return type
* Optional
```Kotlin
fun subtract(a: Int, b: Int): Int = a - b
```

--
### Block body return values
* Expression body without curly braces
* Block body with curly braces
```Kotlin
fun multiply(a: Int, b: Int): Int {
	return a*b
}
```

--
### Unit tests
* Various options
* We use JUnit combined with Kotlin test support
* See the intro slides for the Gradle configuration

--
### Unit test example
```Kotlin
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FunctionsTest {
    @Test
    fun testMultiply() {
        assertEquals(6, functions.multiply(2,3));
    }
}
```

--
### Type inference
* In many cases Kotlin doesn't need type information. It uses type inference to retrieve the type.

--
### if
* Like ternary operator in Java
* Expression, not a statement as in Java
* Expression always has a value
* Kotlin control structures except loops are expressions
```Kotlin
fun min(a: Int, b:Int): Int {
	return if (a < b) a else b
}
```

--
### Class
* Previous examples work without a class declaration
* Necessary if you want to use multiple functions with the same name in different files
* Util functions that don't really belong to a specific class can exist without a 'Util' class
	* Under water a class is created based on the filename
	* All functions will be compiled to static functions in that class
* Functions outside a class are called top level functions
* Top level properties can be used as well
* Class is Public by default


--
### Multiple classes in one file
* File called FilenameDiffersFromClassname.kt contains

```Kotlin
class Answer {
    fun answer() = 42
}

class HalfAnswer {
    fun answer() = 21
}
```

--
### Files/directory structure
* Easiest to adhere to Java packages standard
* Kotlin also has the notion of a package declaration at the top of a Kotlin file
```Kotlin
package com.example
```

--
### Value object
* Constructor, getter and setter are generated
* Properties are called directly instead of through getter/setter
```Kotlin
class Employee(val firstName: String, var salary: Int)
```

--
### Value object test
```Kotlin
@Test
fun testEmployee() {
	val employee = Employee("Peter", 2500)
	assertEquals("Peter", employee.firstName)
	assertEquals(2500, employee.salary)
	employee.salary = 2600
	//employee.firstName = "John" // Val cannot be reassigned
}
```

--
### Custom getter
```Kotlin
class EmployeeCustomGetter(val firstName: String, var salary: Int) {
    val isHighSalary: Boolean
        get() {
            return salary > 3000
        }
    val isLowSalary: Boolean
        get() = salary < 2000
}
```

--
### Variables
```Kotlin
fun valVSvar() {
	var value1 = 3
	val value2 = 5

	value1 = 11
	value2 = 52
}
```
* Gives a message: Val cannot be reassigned

--
### Variables
* val ready only, is like a final variable in Java
* var writable, is like  a normal variable in Java
* Try to use val whenever possible
* Val makes the code immutable, a better fit to functional programming
* Val is immutable. But the object it refers to might change. For instance when it's a list and something is added.

--
### Val can be initialized later
```Kotlin
val boolean: Boolean
if (value1 < 4) {
	boolean = true
} else {
	boolean = false
}
```

--
### Variables
* Type information is optional
```Kotlin
var value1: Int = 3
```
* Type information is mandatory if the field isn't initialized immediately
```Kotlin
var value1: Int
value1 = 3
```

--
### Loops
* While is the same as in Java
	* while
	* do while
* One for loop exist: for each

--
### Ranges
* No variable initializing, updating and checking
* Ranges are used
* Ranges are so called closed or inclusive which means they include the last number
```Kotlin
val range = 1..10
```

--
### Loop example
```Kotlin
fun simpleLoop(): Int {
	var total = 0
	for (item in 1..4) {
		total += item
	}
	return total
}
```

--
### Steps example
```Kotlin
fun simpleLoopSteps(): Int {
	var total = 0
	for (item in 1..5 step 2) {
		total += item
	}
	return total
}
```

--
### Reverse loop example
```Kotlin
fun countDownLoop(): Int {
	var total = 0
	for (item in 3 downTo 1) {
		total += item
	}
	return total
}
```

--
### Exclusive loop example
```Kotlin
fun exclusiveLoop(): Int {
	var total = 0
	for (item in 1 until 3) {
		total += item
	}
	return total
}
```

--
### Map loop example
```Kotlin
fun mapLoop(): Int {
	val map = HashMap<Char, Int>()
	map.put('a', 10)
	map.put('b', 4)
	map.put('c', 2)
	map.put('z', 5)

	var total = 0
	for (char in 'a'..'c') {
		total += map[char]!! // !! to convert Int? to Int
	}
	return total
}
```

--
### Loop with index example
```Kotlin
fun loopWithIndex(names: Array<String>): Array<String> {
	var result = arrayOf<String>()
	for ((index, name) in names.withIndex()) {
		result += "Index $index has name $name"
	}
	return result
}
```

--
### String templates
* Like String concatenation in Java
```Kotlin
fun helloName(name: String): String {
	return "Hello $name"
}
```

--
### String templates
```Kotlin
fun helloNames(names: Array<String>): ArrayList<String> {
	val helloNames: ArrayList<String> = arrayListOf<String>()
	var index = 0
	while (index < names.size) {
		helloNames += "Hello ${names[index]} with index: $index"
		index++
	}
	return helloNames
}
```

--
### If
* Expression with a value
```Kotlin
fun verifyNumber(number: Int): String {
	val result = if (number > 0) {
		"Positive"
	} else {
		"Negative"
	}
	return result
}
```

--
### If
* Expression body instead of block body
```Kotlin
fun verifyNumberShort(number: Int): String =
	if (number > 0) "Positive" else "Negative"
```

--
### Simple Enum
* ```enum class``` instead of ```enum``` in Java
* ```enum``` is a soft keyword and can be used as a normal name in the application
* Using ```enum``` before class makes it an enum
```Kotlin
enum class LanguageSimpleEnum {
    DUTCH, ENGLISH, GERMAN
}
```

--
### Enum with property and method
```Kotlin
enum class CountryEnum(val countrycode: String) {
    NETHERLANDS("NL"), BELGIUM("BE"), ENGLAND("EN"), GERMANY("DE");

    fun retrieveCountryCodeText() = "Countrycode: $countrycode"
}
```
* Only place where a ```;``` is needed in Kotlin
	To separate the constants from the methods

--
### Enum test
```Kotlin
@Test
fun testLanguage() {
	assertEquals("NL", CountryEnum.NETHERLANDS.countrycode)
	assertEquals("Countrycode: NL", CountryEnum.NETHERLANDS.retrieveCountryCodeText())
}
```

--
### when
* When is used instead of ```switch``` in Java
* ```break``` is not needed as only the matching line is executed
```Kotlin
fun retrieveGoodmorning(country: CountryEnum): String =
	when (country) {
		CountryEnum.GERMANY -> "Guten morgen"
		CountryEnum.ENGLAND -> "Good morning"
		CountryEnum.BELGIUM, CountryEnum.NETHERLANDS -> "Goede morgen"
	}
```

--
### when with imported CountryEnum
```Kotlin
import com.example.CountryEnum
import com.example.CountryEnum.*

fun retrieveGoodmorning(country: CountryEnum): String =
	when (country) {
		GERMANY -> "Guten morgen"
		ENGLAND -> "Good morning"
		BELGIUM, NETHERLANDS -> "Goede morgen"
	}
```

--
### When range
```Kotlin
fun rangeCheck(value: Int): Boolean =
		when (value) {
			in 1..3 -> true
			else -> false
```

--
### When not in range
```Kotlin
fun whatDay(number: Int): String =
		when (number) {
			in 1..5 -> "Workday"
			!in 1..5 -> "Weekend"
			else -> "Something went wrong"
		}
```

--
### When type check
* Note the ```Any``` type instead of ```Object```
* Note the code block for ```else```
```Kotlin
fun typeCheck(value: Any): Boolean =
		when (value) {
			is Int -> true
			else -> {
				println("No boolean")
				false
			}
		}
```

--
### When instead of if-else-if
```Kotlin
fun ifElseIf(number: Int): String =
		when {
			number > 0 -> "Positive"
			number % 2 == 0 -> "Zero"
			number < 0 -> "Negative"
			else -> "Something went wrong"
		}
```
	
--
### Smart cast
* Type check and cast in one operation
* Check type by using ```is``` instead of ```instanceof```
* After that a cast is not needed

--
### Smart cast example
```Kotlin
fun smartcast(variable: Any): String {
	var result = ""
	if (variable is String) {
		result += variable.substring(6, 11)
	}
	if (variable is Int) {
		result += variable.div(2)
	}
	return result
}
```

--
### Exceptions
* Comparable to Java
	* Throw exception
	* Try, catch, finally
* But no difference between checked and unchecked exceptions
* Optionally handle any exception
* try is an expression and the value can be assigned to a variable

--
### Strings
* Kotlin hides some Java String functions
* Kotlin adds some String functions
* Example what does ```"hello.world".split(".")``` in Java?
* Returns 0 as "." is seen as a regular expression that matches any character

--
### Strings in Kotlin
* Provides overloaded extension methods
	* split, substring...
* ```String``` argument works as expected
* ```Regex``` argument is available for regular expressions
```Kotlin
val result = "hello.world".split(".")
assertEquals(2, result.size)
assertEquals("hello", result[0])
assertEquals("world", result[1])
```

--
### Split with multiple delimiters
```Kotlin
val result = "hello.world-today".split(".", "-")
assertEquals(3, result.size)
assertEquals("hello", result[0])
assertEquals("world", result[1])
assertEquals("today", result[2])
```

--
### Split with regular expression
```Kotlin
val result = "Monday Tuesday Wednesday".split("\\s".toRegex())
assertEquals(3, result.size)
assertEquals("Monday", result[0])
assertEquals("Tuesday", result[1])
assertEquals("Wednesday", result[2])
```

--
### Triple quotes
* To prevent escaping
```Kotlin
.split("""\s""".toRegex())
```

--
### Multiline strings
* Again with triple quotes
* Can contain String templates
```Kotlin
val text = """
	This is
	a
	long text
""".trimIndent()
assertEquals("This is\na\nlong text", text)
```

--
### Imports
* Possible to name imports and use it in the code
```Kotlin
import com.example.CountryEnum as Country

...

Country.NETHERLANDS
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Basics

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!

