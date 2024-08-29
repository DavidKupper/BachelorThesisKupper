package de.thws.bachelorthesiskupper.casestudy.visitor

interface M1Visitable {
    fun accept(visitor: M1Visitor)
}

interface M1Visitor {
    fun visit(rest: M1Rest)
    fun visit(resource: M1Resource)
    fun visit(attribute: M1Attribute)
    fun visit(reference: M1Reference)
}

class M1ToM2Visitor : M1Visitor {
    lateinit var db: M2Db
    lateinit var currentTable: M2Table

    override fun visit(rest: M1Rest) {
        val rootUsers = m2RootUsers(rest.adminUsers)
        db = M2Db(rest.name, rootUsers.toMutableList(), coreTableCount = 0)
    }

    override fun visit(resource: M1Resource) {
        currentTable = resource.toM2Table()
        db.tables.add(currentTable)
        db.coreTableCount++
    }

    override fun visit(attribute: M1Attribute) {
        currentTable.columns.add(attribute.toM2Column())
    }

    override fun visit(reference: M1Reference) {
        if (reference.cardinality == Cardinality.MANY_TO_ONE || reference.cardinality == Cardinality.ONE_TO_ONE) {
            currentTable.columns.add(reference.toM2FkColumn())
        } else if (reference.cardinality == Cardinality.MANY_TO_MANY) {
            val namePair =
                if (currentTable.name < reference.referencedResource) currentTable.name to reference.referencedResource
                else reference.referencedResource to currentTable.name
            val junctionTableName = namePair.first + namePair.second
            if (db.tables.none { it.name == junctionTableName }) {
                db.tables.add(namePair.toJunctionTable())
            }
        }
    }

}

private fun m2RootUsers(adminUsers: String): List<M2RootUser> {
    return adminUsers.split(",").map { M2RootUser(it, "root") }
}

private fun M1Resource.toM2Table(): M2Table {
    val idColumn = M2Column(name = "Id", M2ColumnType.INT, notNull = true, isPk = true)
    return M2Table(name, columns = mutableListOf(idColumn))
}

private fun Pair<String, String>.toJunctionTable(): M2Table {
    return M2Table(
        first + second,
        mutableListOf(
            M2Column(
                first,
                M2ColumnType.INT,
                notNull = true,
                isFk = true
            ),
            M2Column(
                second,
                M2ColumnType.INT,
                notNull = true,
                isFk = true
            )
        )
    )
}

private fun M1Attribute.toM2Column(): M2Column {
    return M2Column(name, attributeType.toM2ColumnType(), !nullable)
}

private fun M1Reference.toM2FkColumn(): M2Column {
    return M2Column(
        referencedResource,
        M2ColumnType.INT,
        notNull = true,
        isPk = false,
        isFk = true
    )
}

private fun M1AttributeType.toM2ColumnType(): M2ColumnType {
    return when (this) {
        M1AttributeType.INTEGER -> M2ColumnType.INT
        M1AttributeType.DECIMAL -> M2ColumnType.DECIMAL
        M1AttributeType.BOOLEAN -> M2ColumnType.BOOL
        M1AttributeType.STRING -> M2ColumnType.TEXT
    }
}