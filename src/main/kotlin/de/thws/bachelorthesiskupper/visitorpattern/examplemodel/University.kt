package de.thws.bachelorthesiskupper.visitorpattern.examplemodel

import de.thws.bachelorthesiskupper.visitorpattern.m2t.toSqlCreateStatements
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.DataType.*
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.Node
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.EntityAttribute
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.Relation.*
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.RootNode
import de.thws.bachelorthesiskupper.visitorpattern.visitor.AdjacencyVisitor
import de.thws.bachelorthesiskupper.visitorpattern.visitor.ErToDbVisitor
import de.thws.bachelorthesiskupper.visitorpattern.visitor.toDatabase

fun main() {
    val treeRoot = RootNode(
        name = "University",
        attributes = listOf(EntityAttribute("name", STRING)),
        children = setOf(
            Node(
                relationToParent = ONE_TO_MANY,
                name = "Faculty",
                attributes = listOf(EntityAttribute("name", STRING)),
                children = setOf(
                    Node(
                        relationToParent = MANY_TO_ONE,
                        name = "Establishment",
                        attributes = listOf(EntityAttribute("location", STRING)),
                    ),
                    Node(
                        relationToParent = ONE_TO_MANY,
                        name = "Department",
                        attributes = listOf(EntityAttribute("name", STRING)),
                        children = setOf(
                            Node(
                                relationToParent = MANY_TO_MANY,
                                name = "Course",
                                attributes = listOf(EntityAttribute("name", STRING)),
                            ),
                            Node(
                                relationToParent = ONE_TO_MANY,
                                name = "Student",
                                attributes = listOf()
                            ),
                            Node(
                                relationToParent = MANY_TO_MANY,
                                name = "Lecturer",
                                attributes = listOf()
                            )
                        )
                    )
                )
            )
        )
    )

    val visitor = ErToDbVisitor()
    treeRoot.accept(visitor)
    val database = visitor.constructDatabase()

    val adjacencyVisitor = AdjacencyVisitor()
    treeRoot.accept(adjacencyVisitor)
    val adjacencyList = adjacencyVisitor.constructAdjacencyList()

    println(database.toSqlCreateStatements())
    println()
    println(adjacencyList.toDatabase().toSqlCreateStatements())
}