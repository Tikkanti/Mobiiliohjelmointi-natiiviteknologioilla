package com.example.bmicalculator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {

    var heightInput by mutableStateOf("")
    var weightInput by mutableStateOf("")

    private val height: Float
        get() {
            return heightInput.toFloatOrNull() ?: 0f
        }

    private val weight: Float
        get() {
            return weightInput.toFloatOrNull() ?: 0f

        }

    val result: Float
        get() {
            return if (height > 0 && weight > 0) {
                weight / (height * height)
            } else {
                0f
            }

        }
}