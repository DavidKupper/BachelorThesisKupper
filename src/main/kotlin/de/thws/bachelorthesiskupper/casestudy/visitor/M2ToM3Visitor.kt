package de.thws.bachelorthesiskupper.casestudy.visitor

interface M2Visitable {
    fun accept(visitor: M2Visitor)
}

interface M2Visitor {
    fun visit(db: M2Db)
    fun visit(rootUser: M2RootUser)
    fun visit(table: M2Table)
    fun visit(column: M2Column)
}

class M2ToM3Visitor : M2Visitor {
    lateinit var sql: M3Sql
    lateinit var currentTable: M3Table
    
    override fun visit(db: M2Db) {
        sql = M3Sql(db.name)
    }

    override fun visit(rootUser: M2RootUser) {
        sql.users.add(M3User(rootUser.name, rootUser.password, role = "admin"))
    }

    override fun visit(table: M2Table) {
        currentTable = M3Table(table.name)
        sql.tables.add(currentTable)
    }

    override fun visit(column: M2Column) {
        currentTable.columns.add(M3Column(column.name + if (column.isFk) "Id" else "", column.type.toM3ColumnType()))
        if (column.isPk) {
            currentTable.constraints.add(M3Constraint(column.name, M3ConstraintType.PRIMARY_KEY))
        }
        if (column.notNull) {
            currentTable.constraints.add(M3Constraint(column.name, M3ConstraintType.NOT_NULL))
        }
        if (column.isFk) {
            currentTable.constraints.add(M3Constraint(column.name, M3ConstraintType.FOREIGN_KEY_REFERENCES, suffix = "${column.name}(Id)"))
        }
    }
}

private fun M2ColumnType.toM3ColumnType(): M3ColumnType {
    return when (this) {
        M2ColumnType.INT -> M3ColumnType.INT
        M2ColumnType.DECIMAL -> M3ColumnType.DECIMAL
        M2ColumnType.BOOL -> M3ColumnType.BIT
        M2ColumnType.TEXT -> M3ColumnType.VARCHAR
    }
}