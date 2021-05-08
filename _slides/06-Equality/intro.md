# Equality

--
## Types of equality
In Kotlin there are two types of equality
- Structural equality (==)
- Referential equality (===)

--
## Structural equality
Structural equality 
- == operation 
- != operation 

By convention, an expression like a == b is translated to:
```
a?.equals(b) ?: (b === null)
```

If a is not null, it calls the equals(Any?) function, otherwise (a is null) it checks that b is referentially equal to null.

--
## Structural equality

To provide a custom equals check implementation
- override the equals(other: Any?): Boolean function. 

Functions with the same name and other signatures, like equals(other: Foo), don't affect equality checks with the operators == and !=.

--
## Referential equality
Referential equality
- === operation 
- !== operation

a === b evaluates to true if a and b point to the same object

For primitive types (for example, Int )
  - === equality check is equivalent to the == check.

--
## Floating-point numbers equality
When an equality check operands are statically known to be Float or Double (nullable or not), the check follows the IEEE 754 Standard for Floating-Point Arithmetic.

Otherwise, the structural equality is used, which disagrees with the standard so that NaN is equal to itself, and -0.0 is not equal to 0.0.

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. NaN weirdness