package com.example.pokedexgps.data.mappers

import com.example.pokedexgps.data.remote.response.PokemonDto
import com.example.pokedexgps.data.remote.response.SpritesDto
import com.example.pokedexgps.data.remote.response.StatDto
import com.example.pokedexgps.data.remote.response.StatEntryDto
import com.example.pokedexgps.data.remote.response.TypeDto
import com.example.pokedexgps.data.remote.response.TypeEntryDto
import com.example.pokedexgps.domain.model.Pokemon
import com.example.pokedexgps.domain.model.Sprites
import com.example.pokedexgps.domain.model.Stat
import com.example.pokedexgps.domain.model.StatEntry
import com.example.pokedexgps.domain.model.Type
import com.example.pokedexgps.domain.model.TypeEntry

fun PokemonDto.toPokemon() : Pokemon {
    return Pokemon(
        name = name ?: "",
        sprites = sprites?.toSprites() ?: Sprites(""),
        types = types?.map { it.toTypeEntry() } ?: emptyList(),
        stats = stats?.map { it.toStatEntry() } ?: emptyList(),
        weight = weight ?: 0,
        height = height ?: 0,
    )
}

fun SpritesDto.toSprites(): Sprites {
    return Sprites(frontDefault = frontDefault ?: "")
}

fun TypeEntryDto.toTypeEntry(): TypeEntry {
    return TypeEntry(type = type!!.toType())
}

fun TypeDto.toType(): Type {
    return Type(name = name ?: "")
}

fun StatEntryDto.toStatEntry(): StatEntry {
    return StatEntry(
        base_stat = base_stat ?: 0,
        stat = stat!!.toStat()
    )
}

fun StatDto.toStat(): Stat {
    return Stat(name = name ?: "")
}