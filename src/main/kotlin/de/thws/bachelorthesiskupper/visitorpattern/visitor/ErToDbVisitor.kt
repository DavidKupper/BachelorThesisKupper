package de.thws.bachelorthesiskupper.visitorpattern.visitor

import de.thws.bachelorthesiskupper.util.MultiValueMap
import de.thws.bachelorthesiskupper.util.Stack
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.*
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Column
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.ColumnDataType
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Database
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Table

class ErToDbVisitor : Visitor {
    private var databaseName: String = ""
    private val nameToEntity = mutableMapOf<String, Entity>()
    private val junctionTableNames = mutableSetOf<String>()
    private val nameToReferencedEntityNames = MultiValueMap<String, String>()
    private val parentNameStack = Stack<String>()


    override fun enterRoot(node: RootNode) {
        val entity = node.entity
        databaseName = entity.name
        nameToEntity[entity.name] = entity
        parentNameStack.push(entity.name)
    }

    override fun exitRoot(node: RootNode) {

    }

    override fun enterNode(node: Node) {
        val entity = node.entity
        nameToEntity[entity.name] = entity
        val parentName = parentNameStack.peek()!!

        when (node.relationToParent) {
            Relation.ONE_TO_ONE -> {}
            Relation.MANY_TO_ONE -> nameToReferencedEntityNames.put(parentName, entity.name)
            Relation.ONE_TO_MANY -> nameToReferencedEntityNames.put(entity.name, parentName)
            Relation.MANY_TO_MANY -> {
                val junctionTableName = parentName + entity.name
                junctionTableNames.add(junctionTableName)
                nameToReferencedEntityNames.put(junctionTableName, entity.name)
                nameToReferencedEntityNames.put(junctionTableName, parentName)
            }
        }

        parentNameStack.push(entity.name)
    }

    override fun exitNode(node: Node) {
        parentNameStack.pop()
    }

    fun constructDatabase(): Database {
        val tables = nameToEntity.keys
            .map { name ->
                val fkColumns = nameToReferencedEntityNames[name].map { it.entityNameToFkColumn() }
                val columns = if (nameToEntity.keys.contains(name)) {
                    (listOf(Column("id", true, null, ColumnDataType.INTEGER))
                    + nameToEntity[name]!!.toColumns())
                }
                else
                    emptyList()
                Table(name = name + "Table", columns + fkColumns)
            }.toSet()
        val junctionTables = junctionTableNames.map { name ->
            val fkColumns = nameToReferencedEntityNames[name].map { it.entityNameToFkColumn() }.toList()
            Table(name = name + "Table", fkColumns)
        }.toSet()
        return Database(databaseName, tables + junctionTables)
    }
}

