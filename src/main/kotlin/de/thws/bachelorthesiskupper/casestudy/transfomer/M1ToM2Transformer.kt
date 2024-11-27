package de.thws.bachelorthesiskupper.casestudy.transfomer

class M1ToM2Transformer : Transformer<M1Rest, M2Db> {
    override fun transform(source: M1Rest): M2Db {
        val rootUsers = rootUsers(source.adminUsers)
        val tables = source.resources.map { it.toM2Table() } + createM2JunctionTables(source.resources)
        return M2Db(source.name, rootUsers, tables, source.resources.size)
    }
}

private fun rootUsers(adminUsers: String) = adminUsers.split(",").map { M2RootUser(it, "root") }

private fun M1Resource.toM2Table(): M2Table {
    val idColumn = M2Column(name = "Id", M2ColumnType.INT, notNull = true, isPk = true)
    val attributeColumns = attributes.map { it.toM2Column() }
    val fkColumns = references
        .filter { it.cardinality == Cardinality.MANY_TO_ONE || it.cardinality == Cardinality.ONE_TO_ONE }
        .map { it.toM2FkColumn() }
    return M2Table(name, listOf(idColumn) + fkColumns + attributeColumns)
}

private fun createM2JunctionTables(resources: List<M1Resource>): List<M2Table> {
    val junctionTableNames = resources.flatMap { resource ->
        resource.references
            .filter { it.cardinality == Cardinality.MANY_TO_MANY }
            .map { ref ->
                if (resource.name < ref.referencedResource)
                    resource.name to ref.referencedResource
                else ref.referencedResource to resource.name
            }
    }
    return junctionTableNames
        .distinct()
        .map { createM2JunctionTable(it.first, it.second) }
}

private fun createM2JunctionTable(name1: String, name2: String) = M2Table(
    name1 + name2, listOf(
        M2Column(name1, M2ColumnType.INT, notNull = true, isFk = true),
        M2Column(name2, M2ColumnType.INT, notNull = true, isFk = true)
    )
)

private fun M1Attribute.toM2Column() = M2Column(name, attributeType.toM2ColumnType(), !nullable)

private fun M1Reference.toM2FkColumn() =
    M2Column(referencedResource, M2ColumnType.INT, notNull = true, isPk = false, isFk = true)

private fun M1AttributeType.toM2ColumnType(): M2ColumnType {
    return when (this) {
        M1AttributeType.INTEGER -> M2ColumnType.INT
        M1AttributeType.DECIMAL -> M2ColumnType.DECIMAL
        M1AttributeType.BOOLEAN -> M2ColumnType.BOOL
        M1AttributeType.STRING -> M2ColumnType.TEXT
    }
}
