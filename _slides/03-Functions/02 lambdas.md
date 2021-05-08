## High-order functions and lambdas

Kotlin functions are first-class, they can be 
- stored in variables and data structures
- passed as arguments to and returned from other higher-order functions

--
## Lambdas
```
// Lambdas are code blocks enclosed in curly braces.
items.fold(0, { 
    // When a lambda has parameters, they go first, followed by '->'
    acc: Int, i: Int -> 
    print("acc = $acc, i = $i, ") 
    val result = acc + i
    println("result = $result")
    // The last expression in a lambda is considered the return value:
    result
})
​
// Parameter types in a lambda are optional if they can be inferred:
val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })
​
// Function references can also be used for higher-order function calls:
val product = items.fold(1, Int::times)
```

--
## Function types
Kotlin uses a family of function types like (Int) -> String for declarations that deal with functions
``` 
val onClick: () -> Unit = ....
```
These types have a special notation that corresponds to the signatures of the functions - their parameters and return values

--
## Function types
All function types have a parenthesized parameter types list and a return type: 
```
(A, B) -> C 
```
this denotes a type that represents functions taking two arguments of types A and B and returning a value of type C. 

The parameter types list may be empty, as in () -> A
The Unit return type cannot be omitted.

--
## Function types
Function types can optionally have an additional receiver type, which is specified before a dot in the notation
- A.(B) -> C represents functions that can be called on a receiver object of A with a parameter of B and return a value of C

Function literals with receiver are often used along with these types.

--
## Function types
The function type notation can optionally include names for the function parameters: 
- (x: Int, y: Int) -> Point

These names can be used for documenting the meaning of the parameters.

--
## Function types
To specify that a function type is nullable, use parentheses:
- ((Int, Int) -> Int)?

--
## Function types
Function types can be combined using parentheses: 
- (Int) -> ((Int) -> Unit)

The arrow notation is right-associative, (Int) -> (Int) -> Unit is equivalent to the previous example, but not to ((Int) -> (Int)) -> Unit.

--
## Function types
You can also give a function type an alternative name by using a type alias:
```
typealias ClickHandler = (Button, ClickEvent) -> Unit
```

--
## Instantiating a function type
There are several ways to obtain an instance of a function type:
- using a code block within a function literal, in one of the forms:
    - a lambda expression: { a, b -> a + b },
- an anonymous function: fun(s: String): Int { return s.toIntOrNull() ?: 0 }

Function literals with receiver can be used as values of function types with receiver.

Using a callable reference to an existing declaration:

a top-level, local, member, or extension function: ::isOdd, String::toInt,

a top-level, member, or extension property: List<Int>::size,

a constructor: ::Regex

These include bound callable references that point to a member of a particular instance: foo::toString.

Using instances of a custom class that implements a function type as an interface:

class IntTransformer: (Int) -> Int {
    override operator fun invoke(x: Int): Int = TODO()
}

val intFunction: (Int) -> Int = IntTransformer()

```
The compiler can infer the function types for variables if there is enough information:

val a = { i: Int -> i + 1 } // The inferred type is (Int) -> Int
```
Non-literal values of function types with and without receiver are interchangeable, so that the receiver can stand in for the first parameter, and vice versa. For instance, a value of type (A, B) -> C can be passed or assigned where a A.(B) -> C is expected and the other way around:

val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
val twoParameters: (String, Int) -> String = repeatFun // OK
​
fun runTransformation(f: (String, Int) -> String): String {
    return f("hello", 3)
}
val result = runTransformation(repeatFun) // OK
​
Target platform: JVMRunning on kotlin v.1.5.0
A function type with no receiver is inferred by default, even if a variable is initialized with a reference to an extension function. To alter that, specify the variable type explicitly.

--
## Invoking a function type instance
A value of a function type can be invoked by using its invoke(...) operator: 
- f.invoke(x) or just f(x)

If the value has a receiver type, the receiver object should be passed as the first argument. Another way to invoke a value of a function type with receiver is to prepend it with the receiver object, as if the value were an extension function: 
- 1.foo(2)

--
## Invoking a function type instance
Example:

```
val stringPlus: (String, String) -> String = String::plus
val intPlus: Int.(Int) -> Int = Int::plus
​
println(stringPlus.invoke("<-", "->"))
println(stringPlus("Hello, ", "world!")) 
​
println(intPlus.invoke(1, 1))
println(intPlus(1, 2))
println(2.intPlus(3)) // extension-like call

​```
