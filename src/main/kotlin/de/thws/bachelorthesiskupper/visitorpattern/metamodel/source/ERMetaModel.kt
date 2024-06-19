package de.thws.bachelorthesiskupper.visitorpattern.metamodel.source

import de.thws.bachelorthesiskupper.visitorpattern.visitor.Visitor
import de.thws.bachelorthesiskupper.visitorpattern.visitor.VisitorConsumer

class RootNode(
    entity: Entity,
    children: Set<Node>
) : AbstractNode(entity, children) {
    constructor(name: String, attributes: List<EntityAttribute>, children: Set<Node>) : this(Entity(name, attributes), children)

    override fun accept(v: Visitor) {
        v.enterRoot(this)
        children.forEach{child -> child.accept(v)}
        v.exitRoot(this)
    }
}

class Node(
    val relationToParent: Relation,
    entity: Entity,
    children: Set<Node>,
) : AbstractNode(entity, children) {
    constructor(relationToParent: Relation, name: String, attributes: List<EntityAttribute>)
            : this(relationToParent, Entity(name, attributes), emptySet())
    constructor(relationToParent: Relation, name: String, attributes: List<EntityAttribute>, children: Set<Node>)
            : this(relationToParent, Entity(name, attributes), children)

    override fun accept(v: Visitor) {
        v.enterNode(this)
        children.forEach{child -> child.accept(v)}
        v.exitNode(this)
    }

}

abstract class AbstractNode(
    val entity: Entity,
    val children: Set<Node>
): VisitorConsumer
