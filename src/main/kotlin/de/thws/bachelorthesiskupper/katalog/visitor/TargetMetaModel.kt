package de.thws.bachelorthesiskupper.katalog.visitor

class TargetRoot(
    var a1: String,
    val children: MutableList<TargetChild> = mutableListOf()
)

class TargetChild(
    var a1: String,
)