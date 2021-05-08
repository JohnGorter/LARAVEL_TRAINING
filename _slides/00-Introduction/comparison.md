## Comparison to Java

--
## Some Java issues
Java suffers from:
- Null references are controlled by the type system
- No raw types

Kotlin has
- proper function types, as opposed to Java's SAM-conversions
- Use-site variance without wildcards

Kotlin does not have checked exceptions

--
## What Java has 
In Java
- Checked exceptions
- Primitive types that are not classes
    - the byte-code uses primitives where possible, but they are not explicitly available.
- Static members 
- Wildcard Types
- Terniary Operator

Kotlin
- companion objects, top-level functions, extension functions, or @JvmStatic
- declaration-site variance and type projections.
- if expressions

--
## What Kotlin has
Kotlin has
- Lambda expressions + Inline functions = performant custom control structures
- Extension functions
- Null-safety
- Smart casts
- String templates
- Properties
- Primary constructors

--
## What Kotlin has
Kotlin has
- First-class delegation
- Type inference for variable and property types
- Singletons
- Declaration-site variance & Type projections
- Range expressions
- Operator overloading
- Companion objects
- Data classes
- Separate interfaces for read-only and mutable collections
- Coroutines


--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lets dive in..

