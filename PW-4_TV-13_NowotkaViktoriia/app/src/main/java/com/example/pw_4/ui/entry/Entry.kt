package com.example.pw_4.ui.entry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val modifierStyles = Modifier.padding(20.dp)
val titleModifier = Modifier.padding(bottom = 30.dp)
val buttonModifier = Modifier.fillMaxWidth().requiredHeight(height = 70.dp).padding(bottom = 15.dp)
val buttonTextStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)

@Composable
fun EntryScreen(
    onCalculator1Navigate: () -> Unit,
    onCalculator2Navigate: () -> Unit,
    onCalculator3Navigate: () -> Unit,
) {
    Column(modifier = modifierStyles) {
        Text(
            modifier = titleModifier,
            text = "Практична робота №4. \n" +
                    "Розробник: Новотка Вікторія"
        )
        Button(
            modifier = buttonModifier,
            onClick = { onCalculator1Navigate() },
        ) {
            Text(
                text = "Калькулятор 1 (вибір кабелів для живлення)" ,
                style = buttonTextStyle
            )
        }
        Button(
            modifier = buttonModifier,
            onClick = onCalculator2Navigate
        ) {
            Text(
                text = "Калькулятор 2 (визначення струму КЗ на шинах)",
                style = buttonTextStyle
            )
        }
        Button(
            modifier = buttonModifier,
            onClick = onCalculator3Navigate
        ) {
            Text(
                text = "Калькулятор 3 (визначення струму КЗ для підстанції)",
                style = buttonTextStyle
            )
        }
    }
}