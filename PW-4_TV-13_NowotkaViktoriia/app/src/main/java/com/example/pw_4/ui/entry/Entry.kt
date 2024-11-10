package com.example.pw_4.ui.entry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val buttonStyles = Modifier.padding(all = 30.dp).background(color = Color.Green)

@Composable
fun EntryScreen(
    onCalculator1Navigate: () -> Unit,
    onCalculator2Navigate: () -> Unit,
) {
    Column(modifier = buttonStyles) {
        Button(
            onClick = { onCalculator1Navigate() },
        ) {
            Text(
                text = "Go to calculator 1"
            )
        }
        Button(
            onClick = onCalculator2Navigate
        ) {
            Text(
                text = "Go to calculator 2"
            )
        }
    }
}