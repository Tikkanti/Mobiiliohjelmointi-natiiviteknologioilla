package com.example.calories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calories.ui.components.Calculation
import com.example.calories.ui.components.GenderChoices
import com.example.calories.ui.components.Heading
import com.example.calories.ui.components.IntensityList
import com.example.calories.ui.components.WeightField
import com.example.calories.ui.theme.CaloriesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaloriesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalorieApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}




@Composable
fun CalorieApp( modifier: Modifier = Modifier) {
    var weightInput by remember { mutableStateOf("") }
    val weight = weightInput.toIntOrNull() ?: 0
    var male by remember { mutableStateOf(true) }
    var intensity by remember { mutableStateOf(1.3f) }
    var result by remember{mutableIntStateOf(0)}

    Column (
        modifier = modifier.padding(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Heading("Calories")
        WeightField(weightInput = weightInput,onValueChange={weightInput = it})
        GenderChoices(male, setGenderMale = {male = it})
        IntensityList (onClick={ intensity = it })
        Text(
            text = result.toString(),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Calculation(
            male = male,
            weight = weight,
            intensity=intensity,
            setResult = {result = it}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalorieAppPreview() {
    CaloriesTheme {
        CalorieApp()
    }
}