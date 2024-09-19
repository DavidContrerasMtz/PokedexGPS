package com.example.pokedexgps.data.remote.response

data class PokemonDto(
    val name: String?,
    val sprites: SpritesDto?,
    val types: List<TypeEntryDto>?,
    val stats: List<StatEntryDto>?,
    val weight: Int?,
    val height: Int?
)

data class SpritesDto(
    val frontDefault: String?
)

data class TypeEntryDto(
    val type: TypeDto?
)

data class TypeDto(
    val name: String?
)

data class StatEntryDto(
    val base_stat: Int?,
    val stat: StatDto?
)

data class StatDto(
    val name: String?
)