# Inline functions

--
## Inline functions
Using higher-order functions imposes certain runtime penalties
- each function is an object
- each function captures a closure

In many cases this kind of overhead can be eliminated by inlining the lambda expressions. 

--
## Inline functions
The functions shown below are good examples of this situation. 

The lock() function could be easily inlined at call-sites:
```
lock(l) { foo() }
```
Instead of creating a function object for the parameter and generating a call, the compiler could emit the following code:
```
l.lock()
try {
    foo()
} finally {
    l.unlock()
}
```

--
## Inline functions
To make the compiler do this, you need to mark the lock() function with the inline modifier:
```
inline fun <T> lock(lock: Lock, body: () -> T): T { ... }
```

The inline modifier affects both the function itself and the lambdas passed to it
- all will be inlined into the call site

Inlining may cause the generated code to grow

--
## Noinline
In case you want only some of the lambdas passed to an inline function to be inlined, you can mark some of your function parameters with the noinline modifier:
```
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) { ... }
```
Inlinable lambdas can only be called inside the inline functions or passed as inlinable arguments
Noinline ones can be manipulated in any way such as stored in fields or passed around

--
## Non-local returns
A bare return is forbidden inside a lambda because a lambda cannot make the enclosing function return:
```
fun foo() {
    ordinaryFunction {
        return // ERROR: cannot make `foo` return here
    }
}
```
But if the function the lambda is passed to is inlined:
```
fun foo() {
    inlined {
        return // OK: the lambda is inlined
    }
}
```

Such returns are called non-local returns

--
## Non-local returns
This sort of construct in loops:
```
fun hasZeros(ints: List<Int>): Boolean {
    ints.forEach {
        if (it == 0) return true // returns from hasZeros
    }
    return false
}
```

--
## Inline properties
The inline modifier can be used on accessors of properties that don't have a backing field:
```
val foo: Foo
    inline get() = Foo()

var bar: Bar
    get() = ...
    inline set(v) { ... }
```

--
## Inline properties
You can also annotate an entire property, which marks both of its accessors as inline:
```
inline var bar: Bar
    get() = ...
    set(v) { ... }
```
At the call site, inline accessors are inlined as regular inline functions.

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Inline functions

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!