package com.example.weatherapp.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.viewmodel.LocationViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.example.weatherapp.R

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val context = LocalContext.current
    val viewModel = LocationViewModel(context)

    val location by viewModel.getLocationLiveData().observeAsState()

    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        viewModel.getLocationLiveData().getLocationData()
    }


    LaunchedEffect(location) {
        if (loading && location != null) {
            loading = false
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
                Text(stringResource(R.string.fetching_location))
            }
            error != null -> {
                Text(stringResource(R.string.error, error!!), color = Color.Red)
            }
            location != null -> {
                Text(stringResource(R.string.latitude, location!!.latitude))
                Text(stringResource(R.string.longitude, location!!.longitude))

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("weather/${location!!.latitude}/${location!!.longitude}")
                    }
                ) {
                    Text(stringResource(R.string.go_to_weather_page))
                }
            }
            else -> {
                Text(stringResource(R.string.no_location_yet))
            }
        }
    }
}






