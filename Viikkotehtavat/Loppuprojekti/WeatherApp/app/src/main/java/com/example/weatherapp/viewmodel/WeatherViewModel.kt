package com.example.weatherapp.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadWeather(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getWeather(lat, lon, apiKey)
            result.onSuccess {
                _weather.value = it
                _error.value = null
            }.onFailure { e ->
                _error.value = when (e) {
                    is retrofit2.HttpException -> "Unauthorized (check API key)"
                    is java.io.IOException -> "Network error, check connection"
                    else -> "Unexpected error: ${e.localizedMessage}"
                }
            }
            _loading.value = false
        }
    }
}


