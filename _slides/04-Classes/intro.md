## Classes

'A class is a blueprint for an object in Kotlin. It shares common properties and behaviour in form of members and member functions.'

In Kotlin, a class is declared with the class keyword.

--
## Classes

The class declaration consists of the class name, the class header (specifying its type parameters, the primary constructor etc.) and the class body. 

The class body is specified inside two curly braces. Both the header and the body are optional. If the class has no body, curly braces can be omitted.

--
## Classes Example
```
SimpleClass.kt
package com.zetcode

class Simple {
    private val name = "Simple"
    fun info() = "This is $name class"
}

fun main() {
    val s = Simple() // we don't use the new keyword 
    println(s)
    println(s.info())
}
```

--
## Kotlin empty class

```
EmptyClass.kt
package com.zetcode

class Being {}
class Empty

fun main() {
    val b = Being()
    println(b)
    val e = Empty()
    println(e)
}
```
An empty class has no members or member functions. The curly brackets can be omitted.

--
## Constructors
A Kotlin class can have 
- primary constructor 
- one or more secondary constructors

The primary constructor is part of the class header: it goes after the class name (and optional type parameters).

--
### Constructors Example
Here we use the constructor keyword.
```
class User constructor(name: String, email: String) {  }
```

If the primary constructor does not have any annotations or visibility modifiers (such as public), the constructor keyword can be omitted.
```
class User (name: String, email: String) {  }
```

--
## Constructors

The primary constructor cannot contain any code. 

The initialization code can be placed in initializer blocks, they are created with the init keyword.
```
PrimaryConstructor.kt
package com.zetcode

//class User constructor (_name: String, _email: String) {
class User(name: String, email: String) {

    private val name = name
    private val email = email
    
    init {
        println("First initializer block that prints ${name}")
    }

    override fun toString(): String {
        return "User $name has email $email"
    }
}

fun main() {
    val u = User("Peter Novak", "pnovak47@gmail.com")
    println(u)
}
```

--
## Constructors
For declaring properties and initializing them from the primary constructor, Kotlin has a concise syntax:

```
class Person(val firstName: String, val lastName: String, var age: Int) { /*...*/ }
```

--
## Secondary constructors
The class can also declare secondary constructors, which are prefixed with constructor:
```
class Person(val pets: MutableList<Pet> = mutableListOf())

class Pet {
    constructor(owner: Person) {
        owner.pets.add(this) // adds this pet to the list of its owners pets
    }
}
```
--
## Secondary constructors
If the class has a primary constructor, each secondary constructor needs to delegate to the primary constructor:
```
class Person(val name: String) {
    var children: MutableList<Person> = mutableListOf()
    constructor(name: String, parent: Person) : this(name) {
        parent.children.add(this)
    }
}
```

--
## Open class
Kotlin classes are final by default.

To make a class inheritable, we mark it with the open keyword.
```
OpenClass.kt
package com.zetcode

open class Being(private val alive: Boolean = true) {
    fun isAlive(): Boolean {
        return alive
    }
}

class Dog(val name: String): Being() {
    fun bark(): String {
        return "woof-woof"
    }
}

fun main() {
    val d = Dog("Rusty")
    println(d.bark())
    println(d.name)
    println(d.isAlive())
}
```

--
## Data classes
Some classes are designed to hold data. 

Data classes, considerably reduce boilerplate code. 
Compiler automatically creates 
- equals 
- hashCode
- toString
- copy functions

A data class in Kotlin is created with the data keyword.

--
## Data classes

Data classes
- the primary constructor needs to have at least one parameter
- all primary constructor parameters must be marked as val or var

Data classes cannot be 
- abstract
- open
- sealed
- inner

--
## Data classes Example
```
DataClass.kt
package com.zetcode

data class User(val name: String, val email: String)

fun main() {

    val u = User("Peter Novak", "pnovak47@gmail.com")
    println(u)

    println(u.name)
    println(u.email)

    val (name, email) = u;
    println("$name $email")

    val u2 = User("Peter Novak", "pnovak47@gmail.com")

    println(u == u2)
    println(u === u2)
}
```

--
## Nested classes
A nested class is declared inside another class

```
NestedClass.kt
package com.zetcode

class Outer {

    val name = "Outer"
    fun show() = "the name: $name"

    class Nested {
        val name = "Nested"
        fun show() = "the name: $name"
    }
}

fun main() {

    println(Outer().show())
    println(Outer.Nested().show())
}
```

--
## Inner class
Inner classes are created with the inner keyword
Unlike nested classes, they can access the members of their outer classes

```
InnerClass.kt
package com.zetcode

class Outer {

    val name1 = "Outer"
    fun show() = "the name: $name1"

    inner class Inner {

        val name2 = "Inner"
        fun show() = "data: $name2 and $name1"
    }
}

fun main() {

    println(Outer().show())
    println(Outer().Inner().show())
}
```

--
### Abstract class
If a class inherits from an abstract class, it must implement all its abstract members and member functions

Abstract classes are 
- implicitly open
- similar to an interface
 - unlike an interface, an abstract class can have state. 
 
While a class can implement multiple interfaces, it can inherit only from one abstract class.

--
### Abstract classes example
```
AbstractClass.kt
package com.zetcode

abstract class Shape() {
    abstract var w: Int
    abstract var h: Int
    abstract fun area(): Int
    fun info(): String {
        return "width: $w; height: $h"
    }
}

class Rectangle(override var w: Int, override var h: Int): Shape() {
    override fun area(): Int {
        return w * h;
    }
}

fun main() {
    val r = Rectangle(5, 6)
    println(r.area())
    println(r.info());
}
```

--
## Sealed classes
A sealed class is used for representing restricted class hierarchies. 

A value can have one of the types from a limited set, but cannot have any other type. 

Sealed classes are more powerful enum classes and can contain state.

> In some languages, such as C# or Scala, a sealed class is a class which prohibits inheritance.

--
## Sealed classes
Sealed classes are abstract and can
- have abstract members
- have subclasses
    - they must either be in the same file or nested inside of the sealed class declaration

they cannot 
- be instantiated directly
- have public constructors 

--
## Sealed classes Example
```
SealedClass.kt
package com.zetcode

sealed class Shape
class Circle(var radius: Float) : Shape()
class Square(var width: Int) : Shape()
class Rectangle(var width: Int, var height: Int) : Shape()

fun getArea(e: Shape) =
    when (e) {
        is Circle -> println("Circle area is ${Math.PI * e.radius * e.radius}")
        is Square -> println("Square area is ${e.width * e.width}")
        is Rectangle -> println("Rectangle area is ${e.width * e.height}")
    }

fun main() {
    val circle = Circle(7f)
    val square = Square(5)
    val rectangle = Rectangle(8, 6)

    getArea(circle)
    getArea(square)
    getArea(rectangle)
}
```
--
## Sealed classes Example
```
package com.zetcode

sealed class Shape {
    class Circle(var radius: Float) : Shape()
    class Square(var width: Int) : Shape()
    class Rectangle(var width: Int, var height: Int) : Shape()
}

fun getArea(e: Shape) =
    when (e) {
        is Shape.Circle -> println("Circle area is ${Math.PI * e.radius * e.radius}")
        is Shape.Square -> println("Square area is ${e.width * e.width}")
        is Shape.Rectangle -> println("Rectangle area is ${e.width * e.height}")
    }

fun main() {
    val circle = Shape.Circle(7f)
    val square = Shape.Square(5)
    val rectangle = Shape.Rectangle(8, 6)

    getArea(circle)
    getArea(square)
    getArea(rectangle)
}
```

In this example, the subclasses are nested inside the Shape class.


--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Abstract classes

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!