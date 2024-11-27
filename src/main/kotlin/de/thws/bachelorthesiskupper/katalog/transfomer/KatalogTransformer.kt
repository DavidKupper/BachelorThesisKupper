package de.thws.bachelorthesiskupper.katalog.transfomer

interface KatalogTransformer {
    fun transform(sourceRoot: SourceRoot): TargetRoot
}

class KatalogTransformer1 : KatalogTransformer {
    override fun transform(sourceRoot: SourceRoot): TargetRoot {
        return TargetRoot(sourceRoot.a1)
    }
}

class KatalogTransformer31 : KatalogTransformer {
    override fun transform(sourceRoot: SourceRoot): TargetRoot {
        val a1 = sourceRoot.children.joinToString { it.a1 }
        return TargetRoot(a1);
    }
}

class KatalogTransformer32 : KatalogTransformer {
    override fun transform(sourceRoot: SourceRoot): TargetRoot {
        val children = sourceRoot.a1.asSequence().map { TargetChild(it.toString()) }.toList()
        return TargetRoot(sourceRoot.a1, children);
    }
}

class KatalogTransformer33A : KatalogTransformer {
    override fun transform(sourceRoot: SourceRoot): TargetRoot {
        val children = sourceRoot.children.map { it.toTargetChild() }
        return TargetRoot(sourceRoot.a1, children);
    }
}

class KatalogTransformer33B : KatalogTransformer {
    override fun transform(sourceRoot: SourceRoot): TargetRoot {
        val children1 = sourceRoot.children.map { it.toTargetChild() }
        val children2 = sourceRoot.a1.asSequence().map { TargetChild(it.toString()) }.toList()
        return TargetRoot(sourceRoot.a1, children1 + children2);
    }
}

class KatalogTransformer33C : KatalogTransformer {
    override fun transform(sourceRoot: SourceRoot): TargetRoot {
        val children = sourceRoot.children.filter { it.a1.length > 3 }.map { it.toTargetChild() }
        return TargetRoot(sourceRoot.a1, children);
    }
}

fun SourceChild.toTargetChild(): TargetChild {
    return TargetChild(this.a1)
}

