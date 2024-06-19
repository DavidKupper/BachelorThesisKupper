package de.thws.bachelorthesiskupper.util

class MultiValueMap<K, V> {
    private val delegate: MutableMap<K, MutableList<V>> = mutableMapOf()

    val size get() = delegate.size
    val entries get() = delegate.entries.map { it.key to it.value.toList() }
    val keys get() = delegate.keys.toSet()
    val values get() = delegate.values.map { it.toList() }.toList() as Collection<List<V>>

    fun put(key: K, value: V) {
        if (delegate.containsKey(key)) {
            delegate[key]!!.add(value)
        } else {
            delegate[key] = mutableListOf(value)
        }
    }

    fun putAll(key: K, values: List<V>) {
        if (delegate.containsKey(key)) {
            delegate[key]!!.addAll(values)
        } else {
            delegate[key] = values.toMutableList()
        }
    }

    operator fun get(key: K): List<V> {
        if (!delegate.containsKey(key)) {
            return emptyList()
        }
        return delegate[key]!!.toList()
    }

    override fun toString(): String {
        return delegate.toString()
    }
}