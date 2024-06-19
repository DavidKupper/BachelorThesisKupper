package de.thws.bachelorthesiskupper.visitorpattern.metamodel.target

data class Database(
    val name: String,
    val tables: Set<Table>
)

data class Table(
    val name: String,
    val columns: List<Column>
)

data class Column(
    val name: String,
    val isPk: Boolean,
    val referencesTable: String?,
    val datatype: ColumnDataType,
)

enum class ColumnDataType {
    INTEGER,
    DECIMAL,
    VARCHAR,
    BIT,
}