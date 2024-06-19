package de.thws.bachelorthesiskupper.visitorpattern.visitor

import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.DataType
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.source.Entity
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Column
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.ColumnDataType

fun DataType.toTarget(): ColumnDataType {
    return when (this) {
        DataType.INTEGER -> ColumnDataType.INTEGER
        DataType.DECIMAL -> ColumnDataType.DECIMAL
        DataType.STRING -> ColumnDataType.VARCHAR
        DataType.BOOLEAN -> ColumnDataType.BIT
    }
}

fun Entity.toColumns(): List<Column> {
    return this.attributes.map { Column(it.name, false, null, it.datatype.toTarget()) }
}

fun String.entityNameToFkColumn(): Column {
    return Column(this.lowercase() + "Id", false, this, ColumnDataType.INTEGER)
}