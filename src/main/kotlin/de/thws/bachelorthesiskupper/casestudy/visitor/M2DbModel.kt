package de.thws.bachelorthesiskupper.casestudy.visitor

class M2Db(
    var name: String,
    val rootUsers: MutableList<M2RootUser> = mutableListOf(),
    val tables: MutableList<M2Table> = mutableListOf(),
    var coreTableCount: Int,
) : M2Visitable {
    override fun accept(visitor: M2Visitor) {
        visitor.visit(this)
        rootUsers.forEach { it.accept(visitor) }
        tables.forEach { it.accept(visitor) }
    }
}

class M2RootUser(
    var name: String,
    var password: String
) : M2Visitable {
    override fun accept(visitor: M2Visitor) {
        visitor.visit(this)
    }
}

class M2Table(
    var name: String,
    val columns: MutableList<M2Column>,
) : M2Visitable {
    override fun accept(visitor: M2Visitor) {
        visitor.visit(this)
        columns.forEach { it.accept(visitor) }
    }
}

class M2Column(
    var name: String,
    var type: M2ColumnType,
    var notNull: Boolean = false,
    var isPk: Boolean = false,
    var isFk: Boolean = false,
) : M2Visitable {
    override fun accept(visitor: M2Visitor) {
        visitor.visit(this)
    }
}

enum class M2ColumnType {
    INT,
    DECIMAL,
    BOOL,
    TEXT,
}

