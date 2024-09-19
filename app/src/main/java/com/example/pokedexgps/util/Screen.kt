package com.example.pokedexgps.util

sealed class Screen(val route: String) {
    object Home : Screen("main")
    object Details : Screen("details")
    object GPS: Screen("gps")
    object Obtain: Screen("obtain")

    companion object {
        fun getRouteByIndex(index: Int): String {
            return when (index) {
                0 -> GPS.route
                1 -> Obtain.route
                else -> GPS.route
            }
        }
    }
}
