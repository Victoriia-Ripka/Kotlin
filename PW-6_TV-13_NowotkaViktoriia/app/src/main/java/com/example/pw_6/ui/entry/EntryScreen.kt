package com.example.pw_6.ui.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pw_6.ui.components.FancyButton
import com.example.pw_6.ui.components.Header

val bodyModifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
val titleBoxModifier = Modifier.fillMaxWidth().padding(bottom = 30.dp)

@Composable
fun EntryScreen(
    onCalculator1Navigate: () -> Unit
) {
    Column(
        modifier = bodyModifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Header()

        FancyButton(
            text = "Калькулятор 1",
            onClick = { onCalculator1Navigate() },
            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )
    }
}