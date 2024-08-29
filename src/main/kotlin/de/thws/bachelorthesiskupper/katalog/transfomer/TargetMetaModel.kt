package de.thws.bachelorthesiskupper.katalog.transfomer

class TargetRoot(
    val a1: String,
    val children: List<TargetChild> = emptyList()
)

class TargetChild(
    val a1: String,
)