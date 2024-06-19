package de.thws.bachelorthesiskupper.visitorpattern.m2t

import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Column
import de.thws.bachelorthesiskupper.visitorpattern.metamodel.target.Database

fun Database.toSqlCreateStatements(): String {
    return tables.joinToString(";\n") { "CREATE TABLE ${it.name}(\n${it.columns.mapToSql().joinToString(",\n")})" }
}

private fun List<Column>.mapToSql(): List<String> {
    return this.map { "${it.name} ${it.datatype.name.lowercase()} ${if (it.isPk) "PK" else if (it.referencesTable != null) "REFERENCES (${it.referencesTable})" else ""}" }
}