package com.example.pw_5.ui.calculators

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pw_5.services.CalculatorService
import com.example.pw_5.ui.components.FancyButton
import com.example.pw_5.ui.components.Header
import com.example.pw_5.ui.components.Title
import com.example.pw_5.ui.entry.bodyModifier

@Composable
fun Calculator2Screen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    var result by remember { mutableStateOf("") }

    fun calculate(): Unit{
        result = calculatorService.calculateLoses()
        Log.d("Calculator2", "result screen: $result")
    }

    Column(
        modifier = bodyModifier,
    ){
        Header()

        Title(
            text="Завдання 2" ,
            textStyle = TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Title(
            text="Розрахунок збитків від перерв електропостачання" ,
            textStyle = TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))


        FancyButton(
            text="Розрахувати",
            onClick = {calculate()}
        )

        if (result.isNotEmpty()) {
            Box(
                modifier = Modifier.height(70.dp)
            ){
                Text("Математичне сподівання збитків від переривання електропостачання: $result")
            }
        }

        if (result.isEmpty()) {
            Spacer(modifier = Modifier.height(70.dp))
        }

        FancyButton(
            text="Повернутися назад",
            onClick = {goBack()}
        )
    }
}