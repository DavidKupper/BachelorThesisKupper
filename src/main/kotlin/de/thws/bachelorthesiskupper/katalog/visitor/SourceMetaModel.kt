package de.thws.bachelorthesiskupper.katalog.visitor

class SourceRoot(
    var a1: String,
    val children: MutableList<SourceChild> = mutableListOf()
) : Visitable {

    override fun accept(visitor: KatalogVisitor) {
        visitor.visit(this)
        children.forEach { it.accept(visitor) }
    }

}

class SourceChild(
    var a1: String,
) : Visitable {

    override fun accept(visitor: KatalogVisitor) {
        visitor.visit(this)
    }
}