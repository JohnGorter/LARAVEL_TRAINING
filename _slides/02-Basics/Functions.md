## Functions


--
### Collections
* Standard Java collections
```Kotlin
fun createArrayList(): ArrayList<String> =
		arrayListOf("Deer", "Monkey", "Elephant")
		
fun createHashSet(): HashSet<String> =
		hashSetOf("Deer", "Monkey", "Elephant")

fun createHashMap(): HashMap<Int, String> =
		hashMapOf(1 to "Deer", 2 to "Monkey", 3 to "Elephant")
```

--
### Infix call
* ```to``` is an infix call a type of method invocation
* ```1.to("Deer")``` is the same as ```1 to "Deer"```

--
### Custom infix function
```Kotlin
infix fun String.containsInfix(substring: String) = contains(substring)
```
```Kotlin
assertTrue("Hello world" containsInfix "Hello")
assertTrue("Hello world" containsInfix "world")
assertFalse("Hello world" containsInfix "something")
```

--
### Destructing declaration
* Destructure an object into variables
* ```to``` function creates a ```Pair``` instance
* Elements from the ```Pair``` are destructured into separate values

--
### Destructing declaration example 
```Kotlin
@Test
fun testDestructingDeclaration() {
	val (animal, amount) = "deer" to 4
	assertEquals("deer", animal)
	assertEquals(4, amount)
}
```

--
### Destructure your own objects
* Two options:
	* Mark your class as a ```data class```
	* Declare the functions ```component1()``` etcetera in your class

--
### Destructure your own objects example
```Kotlin
data class PersonDataClass(val firstName: String, val age: Int)
```
```Kotlin
val person = PersonDataClass("Peter", 39)
val(firstName, age) = person
```

--
### Collections functionality
* Extra functionality (methods) are added to the Java collections such assertEquals
```Kotlin
@Test
fun retrieveLastFromAnimalsList() {
	assertEquals("Elephant", collections.createArrayListAnimals().last())
}

@Test
fun retrieveMaxFromNumbersList() {
	assertEquals("7", collections.createArrayListNumbers().max())
}

@Test
fun retrieveMinFromNumbersList() {
	assertEquals("1", collections.createArrayListNumbers().min())
}
```

--
### Named arguments
* Less confusing especially with multiple arguments (of the same type)
```Kotlin
fun print(name: String, value:Int): String = "The name $name has value $value"
```
```Kotlin
@Test
fun testPrint() {
	assertEquals("The name Hans has value 10", functions.print(name="Hans", value=10))
}
```

--
### Default values
```Kotlin
fun printDefault(name: String = "John Doe", value:Int = 42): String 
	= "The name $name has value $value"
```
```Kotlin
assertEquals("The name John Doe has value 42", 
	functions.printDefault())
assertEquals("The name Peter has value 42", 
	functions.printDefault(name="Peter"))
assertEquals("The name John Doe has value 3", 
	functions.printDefault(value=3))
assertEquals("The name Peter has value 3", 
	functions.printDefault(name="Peter", value=3))
```

--
### Extension function
* Called on a class
* Not defined in the class

--
### Extension function example
* Declare outside a class
```Kotlin
fun String.firstFive(): String = this.substring(0,5)
```
* String: receiver type
* this: receiver object

--
### Extension function example shorter
* ```this``` is not necessary
```Kotlin
fun String.firstFive(): String = substring(0,5)
```

--
### Extension function example test
```Kotlin
assertEquals("Hello", "Hello world".firstFive())
```
* String: receiver type
* "Hello world": receiver object

--
### Extension property
* Use the extension as a property instead of a function
* Shorter syntax
* No state despite the name

--
### Extension property example
* Possible to add a setter with ```set(..)```
```Kotlin
val String.firstFive: String 
        get() = substring(0,5)
```

--
### Extension property test
```Kotlin
@Test
fun testExtensionProperty() {
	assertEquals("Hello", "Hello world".firstFive)
}
```

--
### Spread operator
* Supply an Array to a Varargs in Java
* Kotlin needs an unpacked version of the Array
* Can be done with a spread operator 
```Kotlin
fun main(args: Array<String>) {
	val list = listOf("args: ", *args) 
}
```

--
### Local function
* It's possible to nest functions
* For instance to remove duplicate code

--
### Local function example test
```Kotlin
assertEquals("success", 
	localFunction.saveEmployee(EmployeeOptional("Peter", 25)))
assertEquals("failed salary is empty ", 
	localFunction.saveEmployee(EmployeeOptional("Peter")))
assertEquals("failed firstname is empty ", 
	localFunction.saveEmployee(EmployeeOptional(salary = 25)))
assertEquals("failed salary is empty failed firstname is empty ", 
	localFunction.saveEmployee(EmployeeOptional()))
```

--
### Local function example
```Kotlin
fun saveEmployee(employee: EmployeeOptional): String {
	fun validateField(value: Any?, fieldName: String): String {
		if (null == value) {
			println("failed $fieldName is empty ")
			return "failed $fieldName is empty "
		}
		return ""
	}
	var result = validateField(employee.salary, "salary")
	result += validateField(employee.firstName, "firstname")
	return if (result.isNotEmpty()) result else "success"
}
```

--
### Extension function
* It's possible to add the function as an extension to the EmployeeOptional class
* Doesn't feel like object oriented any more

--
### Custom getter setter
```Kotlin
class CustomGetterSetter {
    var result = ""

    var name = ""
        set(value: String) {
            result += " set to $value"
            field = value
        }
        get() {
            result += " get"
            return field
        }
}
```

--
### Private setter
```Kotlin
var secure = ""
	private set
```
```Kotlin
assertEquals("", custom.secure)
custom.secure = "" 
// Cannot assign to 'secure': the setter is private in 'CustomGetterSetter'
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Functions

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!