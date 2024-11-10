package com.example.pw_4.ui.calculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pw_4.services.CalculatorService

@Composable
fun CalculatorScreen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    var value1 by remember { mutableStateOf("") }
    var value2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun calculateSum(): Unit {
        val formattedValue1 = value1.toIntOrNull() ?: 0;
        val formattedValue2 = value2.toIntOrNull() ?: 0;

        result = calculatorService.sumValues(formattedValue1, formattedValue2).toString()
    }

    Column(modifier = Modifier.padding(all = 15.dp)) {
        TextField(
            value = value1,
            onValueChange = { value1 = it },
            label = { Text("Enter first number") },
            maxLines = 1,
        )
        TextField(
            value = value2,
            onValueChange = { value2 = it },
            label = { Text("Enter second number") },
            maxLines = 1,
        )
        Button(
            onClick = { calculateSum() }
        ) {
            Text("Calculate sum")
        }
        if (result.isNotEmpty()) {
            Text("Result $result")
        }
        Box(
            modifier = Modifier.padding(top = 100.dp)
        ) {
            Button(
                onClick = goBack
            ) {
                Text("Go back")
            }
        }
    }
}