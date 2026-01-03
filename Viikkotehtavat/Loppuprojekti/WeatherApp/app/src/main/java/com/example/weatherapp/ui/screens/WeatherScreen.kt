package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    latitude: Double?,
    longitude: Double?
) {
    val viewModel: WeatherViewModel = viewModel()
    val weather by viewModel.weather.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()

    val apiKey = ""

    LaunchedEffect(latitude, longitude) {
        if (latitude != null && longitude != null) {
            viewModel.loadWeather(latitude, longitude, apiKey)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            loading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Loading weather…")
            }
            error != null -> {
                Text("Error: $error", color = Color.Red)
            }
            weather != null -> {
                Text("City: ${weather!!.name}")
                Text("Temperature: ${weather!!.main.temp} °C")
                Text("Feels like: ${weather!!.main.feels_like} °C")
                Text("Humidity: ${weather!!.main.humidity}%")
                Text("Description: ${weather!!.weather[0].description}")
            }
        }
        Button(
            onClick = { navController.navigate("location") }
        ) {
            Text("Go to Location page")
        }
    }
}


