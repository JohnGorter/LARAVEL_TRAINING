## Lambda


--
### Lambda
* Piece of code that can be used as an argument in a function
* Plays a large role in Kotlin
* Most notably in collections
* Done in the past with anonymous inner classes

--
### Lambda example
```Kotlin
val employees = listOf(
		Employee("Peter", 3000),
		Employee("Ann", 3500),
		Employee("Sara", 3200)
)
// '!!' because It should give a result
val highestPaidEmployee: Employee = employees.maxBy { it.salary }!!
assertEquals("Ann", highestPaidEmployee.firstName)
assertEquals(3500, highestPaidEmployee.salary)
```

--
### Lambda
* These are equivalent
```Kotlin
employees.maxBy { e: Employee -> e.salary }
```
```Kotlin
employees.maxBy() { it.salary }
```
```Kotlin
employees.maxBy { it.salary }
```

--
### Lambda example
* Code in the curly braces is a lambda
* ```it``` is the Employee object

--
### Lambda basics
* Curly braces around a Lambda expression

```Kotlin
val product = {x: Int, y: Int -> x*y}
assertEquals(6, product(2,3))
```

--
### Lambda with multiple statements
* Last one is the result
```Kotlin
val product = { x: Int, y: Int ->
	println("$x * $y = " + x*y)
	x*y
}
assertEquals(6, product(2,3))
```

--
### Using variables
```Kotlin
fun concatEmployeeNames(employees: List<Employee>, prefix: String) =
	employees.forEach{println("$prefix " + it.firstName) }
```

--
### Using variables
* Unlike Java we can change non-final local variables
```Kotlin
fun concatEmployeeNames(employees: List<Employee>, prefix: String) {
	var count = 0;
	employees.forEach{
		count++;
		println("$prefix " + it.firstName)
	}
	println("Number of employees: $count")
}
```

--
### Passing a function as argument
* Creating a Lambda that calls the function is not so nice
* As with Java 8 we can convert the function to a value with the ```::``` operator
* Known as a member reference
```Kotlin
employees.maxBy { e: Employee -> e.salary }
```
```Kotlin
employees.maxBy ( Employee::salary )
```

--
### Top level function as argument
```Kotlin
fun exampleFunction = ...
employees.maxBy ( ::exampleFunction )
```

--
### Useful functions: filter
* Loop through a collection and return the values for which the lambda is true
```Kotlin
val employees = listOf(
		Employee("Peter", 3000),
		Employee("Anna", 3500),
		Employee("Sara", 3200)
)
```
```Kotlin
val resultList = employees.filter { it.firstName.contains("a") }
assertEquals(2, resultList.size)
assertEquals("Anna", resultList[0].firstName)
assertEquals("Sara", resultList[1].firstName)
```

--
### Useful functions: map
* Change each value based on a function
```Kotlin
val resultList = employees.filter { it.firstName.contains("a") }.map { it.salary + 100 }
assertEquals(2, resultList.size)
assertEquals(3600, resultList[0])
assertEquals(3300, resultList[1])
```
```Kotlin
val resultListNames = employees.filter { it.firstName.contains("a") }.map(Employee::firstName)
```

--
### Extra check functions
* all() checks if all elements from the collection satisfy the condition
* any() checks if any element from the collection satisfies the condition
* count() counts how many elements from the collection satisfy the condition
* find() finds the first element from the collection that satisfies the condition

--
### Examples
```Kotlin
val positiveIncome = {e: Employee -> e.salary > 0}
assertTrue(employees.all(positiveIncome))
assertEquals(3, employees.count(positiveIncome))
assertEquals("Peter", employees.find(positiveIncome)!!.firstName)

val highIncome = {e: Employee -> e.salary > 3400}
assertTrue(employees.any(highIncome))
assertFalse(employees.all(highIncome))
```

--
### groupBy
* Create a map of groups from a list
```Kotlin
val employeesGroup = employees.groupBy { it.firstName.last() }
assertEquals("Peter", employeesGroup.get('r')!![0].firstName)

assertEquals("Anna", employeesGroup.get('a')!![0].firstName)
assertEquals("Sara", employeesGroup.get('a')!![1].firstName)
```



--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Lambda

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!



