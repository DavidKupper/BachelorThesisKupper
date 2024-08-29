package de.thws.bachelorthesiskupper.casestudy.transfomer

class M2ToM3Transformer : Transformer<M2Db, M3Sql> {
    override fun transform(source: M2Db): M3Sql {
        return M3Sql(source.name, source.rootUsers.map { it.toM3Users() }, source.tables.map { it.toM3Tables() })
    }
}

private fun M2RootUser.toM3Users()= M3User(name, password, role = "admin")

private fun M2Table.toM3Tables() = M3Table(name, columns.map { it.toM3Columns() }, createM3Constraints(columns))

private fun M2Column.toM3Columns() = M3Column(name + if (isFk) "Id" else "", type.toM3ColumnType())

private fun createM3Constraints(columns: List<M2Column>): List<M3Constraint> {
    val pkConstraints = columns.filter { it.isPk }.map { M3Constraint(it.name, M3ConstraintType.PRIMARY_KEY) }
    val notNullConstraints = columns.filter { it.notNull }.map { M3Constraint(it.name, M3ConstraintType.NOT_NULL) }
    val fkConstraints = columns.filter { it.isFk }.map { M3Constraint(it.name + "Id", M3ConstraintType.FOREIGN_KEY_REFERENCES, suffix = "${it.name}(Id)") }
    return pkConstraints + notNullConstraints + fkConstraints
}

private fun M2ColumnType.toM3ColumnType(): M3ColumnType {
    return when (this) {
        M2ColumnType.INT -> M3ColumnType.INT
        M2ColumnType.DECIMAL -> M3ColumnType.DECIMAL
        M2ColumnType.BOOL -> M3ColumnType.BIT
        M2ColumnType.TEXT -> M3ColumnType.VARCHAR
    }
}