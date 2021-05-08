# Extensions

--
## Extensions
Extend a class with new functionality without having to inherit from the class. This is done via special declarations called extensions.

- extension functions
- extension properties

--
## Extension functions
Extension function -> prefix its name with a receiver type

```
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}
```

The this keyword corresponds to the receiver object 

Now, you can call such a function on any MutableList<Int>:
```
val list = mutableListOf(1, 2, 3)
list.swap(0, 2) // 'this' inside 'swap()' will hold the value of 'list'
```

--
## Extension functions
You can make it generic:
```
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}
```

You declare the generic type parameter before the function name to make it available in the receiver type expression

--
## Extensions are resolved statically
- extensions do not actually modify classes they extend
- extension functions are dispatched statically

```
open class Shape
class Rectangle: Shape()
fun Shape.getName() = "Shape"
fun Rectangle.getName() = "Rectangle"
fun printClassName(s: Shape) {
    println(s.getName())
}    
printClassName(Rectangle())
```
This example prints Shape, because the called extension function depends only on the declared type of the parameter s, which is the Shape class.

--
## Extension precedence
If a class has a member function, and an extension function is defined which has the same receiver type, the same name, and is applicable to given arguments, the member always wins\
```
class Example {
    fun printFunctionType() { println("Class method") }
}
fun Example.printFunctionType() { println("Extension function") }
Example().printFunctionType()
```
This code prints Class method

--
## Extension method overloading
However, it's OK for extension functions to overload member functions
```
class Example {
    fun printFunctionType() { println("Class method") }
}
fun Example.printFunctionType(i: Int) { println("Extension function") }
Example().printFunctionType(1)
```

--
## Nullable receiver
Extensions can be defined with a nullable receiver type
```
fun Any?.toString(): String {
    if (this == null) return "null"
    // after the null check, 'this' is autocast to a non-null type, so the toString() below
    // resolves to the member function of the Any class
    return toString()
}
```

--
## Extension properties
Similarly to functions, Kotlin supports extension properties:
```
val <T> List<T>.lastIndex: Int
    get() = size - 1
```
Their behavior can only be defined by explicitly providing getters/setters.

Example:
```
val House.number = 1 // error: initializers are not allowed for extension properties
```

--
## Companion object extensions
If a class has a companion object defined, you can also define extension functions and properties for the companion object
```
class MyClass {
    companion object { }  // will be called "Companion"
}
​fun MyClass.Companion.printCompanion() { println("companion") }
fun main() {
    MyClass.printCompanion()
}
```

--
## Scope of extensions
In most cases, you define extensions on the top level - directly under packages:
```
package org.example.declarations

fun List<String>.getLongestString() { /*...*/}
```

To use such an extension outside its declaring package, import it at the call site:
```
package org.example.usage

import org.example.declarations.getLongestString

fun main() {
    val list = listOf("red", "green", "blue")
    list.getLongestString()
}
```

--
## Declaring extensions as members
Inside a class, you can declare extensions for another class. Inside such an extension, there are multiple implicit receivers- objects members of which can be accessed without a qualifier. 
```
class Host(val hostname: String) {
    fun printHostname() { print(hostname) }
}
​
class Connection(val host: Host, val port: Int) {
     fun printPort() { print(port) }
​
     fun Host.printConnectionString() {
         printHostname()   // calls Host.printHostname()
         print(":")
         printPort()   // calls Connection.printPort()
     }
​
     fun connect() {
         /*...*/
         host.printConnectionString()   // calls the extension function
     }
}
​
fun main() {
    Connection(Host("kotl.in"), 443).connect()
    //Host("kotl.in").printConnectionString(443)  // error, the extension function is unavailable outside Connection
}
```

--
## Declaring extensions as members
In case of a name conflict between the members of the dispatch receiver and the extension receiver, the extension receiver takes precedence

To refer to the member of the dispatch receiver, you can use the qualified this syntax

```
class Connection {
    fun Host.getConnectionString() {
        toString()         // calls Host.toString()
        this@Connection.toString()  // calls Connection.toString()
    }
}
```

--
## Declaring extensions as members
Extensions declared as members can be declared as open and overridden
Dispatch is 
- virtual to receiver type
- static to the extension receiver type
```
open class Base { }
class Derived : Base() { }
​
open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }
    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }
    fun call(b: Base) {
        b.printFunctionInfo()   // call the extension function
    }
}
​
class DerivedCaller: BaseCaller() {
    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }
    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}
​
fun main() {
    BaseCaller().call(Base())   // "Base extension function in BaseCaller"
    DerivedCaller().call(Base())  // "Base extension function in DerivedCaller" - dispatch receiver is resolved virtually
    DerivedCaller().call(Derived())  // "Base extension function in DerivedCaller" - extension receiver is resolved statically
}
```

--
## Visibility
Extensions utilize the same visibility of other entities as regular functions declared in the same scope would. 

- an extension declared at the top level of a file has access to the other private top-level declarations in the same file.
- if an extension is declared outside its receiver type, such an extension cannot access the receiver's private

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Extensions

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!