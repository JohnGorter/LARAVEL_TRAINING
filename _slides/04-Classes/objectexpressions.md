# Object expressions
Sometimes you need to create an object of a slight modification of some class, without explicitly declaring a new subclass for it

--
## Object expressions
Object expressions create objects of anonymous classes
- handy for one-time use

Instances of anonymous classes are also called anonymous objects because they are defined by an expression, not a name.

--
## Creating anonymous objects from scratch﻿
Object expressions start with the object keyword.

```
val helloWorld = object {
    val hello = "Hello"
    val world = "World"
    // object expressions extend Any, so `override` is required on `toString()`
    override fun toString() = "$hello $world" 
}
```

--
## Inheriting anonymous objects from supertypes
Specify this type after object and colon (: )
```
window.addMouseListener(object : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { /*...*/ }
    override fun mouseEntered(e: MouseEvent) { /*...*/ }
})
```

--
## Inheriting anonymous objects from supertypes
If a supertype has a constructor, appropriate constructor parameters must be passed to it. Many supertypes can be specified as a comma-delimited list after the colon:
```
open class A(x: Int) {
    public open val y: Int = x
}

interface B { /*...*/ }

val ab: A = object : A(1), B {
    override val y = 15
}
```

--
## Using anonymous object as return and value types
When an anonymous object is used as a type of a local or private but not inline declaration (function or property), all its members are accessible via this function or property:

class C {
    private fun getObject() = object {
        val x: String = "x"
    }

    fun printX() {
        println(getObject().x)
    }
}

--
## Using anonymous object as return and value types
If this function or property is public or private inline, its actual type is:
- any -> if the anonymous object doesn't have a declared supertype
- the declared supertype of the anonymous object if there is exactly one such type
- the explicitly declared type if there is more than one declared supertype

--
## Using anonymous object as return and value types
In all these cases, members added in the anonymous object are not accessible. Overriden members are accessible if they are declared in the actual type of the function or property:
```
interface A {
    fun funFromA() {}
}
interface B

class C {
    // The return type is Any. x is not accessible
    fun getObject() = object {
        val x: String = "x"
    }

    // The return type is A; x is not accessible
    fun getObjectA() = object: A {
        override fun funFromA() {}
        val x: String = "x"
    }

    // The return type is B; funFromA() and x are not accessible
    fun getObjectB(): B = object: A, B { // explicit return type is required
        override fun funFromA() {}
        val x: String = "x"
    }
}
```

--
## Accessing variables from anonymous objects
The code in object expressions can access variables from the enclosing scope:
```
fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }
        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
    // ...
}
```

--
## Object declarations
Singleton can be useful in several cases, and Kotlin (after Scala) makes it easy to declare singletons:
```
object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        // ...
    }

    val allDataProviders: Collection<DataProvider>
        get() = // ...
}
```
Object declaration, and name following the object keyword. 

--
## Object declarations
An object declaration is not an expression, and cannot be used on the right hand side of an assignment statement.

Object declaration's initialization is thread-safe and done at first access.

--
## Object declarations
To refer to the object, use its name directly:
```
DataProviderManager.registerDataProvider(...)
```
--
## Object declarations
Object declarations can have supertypes:
```
object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { ... }

    override fun mouseEntered(e: MouseEvent) { ... }
}
```

--
## Companion objects
An object declaration inside a class can be marked with the companion keyword:
```
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}
```
Members of the companion object can be called by using simply the class name as the qualifier:
```
val instance = MyClass.create()
```

--
## Companion objects
The name of the companion object can be omitted, in which case the name Companion will be used:
```
class MyClass {
    companion object { }
}

val x = MyClass.Companion
```

--
## Companion objects
Class members can access the private fields of a corresponding companion object.

The name of a class used by itself (not as a qualifier to another name) acts as a reference to the companion object of the class (whether named or not):
```
class MyClass1 {
    companion object Named { }
}

val x = MyClass1

class MyClass2 {
    companion object { }
}

val y = MyClass2
```

--
## Companion objects
Companion objects can implement interfaces:
```
interface Factory<T> {
    fun create(): T
}

class MyClass {
    companion object : Factory<MyClass> {
        override fun create(): MyClass = MyClass()
    }
}

val f: Factory<MyClass> = MyClass
```

--
## Semantic difference between object expressions and declarations
One important semantic difference between object expressions and object declarations:
- Object expressions are executed (and initialized) immediately, where they are used.
- Object declarations are initialized lazily, when accessed for the first time.

A companion object is initialized when the corresponding class is loaded (resolved), matching the semantics of a Java static initializer

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Object Expressions

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!