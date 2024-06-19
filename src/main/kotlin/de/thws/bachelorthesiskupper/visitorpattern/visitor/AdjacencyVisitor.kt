package de.thws.bachelorthesiskupper.visitorpattern.visitor

import de.thws.bachelorthesiskupper.util.MultiValueMap
import de.thws.bachelorthesiskupper.util.Stack
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.*

data class AdjacencyList(
    val rootNode: AbstractNode,
    val nodesToEdges: MultiValueMap<AbstractNode, Pair<AbstractNode, Relation>>,
    val edges: Set<Edge>,
)

class AdjacencyVisitor : Visitor {
    private var rootNode: AbstractNode? = null
    private val nodesToNeighbours = MultiValueMap<AbstractNode, Pair<AbstractNode, Relation>>()
    private val edges = mutableSetOf<Edge>()
    private val parentStack = Stack<AbstractNode>()

    override fun enterRoot(node: RootNode) {
        rootNode = node
        addNodeAndEdgesToChildren(node)
        parentStack.push(node)
    }

    override fun exitRoot(node: RootNode) {

    }

    override fun enterNode(node: Node) {
        addNodeAndEdgesToChildren(node)
        nodesToNeighbours.put(node, parentStack.peek()!! to node.relationToParent.inverse())
        parentStack.push(node)
    }

    override fun exitNode(node: Node) {
        parentStack.pop()
    }

    fun constructAdjacencyList(): AdjacencyList {
        return AdjacencyList(rootNode!!, nodesToNeighbours, edges.toSet())
    }

    private fun addNodeAndEdgesToChildren(node: AbstractNode) {
        nodesToNeighbours.putAll(node, node.children.map { it to it.relationToParent })
        edges.addAll(node.children.map { Edge(it.relationToParent, node, it) })
    }
}

private fun Relation.inverse(): Relation {
    return when (this) {
        Relation.ONE_TO_MANY -> Relation.MANY_TO_ONE
        Relation.MANY_TO_ONE -> Relation.ONE_TO_MANY
        else -> this
    }
}

data class Edge(
    val relation: Relation,
    val node1: AbstractNode,
    val node2: AbstractNode
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Edge) {
            return false
        }
        return this.relation == other.relation &&
                    ((this.node1 === other.node1 && this.node2 === other.node2)
                            || this.node2 === other.node1 && this.node1 === other.node2)
    }

    override fun hashCode(): Int {
        var result = relation.hashCode()
        result = 31 * result + node1.hashCode() + node2.hashCode()
        return result
    }
}