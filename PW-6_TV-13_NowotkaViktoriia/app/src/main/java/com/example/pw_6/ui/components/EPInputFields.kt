package com.example.pw_6.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.pw_6.data.EPInput
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EPInputFields(
    epInput: EPInput,
    onUpdate: (EPInput) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = epInput.name,
            onValueChange = { onUpdate(epInput.copy(name = it)) },
            label = { Text("Найменування ЕП") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = epInput.coeffUsefulAct,
            onValueChange = { onUpdate(epInput.copy(coeffUsefulAct = it)) },
            label = { Text("Номінальне значення ККД") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = epInput.coeffPower,
            onValueChange = { onUpdate(epInput.copy(coeffPower = it)) },
            label = { Text("Коефіцієнт потужності навантаження") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = epInput.voltage,
            onValueChange = { onUpdate(epInput.copy(voltage = it)) },
            label = { Text("Напруга навантаження") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = epInput.count,
            onValueChange = { onUpdate(epInput.copy(count = it)) },
            label = { Text("Кількість ЕП") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = epInput.capacity,
            onValueChange = { onUpdate(epInput.copy(capacity = it)) },
            label = { Text("Номінальна потужність ЕП") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = epInput.coefUsage,
            onValueChange = { onUpdate(epInput.copy(coefUsage = it)) },
            label = { Text("Коефіцієнт використання") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = epInput.coeffReactPower,
            onValueChange = { onUpdate(epInput.copy(coeffReactPower = it)) },
            label = { Text("Коефіцієнт реактивної потужності") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
