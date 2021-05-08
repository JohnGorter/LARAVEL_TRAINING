package com.example

import java.util.*

//class ListDecorator<E> : List<E> {
//    override fun contains(element: E): Boolean
//    override fun containsAll(elements: Collection<E>): Boolean
//    override fun get(index: Int): E
//    override fun indexOf(element: E): Int
//    override fun isEmpty(): Boolean
//    override fun iterator(): Iterator<E>
//    override fun lastIndexOf(element: E): Int
//    override fun listIterator(): ListIterator<E>
//    override fun listIterator(index: Int): ListIterator<E>
//    override fun subList(fromIndex: Int, toIndex: Int): List<E>
//    override val size: Int
//}

class ListBy<E>(list: List<E> = ArrayList<E>()) : List<E> by list