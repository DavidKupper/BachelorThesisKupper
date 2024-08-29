package de.thws.bachelorthesiskupper.casestudy.transfomer

interface Transformer<S, T> {
    fun transform(source: S): T
}