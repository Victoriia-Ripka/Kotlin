package com.example.pw_6.ui.calculator

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pw_6.data.EPInput
import com.example.pw_6.services.CalculatorService
import com.example.pw_6.ui.components.EPInputFields
import com.example.pw_6.ui.components.FancyButton
import com.example.pw_6.ui.components.Header
import com.example.pw_6.ui.components.Title
import com.example.pw_6.ui.entry.bodyModifier
import kotlinx.serialization.json.Json

@Composable
fun CalculatorScreen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    val context = LocalContext.current
    val epInputs = remember { mutableStateListOf<EPInput>() }

    LaunchedEffect(Unit) {
        repeat(8) { epInputs.add(EPInput()) }
    }

    var resultArray by remember { mutableStateOf(arrayOf(0.0, 0.0)) }

    fun loadJsonFromAssets(fileName: String): String {
        return try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            Log.d("CalculatorScreen", "Loaded JSON: $jsonString")
            jsonString
        } catch (e: Exception) {
            Log.e("CalculatorScreen", "Error loading JSON file: ${e.message}")
            ""
        }
    }

    fun parseJsonToEPInputs(json: String): List<EPInput> {
        return try {
            Log.d("CalculatorScreen", "Raw JSON: $json") // Debug JSON string
            val parsedList: List<EPInput> = Json.decodeFromString(json)
            Log.d("CalculatorScreen", "Parsed EPInputs: $parsedList") // Debug parsed objects
            parsedList
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("CalculatorScreen", "Error parsing JSON: ${e.message}")
            emptyList()
        }
    }

    fun calculate() {

        try {
            val NPhList = calculatorService.calculateNPh(epInputs)
            val NPhSum = calculatorService.calculateSumNPh(NPhList)
            val I = calculatorService.calculateI(epInputs)
            val sumCount = calculatorService.calculateSumCount(epInputs)
            val KV = calculatorService.calculateGroupUtilizationCoeff(epInputs)
            val nE = calculatorService.calculateEfCount(epInputs)
            val Kp = 1.25
            val Pp = calculatorService.calculatePp(Kp, epInputs)
            val Qp = calculatorService.calculateQp(nE, epInputs)
            val Sp = calculatorService.calculateSp(Pp, Qp)
            val Ip = calculatorService.calculateIp(Pp, epInputs[3].voltage.toDouble())

            Log.d("CalculatorScreen", "Calculation successful. Results: Ip=$Ip")
        } catch (e: Exception) {
            Log.e("CalculatorScreen", "Error during calculation: ${e.message}")
        }

//      step3
        val NPhList = calculatorService.calculateNPh(epInputs)
//        Log.d("Calculator1", "NPhList: $NPhList")

        val NPhSum = calculatorService.calculateSumNPh(NPhList)
//        Log.d("Calculator1", "NPh: $NPhSum")

        val I = calculatorService.calculateI(epInputs)
//        Log.d("Calculator1", "I: $I")

        val sumCount = calculatorService.calculateSumCount(epInputs)
//        Log.d("Calculator1", "sumCount: $sumCount")

//      step4
        val KV = calculatorService.calculateGroupUtilizationCoeff(epInputs)
//        Log.d("Calculator1", "KV: $KV")

        val nE = calculatorService.calculateEfCount(epInputs)
        Log.d("Calculator2", "nE: $nE")

//        how??
        val Kp = 1.25

        val Pp = calculatorService.calculatePp(Kp, epInputs)
//        Log.d("Calculator2", "Pp: $Pp")

        val Qp = calculatorService.calculateQp(nE, epInputs)
        Log.d("Calculator2", "Qp: $Qp")

        val Sp = calculatorService.calculateSp(Pp, Qp)
        Log.d("Calculator2", "Sp: $Sp")

        val Ip = calculatorService.calculateIp(Pp, epInputs[3].voltage.toDouble())
//        Log.d("Calculator2", "Ip: $Ip")

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

        Spacer(modifier = Modifier.height(20.dp))



        epInputs.forEachIndexed { index, epInput ->
            Text(
                text = "ЕП #${index + 1}",
                style = MaterialTheme.typography.displayLarge
            )
            EPInputFields(
                epInput = epInput,
                onUpdate = { updatedInput ->
                    epInputs[index] = updatedInput
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            try {
                val json = loadJsonFromAssets("testInputs.json")
                val parsedInputs = parseJsonToEPInputs(json)
                if (parsedInputs.isEmpty()) {
                    Log.e("CalculatorScreen", "Parsed inputs are empty. Check JSON structure.")
                } else {
                    epInputs.clear()
                    epInputs.addAll(parsedInputs)
                    Log.d("CalculatorScreen", "EPInputs populated successfully.")
                }
            } catch (e: Exception) {
                Log.e("CalculatorScreen", "Error loading inputs: ${e.message}")
            }
        }) {
            Text("Заповнити поля")
        }

        Spacer(modifier = Modifier.height(20.dp))

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
