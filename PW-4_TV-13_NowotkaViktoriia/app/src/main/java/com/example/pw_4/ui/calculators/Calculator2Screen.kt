package com.example.pw_4.ui.calculators

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
fun Calculator2Screen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    var uValue by remember { mutableStateOf("") }
    var skValue by remember { mutableStateOf("") }
    var sNomtValue by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun calculateCurrent(): Unit {
        val formattedU = uValue.toDoubleOrNull() ?: 0;
        val formattedSk = skValue.toDoubleOrNull() ?: 0;
        val formattedsNomt = sNomtValue.toDoubleOrNull() ?: 0;

        result = calculatorService.determinateCurrent(formattedU, formattedSk, formattedsNomt).toString()

    }

    Column(modifier = Modifier.padding(all = 15.dp)) {
        TextField(
            value = uValue,
            onValueChange = { uValue = it },
            label = { Text("Ведіть напругу") },
            maxLines = 1,
        )
        TextField(
            value = skValue,
            onValueChange = { skValue = it },
            label = { Text("Введіть потужність КЗ") },
            maxLines = 1,
        )
        TextField(
            value = sNomtValue,
            onValueChange = { sNomtValue = it },
            label = { Text("Введіть номінальну потужність трансформатора") },
            maxLines = 1,
        )
        Button(
            onClick = { calculateCurrent() }
        ) {
            Text("Розрахувати")
        }
        if (result.isNotEmpty()) {
            Text("Результат:  $result кА")
        }
        Box(
            modifier = Modifier.padding(top = 100.dp)
        ) {
            Button(
                onClick = goBack
            ) {
                Text("Повернутися до головного меню")
            }
        }
    }
}