package com.example.pw_6.ui.calculator

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pw_6.services.CalculatorService
import com.example.pw_6.ui.components.FancyButton
import com.example.pw_6.ui.components.Header
import com.example.pw_6.ui.components.Title
import com.example.pw_6.ui.entry.bodyModifier

@Composable
fun CalculatorScreen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    var nameEP by remember { mutableStateOf("") }
    var coeffUsefulActInput by remember { mutableStateOf("") }
    var coeffPowerInput by remember { mutableStateOf("") }
    var voltageInput by remember { mutableStateOf("") }
    var countEPInput by remember { mutableStateOf("") }
    var capacityEPInput by remember { mutableStateOf("") }
    var coefUsageInput by remember { mutableStateOf("") }
    var coeffReactPowerInput by remember { mutableStateOf("") }

    var resultArray by remember { mutableStateOf(arrayOf(0.0, 0.0)) }

    fun calculate() {
        val coeffUsefulAct = coeffUsefulActInput.toDoubleOrNull() ?: 0.0
        val coeffPower = coeffPowerInput.toDoubleOrNull() ?: 0.0
        val voltage = voltageInput.toDoubleOrNull() ?: 0.0
        val countEP = countEPInput.toDoubleOrNull() ?: 0.0
        val capacityEP = capacityEPInput.toDoubleOrNull() ?: 0.0
        val coefUsage = coefUsageInput.toDoubleOrNull() ?: 0.0
        val coeffReactPower = coeffReactPowerInput.toDoubleOrNull() ?: 0.0

        // Perform calculations using calculatorService

        Log.d("Calculator", "Result: ${resultArray.joinToString()}")
    }

    Column(
        modifier = bodyModifier.verticalScroll(rememberScrollState()),
    ) {
        Header()

        Title(
            text = "Завдання 1",
            textStyle = TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Title(
            text = "Калькулятор розрахунку електричних навантажень об’єктів.",
            textStyle = TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = nameEP,
            onValueChange = { nameEP = it },
            label = { Text("Найменування ЕП") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = coeffUsefulActInput,
            onValueChange = { coeffUsefulActInput = it },
            label = { Text("Номінальне значення ККД") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = coeffPowerInput,
            onValueChange = { coeffPowerInput = it },
            label = { Text("Коефіцієнт потужності навантаження") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = voltageInput,
            onValueChange = { voltageInput = it },
            label = { Text("Напруга навантаження") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = countEPInput,
            onValueChange = { countEPInput = it },
            label = { Text("Кількість ЕП") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = capacityEPInput,
            onValueChange = { capacityEPInput = it },
            label = { Text("Номінальна потужність ЕП") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = coefUsageInput,
            onValueChange = { coefUsageInput = it },
            label = { Text("Коефіцієнт використання") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = coeffReactPowerInput,
            onValueChange = { coeffReactPowerInput = it },
            label = { Text("Коефіцієнт реактивної потужності") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        FancyButton(
            text = "Розрахувати",
            onClick = { calculate() }
        )

        if (resultArray.isNotEmpty() && resultArray[0] != 0.0) {
            Text(
                text = "Результат: ${resultArray.joinToString()}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        FancyButton(
            text = "Повернутися назад",
            onClick = { goBack() }
        )
    }
}
