package de.thws.bachelorthesiskupper.visitorpattern.visitor

import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.*

interface Visitor {
    fun enterRoot(node: RootNode)
    fun exitRoot(node: RootNode)
    fun enterNode(node: Node)
    fun exitNode(node: Node)
}