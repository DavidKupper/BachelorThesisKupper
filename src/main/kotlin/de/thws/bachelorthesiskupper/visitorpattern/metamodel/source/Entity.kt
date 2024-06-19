package de.thws.bachelorthesiskupper.visitorpattern.metamodel.source

data class Entity(
    val name: String,
    val attributes: List<EntityAttribute>,
)

enum class Relation {
    ONE_TO_ONE,
    MANY_TO_ONE,
    ONE_TO_MANY,
    MANY_TO_MANY,
}

data class EntityAttribute(
    val name: String,
    val datatype: DataType
)

enum class DataType {
    INTEGER,
    DECIMAL,
    STRING,
    BOOLEAN
}