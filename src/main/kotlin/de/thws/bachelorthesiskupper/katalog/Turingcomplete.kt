package de.thws.bachelorthesiskupper.katalog

data class A(
    val a: List<Bool>
)

data class Bool(
    val bool: Boolean
)

data class B(
    val b: Int
)

fun turingcomplete(source: A): B {
    return B(source.a.filter { it.bool == true }.size)
}