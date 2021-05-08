# Null Safety

> Eliminate NullPointerExceptions (NPE's)

--
## Nullable and Non-null types
Possible causes of NPE's may be:
- explicit call to throw NullPointerException()
- Usage of the !! operator

Some data inconsistency with regard to initialization, such as when:
- an uninitialized this available in a constructor is passed and used somewhere ("leaking this ")
- a superclass constructor calls an open member whose implementation in the derived class uses uninitialized state

Java interoperation:
- attempts to access a member on a null reference of a platform type;
- generic types used for Java interoperation with incorrect nullability, for example, a piece of Java code might add null into a Kotlin MutableList<String>, meaning that MutableList<String?> should be used for working with it.
- other issues caused by external Java code.

--
## Nullable and Non-null types
Type system of Kotlin distinguishes between
- references that can hold null (nullable references) 
- references that cannot (non-null references)

```
var a: String = "abc" // Regular initialization means non-null by default
a = null // compilation error
```

To allow nulls, you can declare a variable as nullable string, written String?:
```
var b: String? = "abc" // can be set null
b = null // ok
print(b.length)  // error: variable 'b' can be null
```

--
## Nullable and Non-null types
If you still need to access that property, you can
- check for null in conditions
- use safe calls
- !! operator
- Safe casts

--
## Nullable and Non-null types
First option: check for null in conditions
```
val l = if (b != null) b.length else -1
```
or
```
val b: String? = "Kotlin"
if (b != null && b.length > 0) {
    print("String of length ${b.length}")
} else {
    print("Empty string")
}
```

Note that this only works where b is immutable (that means a local variable which is not modified between the check and the usage or a member val which has a backing field and is not overridable), because otherwise it might happen that b changes to null after the check.

--
## Safe Calls
Your second option is the safe call operator, written ?.:
```
val a = "Kotlin"
val b: String? = null
println(b?.length)
println(a?.length) // Unnecessary safe call
```

This returns b.length if b is not null, and null otherwise. The type of this expression is Int?.

--
## Safe Calls
Safe calls are useful in chains
```
bob?.department?.head?.name
```
Chain returns null if any of the properties in it is null.

--
## Safe Calls and Let

To perform a certain operation only for non-null values, you can use the safe call operator together with let
```
val listWithNulls: List<String?> = listOf("Kotlin", null)
for (item in listWithNulls) {
    item?.let { println(it) } // prints Kotlin and ignores null
}
```

--
## Safe Calls and Let

A safe call can also be placed on the left side of an assignment
```
// If either `person` or `person.department` is null, the function is not called:
person?.department?.head = managersPool.getManager()
```

--
## Elvis operator
When you have a nullable reference b, you can say "if b is not null, use it, otherwise use some non-null value":
```
val l: Int = if (b != null) b.length else -1
// can be written as
val l = b?.length ?: -1
```

--
## Elvis operator
Since throw and return are expressions in Kotlin, they can also be used on the right hand side of the elvis operator. 

Handy, for example, for checking function arguments:
```
fun foo(node: Node): String? {
    val parent = node.getParent() ?: return null
    val name = node.getName() ?: throw IllegalArgumentException("name expected")
    // ...
}
```

--
## !! operator
Converts any value to a non-null type and throws an exception if the value is null
- b!! will return a non-null value of b or throw an NPE if b is null:
```
val l = b!!.length
```
If you want an NPE, you can have it, but you have to ask for it explicitly, and it does not appear out of the blue!

--
## Safe casts
Regular casts may result into a ClassCastException if the object is not of the target type. 

An option is to use safe casts that return null if the attempt was not successful:
```
val aInt: Int? = a as? Int
```

--
## Collections of a nullable type
Filter non-null elements, by using filterNotNull:
```
val nullableList: List<Int?> = listOf(1, 2, null, 4)
val intList: List<Int> = nullableList.filterNotNull()
```


--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Nullable Types

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!
