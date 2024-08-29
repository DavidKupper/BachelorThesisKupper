package de.thws.bachelorthesiskupper.casestudy.transfomer


class M3Sql(
    val name: String,
    val users: List<M3User>,
    val tables: List<M3Table>,
)

class M3User(
    val name: String,
    val password: String,
    val role: String
)

class M3Table(
    val name: String,
    val columns: List<M3Column>,
    val constraints: List<M3Constraint>,
)

class M3Column(
    val name: String,
    val type: M3ColumnType
)

enum class M3ColumnType {
    INT,
    DECIMAL,
    VARCHAR,
    BIT,
}

class M3Constraint(
    val target: String,
    val type: M3ConstraintType,
    val suffix: String? = null
)

enum class M3ConstraintType {
    NOT_NULL, PRIMARY_KEY, FOREIGN_KEY_REFERENCES
}
