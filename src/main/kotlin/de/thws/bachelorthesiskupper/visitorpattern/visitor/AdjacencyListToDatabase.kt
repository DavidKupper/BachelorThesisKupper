package de.thws.bachelorthesiskupper.visitorpattern.visitor

import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.Relation
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Column
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.ColumnDataType
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Database
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Table

fun AdjacencyList.toDatabase(): Database {
    val tables = nodesToEdges.entries.map { nodeToEdges ->
        Table(
            nodeToEdges.first.entity.name,
            (listOf(Column("id", true, null, ColumnDataType.INTEGER))
                    + nodeToEdges.first.entity.toColumns()
                    + nodeToEdges.second.filter { it.second == Relation.MANY_TO_ONE }
                .map { it.first.entity.name.entityNameToFkColumn() })
        )
    }.toSet()
    val junctionTables = edges
        .filter { it.relation == Relation.MANY_TO_MANY }
        .map {
            Table(
                it.node1.entity.name + it.node2.entity.name + "Table",
                listOf(it.node1.entity.name.entityNameToFkColumn(), it.node2.entity.name.entityNameToFkColumn())
            )
        }.toSet()
    return Database(
        name = rootNode.entity.name + "Database",
        tables = tables + junctionTables
    )

}