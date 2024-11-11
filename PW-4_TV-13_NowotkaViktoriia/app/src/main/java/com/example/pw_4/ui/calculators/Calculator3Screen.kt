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
fun Calculator3Screen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    var resultArray by remember { mutableStateOf(arrayOf<Double>()) }
    val i3LN = if (resultArray.isNotEmpty()) resultArray[0] else 0.0
    val i2LN = if (resultArray.isNotEmpty()) resultArray[1] else 0.0
    val i3LNmin = if (resultArray.isNotEmpty()) resultArray[2] else 0.0
    val i2LNmin = if (resultArray.isNotEmpty()) resultArray[3] else 0.0

    fun calculateResult(): Unit {
        resultArray = calculatorService.determinateSubstationCurrent()
    }

    Column(modifier = Modifier.padding(all = 15.dp)) {
        Button(
            onClick = { calculateResult() }
        ) {
            Text("Розрахувати")
        }
        if (resultArray.isNotEmpty()) {
            Text("Результат: I 3 = $i3LN А, I 2 = $i2LN А, I 3 min = $i3LNmin А, I 2 min = $i2LNmin А. \n" +
            "Аварійний режим не передбачено")
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