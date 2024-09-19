package com.example.pokedexgps.domain.repository

import com.example.pokedexgps.domain.model.Pokemon
import com.example.pokedexgps.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonById(id: Int): Flow<Resource<Pokemon>>
}