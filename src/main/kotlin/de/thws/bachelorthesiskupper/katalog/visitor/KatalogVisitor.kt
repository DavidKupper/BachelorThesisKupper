package de.thws.bachelorthesiskupper.katalog.visitor

interface Visitable {
    fun accept(visitor: KatalogVisitor)
}

interface KatalogVisitor {
    fun visit(sourceRoot: SourceRoot)

    fun visit(sourceChild: SourceChild)
}

class KatalogVisitor1Root : KatalogVisitor {
    lateinit var targetRoot: TargetRoot

    override fun visit(sourceRoot: SourceRoot) {
        targetRoot = TargetRoot(sourceRoot.a1)
    }

    override fun visit(sourceChild: SourceChild) {
        // not used
    }
}

class KatalogVisitor31 : KatalogVisitor {
    lateinit var targetRoot: TargetRoot

    override fun visit(sourceRoot: SourceRoot) {
        targetRoot = TargetRoot("")
    }

    override fun visit(sourceChild: SourceChild) {
        targetRoot.a1 += sourceChild.a1
    }
}

class KatalogVisitor32 : KatalogVisitor {
    lateinit var targetRoot: TargetRoot

    override fun visit(sourceRoot: SourceRoot) {
        val children = sourceRoot.a1.asSequence().map { TargetChild(it.toString()) }.toList()
        targetRoot = TargetRoot(sourceRoot.a1, children.toMutableList())
    }

    override fun visit(sourceChild: SourceChild) {
        // not used
    }
}

class KatalogVisitor33a : KatalogVisitor {
    lateinit var targetRoot: TargetRoot

    override fun visit(sourceRoot: SourceRoot) {
        targetRoot = TargetRoot(sourceRoot.a1)
    }

    override fun visit(sourceChild: SourceChild) {
        targetRoot.children.add(TargetChild(sourceChild.a1))
    }
}

class KatalogVisitor33b : KatalogVisitor {
    lateinit var targetRoot: TargetRoot

    override fun visit(sourceRoot: SourceRoot) {
        val children = sourceRoot.a1.asSequence().map { TargetChild(it.toString()) }.toList()
        targetRoot = TargetRoot(sourceRoot.a1, children.toMutableList())
    }

    override fun visit(sourceChild: SourceChild) {
        targetRoot.children.add(TargetChild(sourceChild.a1))
    }
}

class KatalogVisitor33c : KatalogVisitor {
    lateinit var targetRoot: TargetRoot

    override fun visit(sourceRoot: SourceRoot) {
        targetRoot = TargetRoot(sourceRoot.a1)
    }

    override fun visit(sourceChild: SourceChild) {
        if (sourceChild.a1.length > 3) {
            targetRoot.children.add(TargetChild(sourceChild.a1))
        }
    }
}
