package com.example.pokedexgps.data.repository

import com.example.pokedexgps.data.mappers.toPokemon
import com.example.pokedexgps.data.remote.PokemonApi
import com.example.pokedexgps.domain.model.Pokemon
import com.example.pokedexgps.domain.repository.PokemonRepository
import com.example.pokedexgps.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi
) : PokemonRepository {
    override suspend fun getPokemonById(id: Int): Flow<Resource<Pokemon>> {
        return flow {
            emit(Resource.Loading(true))

            val pokemon = try {
                pokemonApi.getPokemonById(id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading pokemon"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading pokemon"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading pokemon"))
                return@flow
            }

            emit(Resource.Success(pokemon.toPokemon()))
            emit(Resource.Loading(false))
        }
    }
}