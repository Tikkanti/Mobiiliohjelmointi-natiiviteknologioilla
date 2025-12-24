/*package com.example.weatherapp.ui.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.viewmodel.LocationViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val context = LocalContext.current
    val viewModel = LocationViewModel(context)





    val location by viewModel.getLocationLiveData().observeAsState()



    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                viewModel.getLocationLiveData().getLocationData()

            }
        }
    )

    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Latitude: " + location?.latitude.toString(),
            modifier = Modifier.padding(top = 24.dp)
        )
        Text(
            text = "Longitude: " + location?.longitude.toString(),
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button (
            onClick = {
                requestPermissionLauncher.
                launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        ) {
            Text("Get location")
        }
        Button(
            onClick = { location?.let { navController.navigate("weather/${it.latitude}/${it.longitude}") } }
        ) {
            Text("Go to Weather page")

        }
    }

}*/

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

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val context = LocalContext.current
    val viewModel = LocationViewModel(context)

    val location by viewModel.getLocationLiveData().observeAsState()

    var loading by remember { mutableStateOf(true) }   // oletuksena true
    var error by remember { mutableStateOf<String?>(null) }

    // Käynnistä sijainnin haku heti
    LaunchedEffect(Unit) {
        viewModel.getLocationLiveData().getLocationData()
    }

    // Lopeta lataus kun sijainti päivittyy
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
                Text("Fetching location…")
            }
            error != null -> {
                Text("Error: $error", color = Color.Red)
            }
            location != null -> {
                Text("Latitude: ${location!!.latitude}")
                Text("Longitude: ${location!!.longitude}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("weather/${location!!.latitude}/${location!!.longitude}")
                    }
                ) {
                    Text("Go to Weather page")
                }
            }
            else -> {
                Text("No location yet")
            }
        }
    }
}






