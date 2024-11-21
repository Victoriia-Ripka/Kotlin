package com.example.pw_6.ui.calculator

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pw_6.services.CalculatorService
import com.example.pw_6.ui.components.FancyButton
import androidx.compose.runtime.mutableStateOf
import com.example.pw_6.ui.components.Header
import com.example.pw_6.ui.components.Title
import com.example.pw_6.ui.entry.bodyModifier

@Composable
fun CalculatorScreen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    var resultArray by remember { mutableStateOf(arrayOf(0.0, 0.0)) }
    val w_dk = resultArray.getOrElse(0) { 0.0 }
    val w_ds = resultArray.getOrElse(1) { 0.0 }

    fun calculate(): Unit{

    }

    Column(
        modifier = bodyModifier,
    ){
        Header()

        Title(
            text="Завдання 1" ,
            textStyle = TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Title(
            text="Калькулятор розрахунку електричних навантажень об’єктів" ,
            textStyle = TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        FancyButton(
            text="Розрахувати",
            onClick = {calculate()}
        )

        if (resultArray[0] != 0.0) {
            Box(
                modifier = Modifier.height(50.dp)
            ){
//                Text()
            }

        }

        if (resultArray[0] == 0.0) {
            Spacer(modifier = Modifier.height(50.dp))
        }

        FancyButton(
            text="Повернутися назад",
            onClick = {goBack()}
        )
    }
}