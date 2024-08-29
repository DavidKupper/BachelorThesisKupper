package de.thws.bachelorthesiskupper.casestudy.visitor


class M3Sql(
    var name: String,
    val users: MutableList<M3User> = mutableListOf(),
    val tables: MutableList<M3Table> = mutableListOf(),
)

class M3User(
    var name: String,
    var password: String,
    var role: String
)

class M3Table(
    var name: String,
    val columns: MutableList<M3Column> = mutableListOf(),
    val constraints: MutableList<M3Constraint> = mutableListOf(),
)

class M3Column(
    var name: String,
    var type: M3ColumnType
)

enum class M3ColumnType {
    INT,
    DECIMAL,
    VARCHAR,
    BIT,
}

class M3Constraint(
    var target: String,
    var type: M3ConstraintType,
    var suffix: String? = null
)

enum class M3ConstraintType {
    NOT_NULL, PRIMARY_KEY, FOREIGN_KEY_REFERENCES
}
