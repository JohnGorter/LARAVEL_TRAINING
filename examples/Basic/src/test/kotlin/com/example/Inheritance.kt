package com.example

interface Interface {
    fun retrieveInt(): Int = 11
}

open class Base(): Interface {
    final override fun retrieveInt(): Int = 12
    open fun retrieveZero(): Int = 0
    fun retrieveOne(): Int = 1
}

class Extended: Base() {
    // override fun retrieveInt(): Int = 13
    // 'retrieveInt' in 'Base' is final and cannot be overridden
    override fun retrieveZero(): Int = 42
    // override fun retrieveOne(): Int = 21
    // 'retrieveOne' in 'Base' is final and cannot be overridden
}


class InheritanceTest() {

}