package com.example.pw_5.ui.calculators

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pw_5.ui.components.FancyButton
import com.example.pw_5.ui.components.Header
import com.example.pw_5.ui.components.Title

@Composable
fun Calculator1Screen(
    goBack: () -> Unit
) {
    Column(){
        Header()

        Title(
            text="Завдання 1" ,
            textStyle = TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))



        FancyButton(
            text="Розрахувати",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(10.dp))

        FancyButton(
            text="Повернутися назад",
            onClick = {goBack()}
        )
    }
}