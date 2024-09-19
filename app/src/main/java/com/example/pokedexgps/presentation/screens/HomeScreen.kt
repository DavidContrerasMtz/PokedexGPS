package com.example.pokedexgps.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdsClick
import androidx.compose.material.icons.rounded.GpsFixed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokedexgps.R
import com.example.pokedexgps.presentation.viewmodels.GPSViewModel
import com.example.pokedexgps.presentation.uievents.PokemonUiEvent
import com.example.pokedexgps.presentation.viewmodels.PokemonViewModel
import com.example.pokedexgps.util.Screen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (navController: NavHostController) {

    val pokemonViewModel = viewModel<PokemonViewModel>()
    val pokemonState = pokemonViewModel.pokemonState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                onEvent = pokemonViewModel::onEvent
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (pokemonState.isCurrentGPSScreen)
                            stringResource(R.string.gps)
                        else
                            stringResource(R.string.obtain),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.GPS.route
            ) {
                composable(Screen.GPS.route){
                    GPSPokemonScreen(
                        navController = navController,
                        viewModel = GPSViewModel()
                    )
                }
                composable(Screen.Obtain.route){
                    ObtainPokemonScreen(
                        navController = navController
                    )
                }
            }
        }
    }

}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    onEvent: (PokemonUiEvent) -> Unit
) {
    val items = listOf(
        BottomItem(
            title = stringResource(R.string.gps),
            icon = Icons.Rounded.GpsFixed
        ),
        BottomItem(
            title = stringResource(R.string.obtain),
            icon = Icons.Rounded.AdsClick
        ),
    )
    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        if (currentRoute != Screen.getRouteByIndex(index)) {
                            selected.intValue = index
                            onEvent(PokemonUiEvent.Navigate)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.getRouteByIndex(index))
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)