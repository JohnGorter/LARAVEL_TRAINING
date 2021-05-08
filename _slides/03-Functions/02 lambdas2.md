## High-order functions and lambdas

--
## Inline functions

Sometimes it is beneficial to use inline functions, which provide flexible control flow, for higher-order functions.

--
## Lambda expressions and anonymous functions
Lambda expressions and anonymous functions are function literals. Function literals are functions that are not declared but passed immediately as an expression:
```
max(strings, { a, b -> a.length < b.length })
```

Function max is a higher-order function, it takes a function value as the second argument

--
## Lambda expression syntax
The full syntactic form of lambda expressions is as follows:
```
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
```
- a lambda expression is always surrounded by curly braces
- parameter declarations in the full syntactic form go inside curly braces and have optional type annotations
- the body goes after an -> sign
- if the inferred return type of the lambda is not Unit, the last expression inside the lambda body is the return value

--
## Lambda expression syntax
If you leave all the optional annotations out, what's left looks like this:
```
val sum = { x: Int, y: Int -> x + y }
```

--
## Passing trailing lambdas
In Kotlin, there is a convention: 
- if the last parameter of a function is a function, then a lambda expression passed as the corresponding argument can be placed outside the parentheses
```
val product = items.fold(1) { acc, e -> acc * e }
```
Such syntax is also known as trailing lambda.

If the lambda is the only argument to that call, the parentheses can be omitted entirely:
```
run { println("...") }
```

--
## implicit name of a single parameter
It's very common that a lambda expression has only one parameter.

If the compiler can figure the signature out itself, it is allowed not to declare the only parameter and omit ->. 
```
ints.filter { it > 0 } // this literal is of type '(it: Int) -> Boolean'
```

--
## Returning a value from a lambda expression
You can explicitly return a value from the lambda using the qualified return syntax. Otherwise, the value of the last expression is implicitly returned.

```
ints.filter {
    val shouldFilter = it > 0
    shouldFilter
}

ints.filter {
    val shouldFilter = it > 0
    return@filter shouldFilter
}
```
This convention, along with passing a lambda expression outside parentheses, allows for LINQ-style code:
```
strings.filter { it.length == 5 }.sortedBy { it }.map { it.uppercase() }
```

--
## Underscore for unused variables
If the lambda parameter is unused, you can place an underscore instead of its name:
```
map.forEach { _, value -> println("$value!") }
```

--
## Anonymous functions
If you do need to specify return type, you can use an anonymous function
```
fun(x: Int, y: Int): Int = x + y
```
An anonymous function looks very much like a regular function declaration, except that its name is omitted. Its body can be either an expression (as shown above) or a block:
```
fun(x: Int, y: Int): Int {
    return x + y
}
```

--
## Anonymous functions
The parameters and the return type are specified in the same way as for regular functions, except that the parameter types can be omitted if they can be inferred from context:
```
ints.filter(fun(item) = item > 0)
```

The return type inference for anonymous functions works just like for normal functions: the return type is inferred automatically for anonymous functions with an expression body and has to be specified explicitly (or is assumed to be Unit) for anonymous functions with a block body

--
## Anonymous functions vs Lambdas
- anonymous function parameters are always passed inside the parentheses
- a return inside a lambda expression will return from the enclosing function, whereas a return inside an anonymous function will return from the anonymous function itself

--
## Closures
A lambda expression or anonymous function (as well as a local function and an object expression) can access its closure
- the variables declared in the outer scope

The variables captured in the closure can be modified in the lambda:
```
var sum = 0
ints.filter { it > 0 }.forEach {
    sum += it
}
print(sum)
```

--
## Function literals with receiver
Function types with receiver, such as A.(B) -> C, can be instantiated with a special form of function literals 
- function literals with receiver

Inside the body of the function literal, the receiver object passed to a call becomes an implicit this:
```
val sum: Int.(Int) -> Int = { other -> plus(other) }
```
The anonymous function syntax allows you to specify the receiver type of a function literal directly. This can be useful if you need to declare a variable of a function type with receiver, and to use it later.
```
val sum = fun Int.(other: Int): Int = this + other
```
--
## Function literals with receiver
Lambda expressions can be used as function literals with receiver when the receiver type can be inferred from context. One of the most important examples of their usage is type-safe builders:
```
class HTML {
    fun body() { ... }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()  // create the receiver object
    html.init()        // pass the receiver object to the lambda
    return html
}

html {       // lambda with receiver begins here
    body()   // calling a method on the receiver object
}
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Lambdas

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!