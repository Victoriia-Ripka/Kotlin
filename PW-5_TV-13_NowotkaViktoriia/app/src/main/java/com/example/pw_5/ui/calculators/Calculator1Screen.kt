package com.example.pw_5.ui.calculators

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
import com.example.pw_5.services.CalculatorService
import com.example.pw_5.ui.components.FancyButton
import androidx.compose.runtime.mutableStateOf
import com.example.pw_5.ui.components.Header
import com.example.pw_5.ui.components.Title
import com.example.pw_5.ui.entry.bodyModifier

@Composable
fun Calculator1Screen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    var resultArray by remember { mutableStateOf(arrayOf(0.0, 0.0)) }
    val w_dk = resultArray.getOrElse(0) { 0.0 }
    val w_ds = resultArray.getOrElse(1) { 0.0 }

    fun calculate(): Unit{
        resultArray = calculatorService.compareReliabilitySystems()
        Log.d("Calculator1", "w_dk screen: $w_dk")
        Log.d("Calculator1", "w_ds screen: $w_ds")
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
            text="Порівняння надійності одноколової та двоколової систем електропередач" ,
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
                Text("Результат: w_dk = $w_dk, w_ds = $w_ds \n" +
                          "Надійність двоколової системи є вищою")
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