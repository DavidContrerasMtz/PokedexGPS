package com.example.pokedexgps.data.remote

import com.example.pokedexgps.data.remote.response.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonById(
        @Path("pokemonId") pokemonId: Int
    ) : PokemonDto

    companion object{
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

}