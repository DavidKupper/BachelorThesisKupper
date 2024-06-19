package de.thws.bachelorthesiskupper.util

class Stack<T> {
    private val delegate = mutableListOf<T>()

    fun push(t: T) {
        delegate.addLast(t)
    }

    fun pop(): T {
        return delegate.removeLast()
    }

    fun peek(): T? {
        if (delegate.isEmpty()) {
            return null
        }
        return delegate.last()
    }
}