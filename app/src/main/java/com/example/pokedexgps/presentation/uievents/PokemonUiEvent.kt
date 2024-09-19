package com.example.pokedexgps.presentation.uievents

sealed interface PokemonUiEvent {
    data object Navigate: PokemonUiEvent
}