package com.example.pokedexgps.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pokedexgps.presentation.states.PokemonState
import com.example.pokedexgps.presentation.uievents.PokemonUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class PokemonViewModel : ViewModel() {

    private var _pokemonState = MutableStateFlow(PokemonState())
    val pokemonState = _pokemonState.asStateFlow()

    fun onEvent(event: PokemonUiEvent){
        when(event) {
            PokemonUiEvent.Navigate->{
                _pokemonState.update {
                    it.copy(
                        isCurrentGPSScreen = !pokemonState.value.isCurrentGPSScreen
                    )
                }
            }
        }
    }
}