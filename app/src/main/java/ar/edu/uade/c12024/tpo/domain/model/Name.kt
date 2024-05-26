package ar.edu.uade.c12024.tpo.domain.model

data class Name(
    val common: String,
    val official: String,
    val nativeName: Map<String, NativeName>
)