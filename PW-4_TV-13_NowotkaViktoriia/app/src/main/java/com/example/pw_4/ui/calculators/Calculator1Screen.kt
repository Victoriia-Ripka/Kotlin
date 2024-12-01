package com.example.pw_4.ui.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pw_4.services.CalculatorService
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculator1Screen(
    goBack: () -> Unit
) {
    var conductor by remember { mutableStateOf("") }
    var cableType by remember { mutableStateOf("") }
    var timeRange by remember { mutableIntStateOf(1000) }
    var result by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calculatorService = CalculatorService(context)

    val conductors = listOf(
        "Неізольовані проводи та шини",
        "Кабелі з паперовою та проводи гумовою ізоляцією",
        "Кабелі з гумовою та пластмасовою ізоляцією"
    )
    val cableTypes = listOf("Алюмінієві", "Мідні")
    val timeRanges = (1000..10000 step 1000).toList()

    var isConductorDropdownExpanded by remember { mutableStateOf(false) }
    var isCableTypeDropdownExpanded by remember { mutableStateOf(false) }
    var isTimeRangeDropdownExpanded by remember { mutableStateOf(false) }

    fun calculateResult(): Unit {
        result = calculatorService.calculateCablesCompatibility(conductor, cableType, timeRange.toDouble())
            .toString()
    }

    Column(modifier = Modifier.padding(all = 15.dp)) {
        // Conductor Dropdown
        ExposedDropdownMenuBox(expanded = isConductorDropdownExpanded, onExpandedChange = { isConductorDropdownExpanded = it }) {
            TextField(
                value = conductor,
                onValueChange = { conductor = it },
                label = { Text("Оберіть проводник") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isConductorDropdownExpanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = isConductorDropdownExpanded, onDismissRequest = { isConductorDropdownExpanded = false }) {
                conductors.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            conductor = item
                            isConductorDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cable Type Dropdown
        ExposedDropdownMenuBox(expanded = isCableTypeDropdownExpanded, onExpandedChange = { isCableTypeDropdownExpanded = it }) {
            TextField(
                value = cableType,
                onValueChange = { cableType = it },
                label = { Text("Оберіть тип кабелю") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCableTypeDropdownExpanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = isCableTypeDropdownExpanded, onDismissRequest = { isCableTypeDropdownExpanded = false }) {
                cableTypes.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            cableType = item
                            isCableTypeDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Time Range Dropdown
        ExposedDropdownMenuBox(expanded = isTimeRangeDropdownExpanded, onExpandedChange = { isTimeRangeDropdownExpanded = it }) {
            TextField(
                value = timeRange.toString(),
                onValueChange = { timeRange = it.toIntOrNull() ?: timeRange },
                label = { Text("Зазначте час (1000-10000)") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isTimeRangeDropdownExpanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = isTimeRangeDropdownExpanded, onDismissRequest = { isTimeRangeDropdownExpanded = false }) {
                timeRanges.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.toString()) },
                        onClick = {
                            timeRange = item
                            isTimeRangeDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calculate Button
        Button(
            onClick = { calculateResult() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Рохрахувати")
        }

        if (result.isNotEmpty()) {
            Text("Результат: потрібно змінити переріз жил до $result мм^2")
        }

        Box(
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Button(
                onClick = goBack
            ) {
                Text("Повернутися до головного меню")
            }
        }
    }
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}

