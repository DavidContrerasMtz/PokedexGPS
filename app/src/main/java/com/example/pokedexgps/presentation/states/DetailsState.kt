package com.example.pokedexgps.presentation.states

import com.example.pokedexgps.domain.model.Pokemon

data class DetailsState (
    val isLoading: Boolean = false,
    val pokemon : Pokemon? = null
)