package de.thws.bachelorthesiskupper.casestudy.transfomer

class M1Rest(
    val name: String,
    val adminUsers: String, // comma separated list of users
    val resources: List<M1Resource>,
)

class M1Resource(
    val name: String,
    val attributes: List<M1Attribute>,
    val references: List<M1Reference>
)

class M1Attribute(
    val name: String,
    val attributeType: M1AttributeType,
    val nullable: Boolean,
)

class M1Reference(
    val referencedResource: String,
    val cardinality: Cardinality,
)

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