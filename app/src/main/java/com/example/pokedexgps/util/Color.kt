package com.example.pokedexgps.util

import androidx.compose.ui.graphics.Color

fun getColorForProgress(progress: Int): Color {
    return when {
        progress <= 30 -> Color(0xFFF44336) // Rojo para valores bajos
        progress <= 70 -> Color(0xFFFFC107) // Amarillo para valores medios
        else -> Color(0xFF4CAF50) // Verde para valores altos
    }
}

fun getColorForPokemonType(typeName: String?): Color {
    return when (typeName?.lowercase()) {
        "fire" -> Color(0xFFFFA726)
        "water" -> Color(0xFF42A5F5)
        "grass" -> Color(0xFF66BB6A)
        "electric" -> Color(0xFFFFEB3B)
        "bug" -> Color(0xFF8BC34A)
        "rock" -> Color(0xFF795548)
        "ground" -> Color(0xFFD4A373)
        "psychic" -> Color(0xFFAB47BC)
        "fairy" -> Color(0xFFF8BBD0)
        "fighting" -> Color(0xFFE57373)
        "poison" -> Color(0xFF9C27B0)
        "ghost" -> Color(0xFF5C6BC0)
        "dark" -> Color(0xFF424242)
        "dragon" -> Color(0xFF1976D2)
        "steel" -> Color(0xFFB0BEC5)
        "ice" -> Color(0xFF80DEEA)
        "normal" -> Color(0xFFA8A77A)
        else -> Color.White
    }
}