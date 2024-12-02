package com.example.pw_6.ui.calculator

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
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun CalculatorScreen(
    goBack: () -> Unit,
    calculatorService: CalculatorService
) {
    val context = LocalContext.current
    val epInputs = remember { mutableStateListOf<EPInput>() }
    val epExtraInputs = remember { mutableStateListOf<EPInput>() }
    var resultArray by remember { mutableStateOf(DoubleArray(14)) }

    val allCount = 81.0
    val allNPh = 2330.0
    val allNPK = 752.0
    val allNPKtg = 657.0
    val allNP2 = 96399.0

    LaunchedEffect(Unit) {
        repeat(8) { epInputs.add(EPInput()) }
        repeat(2) { epExtraInputs.add(EPInput()) }
    }

    fun Double.trimToTwoDecimals(): Double {
        return String.format("%.2f", this).toDouble()
    }

    fun loadJsonFromAssets(fileName: String): String {
        return try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            jsonString
        } catch (e: Exception) {
            Log.e("CalculatorScreen", "Error loading JSON file: ${e.message}")
            ""
        }
    }

    fun parseJsonToEPInputs(json: String): List<EPInput> {
        return try {
            val parsedList: List<EPInput> = Json.decodeFromString(json)
            parsedList
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("CalculatorScreen", "Error parsing JSON: ${e.message}")
            emptyList()
        }
    }

    fun calculate() {
        try {
            //  step3
            val NPhList = calculatorService.calculateNPh(epInputs)              // 80, 28, ...
            //        Log.d("Calculator", "NPhList: $NPhList")
            val NPhSum = calculatorService.calculateSumNPh(NPhList)             // 456
            //        Log.d("Calculator", "NPh: $NPhSum")
            val I = calculatorService.calculateI(epInputs)                       // 146, 51, ...
            //        Log.d("Calculator", "I: $I")
            val sumCount = calculatorService.calculateSumCount(epInputs)        // 16
            //        Log.d("Calculator", "I: $I")

            //  step4
            val KV = calculatorService.calculateGroupUtilizationCoeff(epInputs) // 0.208
            //        Log.d("Calculator", "KV: $KV")
            val nE = calculatorService.calculateEfCount(NPhSum, epInputs)       // 15
            //        Log.d("Calculator", "nE: $nE")
            val Kp = 1.25
            //        how?? table 6.3/1.3
            val Pp = calculatorService.calculatePp(Kp, epInputs)                // 118
            //        Log.d("Calculator", "Pp: $Pp")
            val Qp = calculatorService.calculateQp(nE, epInputs)                // 107
            //        Log.d("Calculator", "Qp: $Qp")
            val Sp = calculatorService.calculateSp(Pp, Qp)                      // 160
            //        Log.d("Calculator", "Sp: $Sp")
            val Ip = calculatorService.calculateIp(Pp, epInputs[3].voltage.toDouble())  // 313
            //        Log.d("Calculator", "Ip: $Ip")

            // step 6
            val allKV = allNPK / allNPh
            //  Log.d("Calculator all", "KV: $allKV") // 0.32
            val allNe = allNPh.pow(2.0) / allNP2
            //  Log.d("Calculator all", "nE: $allNe") // 56
            val allKp = 0.7

            val allPp = allKp * allNPK
            //  Log.d("Calculator all", "Pp: $allPp") // 526
            val allQp = allKp * allNPKtg
            //  Log.d("Calculator all", "Qp: $allQp") // 459.9
            val allSp = sqrt(allPp.pow(2.0) + allQp.pow(2.0))
            //  Log.d("Calculator all", "Sp: $allSp") // 699
            val allIp = allPp /  epInputs[3].voltage.toDouble()
            //  Log.d("Calculator all", "Ip: $allIp") // 1385.26


            resultArray = doubleArrayOf(
                KV.trimToTwoDecimals(),
                nE.toDouble().trimToTwoDecimals(),
                Kp.trimToTwoDecimals(),
                Pp.trimToTwoDecimals(),
                Qp.trimToTwoDecimals(),
                Sp.trimToTwoDecimals(),
                Ip.trimToTwoDecimals(),
                allKV.trimToTwoDecimals(),
                allNe.trimToTwoDecimals(),
                allKp.trimToTwoDecimals(),
                allPp.trimToTwoDecimals(),
                allQp.trimToTwoDecimals(),
                allSp.trimToTwoDecimals(),
                allIp.trimToTwoDecimals()
            )

            Log.d("Calculator", "resultArray: ${resultArray.joinToString()}")
        } catch (e: Exception) {
            Log.e("CalculatorScreen", "Error during calculation: ${e.message}")
        }
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
                }
            } catch (e: Exception) {
                Log.e("CalculatorScreen", "Error loading inputs: ${e.message}")
            }
        }) {
            Text("Заповнити поля")
        }

        Spacer(modifier = Modifier.height(20.dp))

        epExtraInputs.forEachIndexed { index, epInput ->
            Text(
                text = "Крупні ЕП #${index + 1}",
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
                val json = loadJsonFromAssets("extraEPdata.json")
                val parsedInputs = parseJsonToEPInputs(json)
                if (parsedInputs.isEmpty()) {
                    Log.e("CalculatorScreen", "Parsed inputs are empty. Check JSON structure.")
                } else {
                    epExtraInputs.clear()
                    epExtraInputs.addAll(parsedInputs)
//                    Log.d("CalculatorScreen", "EPInputs populated successfully.")
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
        Spacer(modifier = Modifier.height(20.dp))

        FancyButton(
            text = "Повернутися назад",
            onClick = { goBack() }
        )

        if (resultArray.isNotEmpty() && resultArray[0] > 0.0) {
            Text(
                text = "Для заданого складу ЕП та їх характеристик цехової мережі силове навантаження становитиме",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Груповий коефіцієнт використання для ШР1=ШР2=ШР3: ${resultArray[0]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Ефективна кількість ЕП для ШР1=ШР2=ШР3: ${resultArray[1]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Розрахунковий коефіцієнт активної потужності для ШР1=ШР2=ШР3: ${resultArray[2]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Розрахункове активне навантаження для ШР1=ШР2=ШР3: ${resultArray[3]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Розрахункове реактивне навантаження для ШР1=ШР2=ШР3: ${resultArray[4]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Повна потужність для ШР1=ШР2=ШР3: ${resultArray[5]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Розрахунковий груповий струм для ШР1=ШР2=ШР3: ${resultArray[6]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Коефіцієнти використання цеху в цілому: ${resultArray[7]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Ефективна кількість ЕП цеху в цілому: ${resultArray[8]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Розрахунковий коефіцієнт активної потужності цеху в цілому: ${resultArray[9]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Розрахункове активне навантаження на шинах 0,38 кВ ТП: ${resultArray[10]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Розрахункове реактивне навантаження на шинах 0,38 кВ ТП: ${resultArray[11]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Повна потужність на шинах 0,38 кВ ТП: ${resultArray[12]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Розрахунковий груповий струм на шинах 0,38 кВ ТП: ${resultArray[13]}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}
