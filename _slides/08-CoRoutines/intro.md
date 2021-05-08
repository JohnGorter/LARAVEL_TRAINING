# Coroutines
> a number of high-level coroutine-enabled primitives 

--
## Basics
A coroutine is an instance of suspendable computation. 

It is conceptually similar to a thread, in the sense that it takes a block of code to run that works concurrently with the rest of the code. However, a coroutine is not bound to any particular thread. It may suspend its execution in one thread and resume in another one.

--
## Basics
Coroutines can be thought of as light-weight threads, but there are differences

```
fun main() = runBlocking { // this: CoroutineScope
    launch { // launch a new coroutine and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello") // main coroutine continues while a previous one is delayed
}
```

You can get the full code here.

You will see the following result:

Hello
World!
Copied!
Let's dissect what this code does.

launch is a coroutine builder. It launches a new coroutine concurrently with the rest of the code, which continues to work independently. That's why Hello has been printed first.

delay is a special suspending function. It suspends the coroutine for a specific time. Suspending a coroutine does not block the underlying thread, but allows other coroutines to run and use the underlying thread for their code.

runBlocking is also a coroutine builder that bridges the non-coroutine world of a regular fun main() and the code with coroutines inside of runBlocking { ... } curly braces. This is highlighted in an IDE by this: CoroutineScope hint right after the runBlocking opening curly brace.

If you remove or forget runBlocking in this code, you'll get an error on the launch call, since launch is declared only in the CoroutineScope:

Unresolved reference: launch
Copied!
The name of runBlocking means that the thread that runs it (in this case — the main thread) gets blocked for the duration of the call, until all the coroutines inside runBlocking { ... } complete their execution. You will often see runBlocking used like that at the very top-level of the application and quite rarely inside the real code, as threads are expensive resources and blocking them is inefficient and is often not desired.

Structured concurrency﻿
Coroutines follow a principle of structured concurrency which means that new coroutines can be only launched in a specific CoroutineScope which delimits the lifetime of the coroutine. The above example shows that runBlocking establishes the corresponding scope and that is why the previous example waits until World! is printed after a second's delay and only then exits.

In the real application, you will be launching a lot of coroutines. Structured concurrency ensures that they are not lost and do not leak. An outer scope cannot complete until all its children coroutines complete. Structured concurrency also ensures that any errors in the code are properly reported and are never lost.

Extract function refactoring﻿
Let's extract the block of code inside launch { ... } into a separate function. When you perform "Extract function" refactoring on this code, you get a new function with the suspend modifier. This is your first suspending function. Suspending functions can be used inside coroutines just like regular functions, but their additional feature is that they can, in turn, use other suspending functions (like delay in this example) to suspend execution of a coroutine.

fun main() = runBlocking { // this: CoroutineScope
    launch { doWorld() }
    println("Hello")
}
​
// this is your first suspending function
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}
Target platform: JVMRunning on kotlin v.1.4.30
You can get the full code here.

Scope builder﻿
In addition to the coroutine scope provided by different builders, it is possible to declare your own scope using the coroutineScope builder. It creates a coroutine scope and does not complete until all launched children complete.

runBlocking and coroutineScope builders may look similar because they both wait for their body and all its children to complete. The main difference is that the runBlocking method blocks the current thread for waiting, while coroutineScope just suspends, releasing the underlying thread for other usages. Because of that difference, runBlocking is a regular function and coroutineScope is a suspending function.

You can use coroutineScope from any suspending function. For example, you can move the concurrent printing of Hello and World into a suspend fun doWorld() function:

fun main() = runBlocking {
    doWorld()
}
​
suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}
Target platform: JVMRunning on kotlin v.1.4.30
You can get the full code here.

This code also prints:

Scope builder and concurrency﻿
A coroutineScope builder can be used inside any suspending function to perform multiple concurrent operations. Let's launch two concurrent coroutines inside a doWorld suspending function:

// Sequentially executes doWorld followed by "Hello"
fun main() = runBlocking {
    doWorld()
    println("Done")
}
​
// Concurrently executes both sections
suspend fun doWorld() = coroutineScope { // this: CoroutineScope
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}
Target platform: JVMRunning on kotlin v.1.4.30
You can get the full code here.

Both pieces of code inside launch { ... } blocks execute concurrently, with World 1 printed first, after a second from start, and World 2 printed next, after two seconds from start. A coroutineScope in doWorld completes only after both are complete, so doWorld returns and allows Done string to be printed only after that:

Hello
World 1
World 2
Done
Copied!
An explicit job﻿
A launch coroutine builder returns a Job object that is a handle to the launched coroutine and can be used to explicitly wait for its completion. For example, you can wait for completion of the child coroutine and then print "Done" string:

val job = launch { // launch a new coroutine and keep a reference to its Job
    delay(1000L)
    println("World!")
}
println("Hello")
job.join() // wait until child coroutine completes
println("Done") 
Target platform: JVMRunning on kotlin v.1.4.30
You can get the full code here.

This code produces:

Hello
World!
Done
Copied!
Coroutines ARE light-weight﻿
Run the following code:

import kotlinx.coroutines.*

//sampleStart
fun main() = runBlocking {
    repeat(100_000) { // launch a lot of coroutines
        launch {
            delay(5000L)
            print(".")
        }
    }
}
//sampleEnd
Copied!
You can get the full code here.

It launches 100K coroutines and, after 5 seconds, each coroutine prints a dot.

Now, try that with threads (remove runBlocking, replace launch with thread, and replace delay with Thread.sleep ). What would happen? (Most likely your code will produce some sort of out-of-memory error)