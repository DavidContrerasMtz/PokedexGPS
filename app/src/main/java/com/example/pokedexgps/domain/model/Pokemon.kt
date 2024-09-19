package com.example.pokedexgps.domain.model

data class Pokemon(
    val name: String,
    val sprites: Sprites,
    val types: List<TypeEntry>,
    val stats: List<StatEntry>,
    val weight: Int,
    val height: Int
)

data class Sprites(
    val frontDefault: String
)

data class TypeEntry(
    val type: Type
)

data class Type(
    val name: String
)

data class StatEntry(
    val base_stat: Int,
    val stat: Stat
)

data class Stat(
    val name: String
)