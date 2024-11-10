package com.example.pw_4.services

import kotlin.math.sqrt
import kotlin.math.ceil
import android.util.Log
import android.content.Context
import org.json.JSONObject
import org.json.JSONArray

class CalculatorService(private val context: Context) {
//    private val cableData: List<CableTime> = loadCableData(context)

    private fun roundUpToNearest(number: Double, base: Int): Int {
        return (ceil(number / base.toDouble()) * base).toInt()
    }

    private fun findCableByConductorAndType(jsonArray: JSONArray, conductor: String, type: String): JSONObject? {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            if (jsonObject.getString("conductor") == conductor && jsonObject.getString("type") == type) {
                return jsonObject
            }
        }
        return null
    }

    private fun findJekValue(selectedCable: JSONObject, timeInput: Double): Double {
        val timeData = selectedCable.getJSONObject("time")
        val ranges = listOf("1000-3000", "3000-5000", "5000-10000")
        for (range in ranges) {
            val (start, end) = range.split("-").map { it.toInt() }
            if (timeInput >= start && timeInput <= end) {
                return timeData.getDouble(range)
            }
        }
        throw IllegalArgumentException("No matching time range found for time input: $timeInput")
    }

    fun calculateCablesCompatibility(conductorInput: String, cableTypeInput: String, timeInput: Double): Int{
        val cableData: JSONArray = loadCableData(context)

        Log.d("CalculatorService", "conductorInput: $conductorInput")
        Log.d("CalculatorService", "cableTypeInput: $cableTypeInput")
        Log.d("CalculatorService", "timeInput: $timeInput")
        Log.d("CalculatorService", "cableData: $cableData")

        val U = 10.0
        val IK = 2.5
        val tF = 2.5
        val N = 2.0*1000
        val SM = 1300.0

        val selectedCable = findCableByConductorAndType(cableData, conductorInput, cableTypeInput)
        val Jek = selectedCable?.let { findJekValue(it, timeInput) } ?: 0.0
        Log.d("CalculatorService", "Jek: $Jek")

        val IM = (SM / 2) / (sqrt(3.0) * U)
        Log.d("CalculatorService", "IM: $cableData")
        val IMPa = 2 * IM

        val Sek = IM / Jek

        val CT = 92
        val Smin = IK * sqrt(tF) / CT
        val result = roundUpToNearest(Smin, 10)

        return result
    }

    fun sumValues(value1: Int, value2: Int) = value1 + value2

}