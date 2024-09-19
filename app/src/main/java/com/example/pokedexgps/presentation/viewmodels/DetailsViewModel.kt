package com.example.pokedexgps.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexgps.domain.repository.PokemonRepository
import com.example.pokedexgps.presentation.states.DetailsState
import com.example.pokedexgps.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val pokemonId = savedStateHandle.get<Int>("pokemonId")

    private var _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    init {
        getPokemonById(pokemonId ?: 0)
    }

    private fun getPokemonById(pokemonId: Int){
        viewModelScope.launch {
            _detailsState.update {
                it.copy(isLoading = true)
            }

            pokemonRepository.getPokemonById(pokemonId).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                       result.data.let { pokemon->
                           _detailsState.update {
                               it.copy(pokemon = pokemon)
                           }
                       }
                    }
                }
            }
        }
    }
}