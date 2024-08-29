package de.thws.bachelorthesiskupper.casestudy.visitor

class M1Rest(
    var name: String,
    var adminUsers: String, // comma separated MutableList of users
    val resources: MutableList<M1Resource> = mutableListOf(),
) : M1Visitable {
    override fun accept(visitor: M1Visitor) {
        visitor.visit(this)
        resources.forEach { it.accept(visitor) }
    }
}

class M1Resource(
    var name: String,
    val attributes: MutableList<M1Attribute>,
    val references: MutableList<M1Reference>
) : M1Visitable {
    override fun accept(visitor: M1Visitor) {
        visitor.visit(this)
        attributes.forEach { it.accept(visitor) }
        references.forEach { it.accept(visitor) }
    }
}

class M1Attribute(
    var name: String,
    var attributeType: M1AttributeType,
    var nullable: Boolean,
) : M1Visitable {
    override fun accept(visitor: M1Visitor) {
        visitor.visit(this)
    }
}

class M1Reference(
    var referencedResource: String,
    var cardinality: Cardinality,
) : M1Visitable {
    override fun accept(visitor: M1Visitor) {
        visitor.visit(this)
    }
}

enum class Cardinality {
    ONE_TO_ONE,
    ONE_TO_MANY,
    MANY_TO_ONE,
    MANY_TO_MANY
}

enum class M1AttributeType {
    INTEGER,
    DECIMAL,
    BOOLEAN,
    STRING,
}