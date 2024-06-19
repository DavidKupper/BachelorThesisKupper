package de.thws.bachelorthesiskupper.visitorpattern.visitor

interface VisitorConsumer {
    fun accept(v: Visitor)
}