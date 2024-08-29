package de.thws.bachelorthesiskupper.casestudy.transfomer

class M2Db(
    val name: String,
    val rootUsers: List<M2RootUser>,
    val tables: List<M2Table>,
    val coreTableCount: Int,
)

class M2RootUser(
    val name: String,
    val password: String
)

class M2Table(
    val name: String,
    val columns: List<M2Column>,
)

class M2Column(
    val name: String,
    val type: M2ColumnType,
    val notNull: Boolean = false,
    val isPk: Boolean = false,
    val isFk: Boolean = false,
)

enum class M2ColumnType {
    INT,
    DECIMAL,
    BOOL,
    TEXT,
}

