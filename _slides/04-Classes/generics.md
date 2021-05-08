# Generics

--
## Generics
As in Java, classes in Kotlin can have type parameters:
```
class Box<T>(t: T) {
    var value = t
}
```
In general, to create an instance of such a class, provide the type arguments:
```
val box: Box<Int> = Box<Int>(1)
```
Often you can omit the type arguments:
```
val box = Box(1) // 1 has type Int, so the compiler figures out that it is Box<Int>
```

--
## Variance
Generic types in Java are invariant
- List<String> is not a subtype of List<Object> 

Why so? If List was not invariant, it would have been no better than Java's arrays, since the following code would have compiled and caused an exception at runtime:
```
// Java
List<String> strs = new ArrayList<String>();
List<Object> objs = strs; // !!! A compile-time error here saves us from a runtime exception later.
objs.add(1); // Put an Integer into a list of Strings
String s = strs.get(0); // !!! ClassCastException: Cannot cast Integer to String
```
--
## Variance
Java prohibits such things in order to guarantee run-time safety. But this has some implications. For example, consider the addAll() method from Collection interface. What's the signature of this method? Intuitively, you'd put it this way:
```
// Java
interface Collection<E> ... {
    void addAll(Collection<E> items);
}
```
But then, you are not able to do the following simple thing (which is perfectly safe):
```
// Java
void copyAll(Collection<Object> to, Collection<String> from) {
    to.addAll(from);
    // !!! Would not compile with the naive declaration of addAll:
    // Collection<String> is not a subtype of Collection<Object>
}
```
--
## Variance
That's why the actual signature of addAll() is the following:
```
// Java
interface Collection<E> ... {
    void addAll(Collection<? extends E> items);
}
```


The key to understanding why this trick works is rather simple:
> if you can only take items from a collection, then using a collection of String s and reading Object s from it is fine. Conversely,
> if you can only put items into the collection, it's OK to take a collection of Object s and put String s into it: there is List<? super String> in Java, a supertype of List<Object>

The latter is called contravariance, and you can only call methods that take String as an argument on List<? super String> (for example, you can call add(String) or set(int, String) ). If you call something that returns T in List<T>, you don't get a String, but an Object.

--
## declaration-site variance
Let's suppose that there is a generic interface Source<T> that does not have any methods that take T as a parameter, only methods that return T:
```
// Java
interface Source<T> {
  T nextT();
}
```
Then, it would be perfectly safe to store a reference to an instance of Source<String> in a variable of type Source<Object>- there are no consumer-methods to call. But Java does not know this, and still prohibits it:
```
// Java
void demo(Source<String> strs) {
  Source<Object> objects = strs; // !!! Not allowed in Java
  // ...
}
```
To fix this, you should declare objects of type Source<? extends Object>, which is meaningless, because you can call all the same methods on such a variable as before, so there's no value added by the more complex type. But the compiler does not know that.

--
## declaration-site variance
In Kotlin you can annotate the type parameter T of Source to make sure that it is only returned (produced) from members of Source<T>, and never consumed. To do this, use the out modifier:
```
interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
    // ...
}
```

--
## declaration-site variance
The general rule is: 
- when a type parameter T of a class C is declared out, it may occur only in out -position in the members of C, but in return C<Base> can safely be a supertype of C<Derived>.

In other words, you can say that the class C is covariant in the parameter T, or that T is a covariant type parameter. You can think of C as being a producer of T 's, and NOT a consumer of T 's.


The out modifier is called a variance annotation

--
## declaration-site variance
In addition to out, Kotlin provides a complementary variance annotation: in. 

This makes a type parameter contravariant: it can only be consumed and never produced. 

A good example of a contravariant type is Comparable:

```
interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
}
```
--
## Use-site variance: type projections
It is very convenient to declare a type parameter T as out and avoid trouble with subtyping on the use site, but some classes can't actually be restricted to only return T 's! A good example of this is Array:
```
class Array<T>(val size: Int) {
    fun get(index: Int): T { ... }
    fun set(index: Int, value: T) { ... }
}
```
This class cannot be either co- or contravariant in T. And this imposes certain inflexibilities. Consider the following function:
```
fun copy(from: Array<Any>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}
```
--
## Use-site variance: type projections
This function is supposed to copy items from one array to another. Let's try to apply it in practice:
```
val ints: Array<Int> = arrayOf(1, 2, 3)
val any = Array<Any>(3) { "" }
copy(ints, any)
//   ^ type is Array<Int> but Array<Any> was expected
```
Here you run into the same familiar problem: Array<T> is invariant in T, thus neither of Array<Int> and Array<Any> is a subtype of the other. 

--
## Use-site variance: type projections
To prohibit the copy function from writing to from, do the following:
```
fun copy(from: Array<out Any>, to: Array<Any>) { ... }
```
This is type projection, that means that from is not simply an array, but a restricted (projected) one.

You can only call those methods that return the type parameter T, in this case it means that you can only call get(). 

--
## Use-site variance: type projections
You can project a type with in as well:
```
fun fill(dest: Array<in String>, value: String) { ... }
```
Array<in String> corresponds to Java's Array<? super String>. This means that you can pass an array of CharSequence or an array of Object to the fill() function.

--
## Star-projections
Sometimes you want to say that you know nothing about the type argument, but still want to use it in a safe way. The safe way here is to define such a projection of the generic type, that every concrete instantiation of that generic type would be a subtype of that projection.

Kotlin provides so called star-projection syntax for this:
```
For Foo<out T : TUpper>
```
where T is a covariant type parameter with the upper bound TUpper, Foo<*> is equivalent to Foo<out TUpper>. It means that when the T is unknown you can safely read values of TUpper from Foo<*>.

For Foo<in T>, where T is a contravariant type parameter, Foo<*> is equivalent to Foo<in Nothing>. It means there is nothing you can write to Foo<*> in a safe way when T is unknown.

For Foo<T : TUpper>, where T is an invariant type parameter with the upper bound TUpper, Foo<*> is equivalent to Foo<out TUpper> for reading values and to Foo<in Nothing> for writing values.

If a generic type has several type parameters each of them can be projected independently. For example, if the type is declared as interface Function<in T, out U> you can imagine the following star-projections:

Function<*, String> means Function<in Nothing, String>.

Function<Int, *> means Function<Int, out Any?>.

Function<*, *> means Function<in Nothing, out Any?>.

Star-projections are very much like Java's raw types, but safe.

--
## Generic functions
Not only classes can have type parameters. Functions can, too. Type parameters are placed before the name of the function:
```
fun <T> singletonList(item: T): List<T> {
    // ...
}

fun <T> T.basicToString(): String {  // extension function
    // ...
}
```
To call a generic function, specify the type arguments at the call site after the name of the function:
```
val l = singletonList<Int>(1)
```

--
## Generic functions
Type arguments can be omitted if they can be inferred from the context, so the following example works as well:
```
val l = singletonList(1)
```

--
## Generic constraints
The set of all possible types that can be substituted for a given type parameter may be restricted by generic constraints.

--
## Upper bounds
The most common type of constraint is an upper bound that corresponds to Java's extends keyword:
```
fun <T : Comparable<T>> sort(list: List<T>) {  ... }
```
The type specified after a colon is the upper bound: only a subtype of Comparable<T> can be substituted for T. For example:
```
sort(listOf(1, 2, 3)) // OK. Int is a subtype of Comparable<Int>
sort(listOf(HashMap<Int, String>())) // Error: HashMap<Int, String> is not a subtype of Comparable<HashMap<Int, String>>
```

--
## Upper bounds
The default upper bound (if none specified) is Any?

Only one upper bound can be specified inside the angle brackets

If the same type parameter needs more than one upper bound, you need a separate where -clause:
```
fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
    where T : CharSequence,
          T : Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}
```

The passed type must satisfy all conditions of the where clause simultaneously. In the above example, the T type must implement both CharSequence and Comparable.


