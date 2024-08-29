package de.thws.bachelorthesiskupper.katalog.transfomer

class SourceRoot(
    val a1: String,
    val children: List<SourceChild> = emptyList()
)

class SourceChild(
    val a1: String,
)