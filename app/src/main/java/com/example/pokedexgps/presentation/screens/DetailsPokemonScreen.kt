package com.example.pokedexgps.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.pokedexgps.domain.model.Pokemon
import com.example.pokedexgps.presentation.viewmodels.DetailsViewModel
import com.example.pokedexgps.util.getColorForPokemonType
import com.example.pokedexgps.util.getColorForProgress
import com.example.pokedexgps.util.statAbbreviations
import java.util.Locale

@Composable
fun DetailsPokemonScreen(
    navController: NavHostController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val detailsState by viewModel.detailsState.collectAsState()
    val pokemonId = navController.currentBackStackEntry?.arguments?.getInt("pokemonId")

    val pokemon = detailsState.pokemon
    val backgroundColor =
        pokemon?.let { getColorForPokemonType(it.types.firstOrNull()?.type?.name) } ?: Color.White

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Box {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 40.dp, start = 16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            when {
                detailsState.isLoading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                detailsState.pokemon != null -> {
                    PokemonDetailsContent(
                        pokemon = detailsState.pokemon!!,
                        pokemonId,
                        backgroundColor
                    )
                }

                else -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "No se encontró el Pokémon")
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonDetailsContent(pokemon: Pokemon, pokemonId: Int?, backgroundColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(data = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonId}.png"),
            contentDescription = "Imagen de ${pokemon.name}",
            modifier = Modifier
                .size(250.dp)
                .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                .offset(y = 48.dp)
                .zIndex(5f),
            contentScale = ContentScale.Fit
        )

        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "Types: ${pokemon.types.joinToString { it.type.name.capitalize() }}",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "Weight: ${pokemon.weight}kg   Height: ${pokemon.height}cm",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Stats",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                pokemon.stats.forEach { stat ->
                    val progress = stat.base_stat / 100f

                    val color = getColorForProgress(stat.base_stat)

                    val statName =
                        statAbbreviations[stat.stat.name] ?: stat.stat.name.uppercase(Locale.ROOT)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {

                        Text(
                            text = "${statName}:",
                            fontSize = 18.sp,
                            color = backgroundColor,
                            fontWeight = FontWeight(700),
                            modifier = Modifier
                                .weight(.5f)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "${stat.base_stat}",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .weight(.5f)
                                .padding(end = 8.dp)
                        )
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .weight(2f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = color,
                            trackColor = Color.LightGray
                        )
                    }

                }
            }
        }
    }
}