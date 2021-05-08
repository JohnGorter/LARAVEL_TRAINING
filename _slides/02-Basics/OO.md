## Object oriented: interfaces, classes and objects


--
### Interface
```Kotlin
interface PrintInterface {
    var number: Int
    fun print(value: String): String
}
```

--
### Test
```Kotlin
val print = Print()

assertEquals(0, print.number)
assertEquals("hello Test 1", print.print("Test 1"))
assertEquals(1, print.number)
```

--
### Implement interface
* ```override``` is mandatory
```Kotlin
class Print : PrintInterface {
    override var number = 0
    override fun print(value: String): String {
        println("This is the $value")
        number++
        return "hello $value"
    }
}
```

--
### Default method in interface
* No need to use the ```default``` keyword
* If a class implements two interfaces with the same default method then the compiler issues a warning
```Kotlin
interface PrintInterface {
    var number: Int
    fun print(value: String): String
    fun defaultPrint(value:String): String = "default print $value"
}
```

--
### Class
* Can implement multiple interfaces
* Can extend one class
* ```final``` by default
* ```open``` can be used to extend the class

--
### Extend a class 1/2
```Kotlin
interface Interface {
    fun retrieveInt(): Int = 11
}
```

--
### Extend a class 2/2
```Kotlin
open class Base(): Interface {
    final override fun retrieveInt(): Int = 12
    open fun retrieveZero(): Int = 0
    fun retrieveOne(): Int = 1
}

class Extended: Base() {
    // override fun retrieveInt(): Int = 13 
    // 'retrieveInt' in 'Base' is final and cannot be overridden
    override fun retrieveZero(): Int = 42
    // override fun retrieveOne(): Int = 21
    // 'retrieveOne' in 'Base' is final and cannot be overridden
}
```

--
###
* Use open on a class so it can be extended in a subclass
* Use open on a method so it can be overridden in a subclass
* Use final on an override to make sure subclasses can't override it again
* Use abstract to create abstract (by default open) methods

--
### Visibility modifiers
* ```public```, ```protected``` and ```private```
* ```public``` is default
* ```package``` default from Java is not available
* ```internal``` visible in a module (set of Kotlin files compiled together)
	* For instance Maven or Gradle project
* protected is visible in the (sub)class not in the same package such as for Java

--
### Nested classes
* Same as Java
* But by default no access from the nested class to the outer class
* Reason is that a nested class is ```static``` by default
* Use ```inner``` so the nested class has access to the outer class

--
### Nested class test
```Kotlin
val outerClass = Outer()
assertEquals("Hello world", outerClass.helloWorld())
assertEquals("Nested: Hello world", outerClass.Nested().helloWorldNested())
```

--
### Nested class example
```Kotlin
class Outer {
    fun helloWorld(): String = "Hello world"

    inner class Nested() {
        val outer = this@Outer
        fun helloWorldNested(): String = "Nested: " + helloWorld()
    }
}
```

--
### Sealed classes
* All subclasses must be nested in the class
* Else clause not needed in a when expression for checking the various subclasses

--
### Sealed class example test
```Kotlin
assertEquals("1", whichClass(Sealed.SubClass1()))
assertEquals("2", whichClass(Sealed.SubClass2()))
```

--
### Sealed class example test
```Kotlin
sealed class Sealed {
    class SubClass1(): Sealed()
    class SubClass2(): Sealed()
}

fun whichClass(claz: Sealed): String =
    when(claz) {
        is Sealed.SubClass1 -> "1"
        is Sealed.SubClass2 -> "2"
		// no else needed
    }
```

--
### Data class
* Standard to implement toString(), equals() and hashcode()
* Add ```data``` for the class and Kotlin generates them
* Good practice to make the properties val so it's immutable

--
### Example data class
```Kotlin
data class DataClass(val value: String)
```

--
### Example data class test
```Kotlin
// toString() returns com.example.NormalClass@[object id]
assertEquals("com.example.NormalClass", normalClass.toString().substringBefore("@"))
assertEquals("DataClass(value=Data value)", dataClass.toString())
```

--
### Changing field
* Use the generated copy method to create a new object
```Kotlin
//dataClass.value = "test" // Val cannot be reassigned
val newDataClass = dataClass.copy(value="test")
assertEquals("test", newDataClass.value)
```

--
### Constructors
* Optionally use the ```private``` keyword
* Primary: declared outside the class
	* Part of the class header
	* After the class name and type parameters
	* Does not contain code
	* Code can be placed in initializer blocks with the ```init``` keyword
* Secondary: declared in the class

--
### Primary
```Kotlin
class Employee constructor(val firstName: String, var salary: Int)
```
* Simple version, when not using annotations or visibility modifiers 
```Kotlin
class Employee (val firstName: String, var salary: Int)
```

--
### Initializer blocks
```Kotlin
class Init(value: String) {
    var result = "";
    
    val value1 = "Value1: $value"
    init {
        result += value1
        println(result)
    }
    
    var value2 = " Value2: $value"
    init {
        result += value2
        println(result)
    }
}
```

--
### Initializer block test
```Kotlin
val init = Init("test")
assertEquals("Value1: test Value2: test", init.result)
```

--
### Secondary constructor example
```Kotlin
class SecondaryConstructor {
    var result = ""
    init {
        result += "init "
    }

    constructor(value: String) {
        result += value
    }
}
```Kotlin

--
### Secondary constructor example test
* Note the init block is called before the secondary constructor
```Kotlin
val secondaryConstructor = SecondaryConstructor("one")
assertEquals("init one", secondaryConstructor.result)
```

--
### Secondary constructor
* Not common
* When extending a class that has multiple constructors

--
### by
* Decorator pattern can be used to add functionality to a final class
* ```by``` keyword can be used to delegate the implementation of an interface to another class

--
### Decorator example
```Kotlin
class ListDecorator<E> : List<E> {
    override fun contains(element: E): Boolean
    override fun containsAll(elements: Collection<E>): Boolean
    override fun get(index: Int): E
    override fun indexOf(element: E): Int
    override fun isEmpty(): Boolean
    override fun iterator(): Iterator<E>
    override fun lastIndexOf(element: E): Int
    override fun listIterator(): ListIterator<E>
    override fun listIterator(index: Int): ListIterator<E>
    override fun subList(fromIndex: Int, toIndex: Int): List<E>
    override val size: Int
}
```

--
### by example
```Kotlin
class ListBy<E>(list: List<E> = ArrayList<E>()) : List<E> by list
```

--
### object
* The ```object``` keyword is used to declare a class and create an instance
	* To create a singleton
	* Companion object
	* Object expression
	
--
### Singleton
* object can also be placed inside a class
```Kotlin
object Singleton {
    val amount = 42;
    fun halfAmount() = amount / 2
}
```

--
### Singleton test
```Kotlin
assertEquals(21, Singleton.halfAmount())
val singleton1 = Singleton() 
// Expression 'Singleton' of type 'Singleton' cannot be 
// invoked as a function. The function 'invoke()' is not found
```


--
### Companion object
* Object inside class
	* Factory methods
* ```static``` keyword not part of Kotlin
* Instead there are top level functions
* Top level function cannot access private class fields
* Regular object
* Can implement an interface

--
### Companion object example
```Kotlin
class OuterClass {
    companion object CompanionObject {
        fun hello(): String = "Hello"
    }
}
```
```Kotlin
assertEquals("Hello world", OuterClass.CompanionObject.hello())
```

--
### Companion object example without name
* Use the default name ```Companion```
```Kotlin
class OuterClassWithoutCompanionObjectName {
    companion object {
        fun hello(): String = "Hello"
    }
}
```
```Kotlin
assertEquals("Hello world", OuterClassWithoutCompanionObjectName.Companion.hello())
```

--
### Companion object extension
```Kotlin
fun OuterClass.CompanionObject.goodbye(): String = "Goodbye"
```
```Kotlin
assertEquals("Goodbye", OuterClass.CompanionObject.goodbye())
```

--
### Object expression
* Anonymous object
* Comparable to Java's inner classes
```Kotlin
window.addMouseListener(object : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {
        // ...
    }

    override fun mouseEntered(e: MouseEvent) {
        // ...
    }
})
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. OO

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!