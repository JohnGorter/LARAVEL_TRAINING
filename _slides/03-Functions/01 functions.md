# Functions

--
## Functions
Functions in Kotlin are declared using the fun keyword:
```
fun double(x: Int): Int {
    return 2 * x
}
```

--
## Function usage
Calling functions uses the traditional approach:
```
val result = double(2)
```
Calling member functions uses the dot notation:
```
Stream().read() // create instance of class Stream and call read()
```

-- 
## Parameters
Function parameters are defined using Pascal notation 
- name: type

Each parameter must be explicitly typed:
```
fun powerOf(number: Int, exponent: Int): Int { /*...*/ }
```

You can use a trailing comma when you declare function parameters:
```
fun powerOf(
    number: Int,
    exponent: Int, // trailing comma
) { /*...*/ }
```

--
## Default arguments
Function parameters can have default values, which are used when you skip the corresponding argument. 

Reduces a number of overloads compared to other languages:
```
fun read(
    b: ByteArray,
    off: Int = 0,
    len: Int = b.size,
) { /*...*/ }
```

--
## Default arguments
Overriding methods always use the same default parameter values as the base method. 

When overriding a method with default parameter values, the default parameter values must be omitted from the signature:
```
open class A {
    open fun foo(i: Int = 10) { /*...*/ }
}

class B : A() {
    override fun foo(i: Int) { /*...*/ }  // No default value is allowed.
}
```

--
## Default arguments
If a default parameter precedes a parameter with no default value, the default value can only be used by calling the function with named arguments:
```
fun foo(
    bar: Int = 0,
    baz: Int,
) { /*...*/ }

foo(baz = 1) // The default value bar = 0 is used
```

--
## Default arguments
If the last argument after default parameters is a lambda, you can pass it either as a named argument or outside the parentheses:
```
fun foo(
    bar: Int = 0,
    baz: Int = 1,
    qux: () -> Unit,
) { /*...*/ }

foo(1) { println("hello") }     // Uses the default value baz = 1
foo(qux = { println("hello") }) // Uses both default values bar = 0 and baz = 1
foo { println("hello") }        // Uses both default values bar = 0 and baz = 1
```

--
## Named arguments
When calling a function, you can name one or more of its arguments. 

Helpful when a function has a large number of arguments, and it's difficult to associate a value with an argument, especially if it's a boolean or null value.

When you use named arguments in a function call, you can
- freely change the order they are listed in 
- just leave them out altogether if you want to use the default values

--
## Named arguments
Given the following function reformat() that has 4 arguments with default values.
```
fun reformat(
    str: String,
    normalizeCase: Boolean = true,
    upperCaseFirstLetter: Boolean = true,
    divideByCamelHumps: Boolean = false,
    wordSeparator: Char = ' ',
) { /*...*/ }
```
When calling this function, you don’t have to name all its arguments:
```
reformat("String!",false,upperCaseFirstLetter = false,divideByCamelHumps = true,'_')
```
You can skip all arguments with default values:
```
reformat("This is a long String!")
```

--
## Named arguments
You can skip some arguments with default values. However, after the first skipped argument, you must name all subsequent arguments:

```
reformat("This is a short String!", upperCaseFirstLetter = false, wordSeparator = '_')
```

--
## Variable number of arguments
You can pass a variable number of arguments (vararg) with names using the spread operator:
```
fun foo(vararg strings: String) { /*...*/ }

foo(strings = *arrayOf("a", "b", "c"))  // star is spread operator
```

--
## Unit-returning functions
If a function does not return any useful value, its return type is Unit.

This value does not have to be returned explicitly:
```
fun printHello(name: String?): Unit {
    if (name != null)
        println("Hello $name")
    else
        println("Hi there!")
    // `return Unit` or `return` is optional
}
```
The Unit return type declaration is also optional. 
The above code is equivalent to:
```
fun printHello(name: String?) { ... }
```

--
## Single-expression functions
The curly braces can be omitted and the body is specified after a = symbol:
```
fun double(x: Int): Int = x * 2
```

Explicitly declaring the return type is optional when this can be inferred by the compiler:
```
fun double(x: Int) = x * 2
```

--
## Variable number of arguments (Varargs)
You can mark a parameter of a function (usually the last one) with the vararg modifier:
```
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}

val list = asList(1, 2, 3)
```

Only one parameter can be marked as vararg

--
## Variable number of arguments (Varargs)
When you call a vararg -function, you can pass arguments one-by-one
If you already have an array, use the spread operator
```
val a = arrayOf(1, 2, 3)
val list = asList(-1, 0, *a, 4)
```

--
## Infix notation
Functions marked with the infix keyword can also be called using the infix notation


Infix functions must meet the following requirements:
- they must be member functions or extension functions
- they must have a single parameter
- the parameter must not accept variable number of arguments 
- must have no default value

```
infix fun Int.shl(x: Int): Int { ... }

// calling the function using the infix notation
1 shl 2

// is the same as
1.shl(2)
```

--
## Infix notation
Infix function calls have lower precedence than the arithmetic operators, type casts, and the rangeTo operator. 

The following expressions are equivalent:
```
1 shl 2 + 3 is equivalent to 1 shl (2 + 3)

0 until n * 2 is equivalent to 0 until (n * 2)

xs union ys as Set<*> is equivalent to xs union (ys as Set<*>)
```

--
## Infix notation
On the other hand, infix function call's precedence is higher than that of the boolean operators && and ||, is- and in -checks,

These expressions are equivalent as well:
```
a && b xor c is equivalent to a && (b xor c)

a xor b in c is equivalent to (a xor b) in c
```

--
## Infix notation
Note that infix functions always require both the receiver and the parameter to be specified. When you're calling a method on the current receiver using the infix notation, use this explicitly. This is required to ensure unambiguous parsing.
```
class MyStringCollection {
    infix fun add(s: String) { /*...*/ }

    fun build() {
        this add "abc"   // Correct
        add("abc")       // Correct
        //add "abc"        // Incorrect: the receiver must be specified
    }
}

```
--
## Function scope
Kotlin functions can be 
- declared at the top level in a file
- declared locally, as member functions and extension functions

--
## Local functions
Functions inside another function:
```
fun dfs(graph: Graph) {
    fun dfs(current: Vertex, visited: MutableSet<Vertex>) {
        if (!visited.add(current)) return
        for (v in current.neighbors)
            dfs(v, visited)
    }

    dfs(graph.vertices[0], HashSet())
}
```

--
## Local functions
A local function can access local variables of outer functions (the closure). 
```
fun dfs(graph: Graph) {
    val visited = HashSet<Vertex>()
    fun dfs(current: Vertex) {
        if (!visited.add(current)) return
        for (v in current.neighbors)
            dfs(v)
    }

    dfs(graph.vertices[0])
}
```

--
## Member functions
A member function is a function that is defined inside a class or object:
```
class Sample {
    fun foo() { print("Foo") }
}
```
Member functions are called with dot notation:
```
Sample().foo() // creates instance of class Sample and calls foo
```


--
## Generic functions
Functions can have generic parameters
```
fun <T> singletonList(item: T): List<T> { /*...*/ }
```
More on this later

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Functions

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!