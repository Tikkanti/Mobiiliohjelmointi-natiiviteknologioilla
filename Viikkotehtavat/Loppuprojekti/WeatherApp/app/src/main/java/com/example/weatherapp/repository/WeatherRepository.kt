package com.example.weatherapp.repository


import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.RetrofitClient
import com.example.weatherapp.network.WeatherApi

class WeatherRepository {

    private val api = RetrofitClient.instance.create(WeatherApi::class.java)

    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Result<WeatherResponse> {
        return try {
            val response = api.getWeatherByCoordinates(lat, lon, apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

