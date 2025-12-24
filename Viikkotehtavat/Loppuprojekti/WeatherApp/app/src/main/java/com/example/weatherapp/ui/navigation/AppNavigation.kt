package com.example.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.ui.screens.LocationScreen
import com.example.weatherapp.ui.screens.WeatherScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "location"
    ) {
        composable("location") {
            LocationScreen(
                modifier = modifier,
                navController = navController
            )
        }

        composable("weather/{lat}/{lon}") { backStackEntry ->
            val lat = backStackEntry.arguments?.getString("lat")?.toDouble()
            val lon = backStackEntry.arguments?.getString("lon")?.toDouble()

            WeatherScreen(
                modifier = modifier,
                navController = navController,
                latitude = lat,
                longitude = lon
            )
        }

    }
}
