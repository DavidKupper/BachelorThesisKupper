package de.thws.bachelorthesiskupper.visitorpattern2.metamodel.source

class M1Class(
    val name: String,
    val attributes: MutableSet<M1Attribute>,
)

class M1Attribute(
    val name: String,
    val attributeType: AttributeType
)

enum class AttributeType {
    INT,
    BOOL,
    STRING
}

