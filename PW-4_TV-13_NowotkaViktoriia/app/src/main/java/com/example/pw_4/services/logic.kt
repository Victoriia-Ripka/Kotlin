package com.example.pw_4.services

import kotlin.math.*
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
        val IK = 2.5*1000
        val tF = 2.5
        val N = 2.0*1000
        val SM = 1300.0

        val selectedCable = findCableByConductorAndType(cableData, conductorInput, cableTypeInput)
        val Jek = selectedCable?.let { findJekValue(it, timeInput) } ?: 0.0
        Log.d("CalculatorService", "Jek: $Jek")

        val IM = (SM / 2) / (sqrt(3.0) * U)
        Log.d("CalculatorService", "IM: $IM")
        val IMPa = 2 * IM

        val Sek = IM / Jek

        val CT = 92
        val Smin = IK * sqrt(tF) / CT
        Log.d("CalculatorService", "Smin: $Smin")
        val result = roundUpToNearest(Smin, 10)

        return result
    }

    fun determinateCurrent(u: Number = 10.5, sk: Number = 200.0, sNomt: Number = 6.3): Double {
        val formattedU = u.toDouble()
        val formattedSk = sk.toDouble()
        val formattedSnomt = sNomt.toDouble()

        val Xc = formattedU.pow(2.0) / formattedSk
        val Xt = formattedU / 100 * (formattedU.pow(2) / formattedSnomt)
        val X = Xc + Xt
        val Ip0 = formattedU / (sqrt(3.0) * X)

        val roundedValue = String.format("%.2f", Ip0).toDouble()
        return roundedValue
    }

    fun determinateSubstationCurrent(): Array<Double> {
        val uAT1 = 750.0
        val uAT2 = 330.0
        val u = arrayOf(6, 10, 35, 110)

        val Rsn110 = 10.65
        val Xcn110 = 24.02
        val Rsmin110 = 34.88
        val Xcmin110 = 65.68

        val sNomt = 6.3
        val uKMax = 11.1
        val uVn = 115.toDouble()

        val xT = (uKMax * uVn.pow(2.0)) / (100 * sNomt)

        val rSH = Rsn110
        val xSH = Xcn110 + xT
        val zSH = sqrt((rSH.pow(2)) + (xSH.pow(2)))

        val rSHmin = Rsmin110
        val xSHmin = Xcmin110 + xT
        val zSHmin = sqrt((rSHmin.pow(2)) + (xSHmin.pow(2)))

        val i3SH = uVn * 1000 / (sqrt(3.0) * zSH)
        val i2SH = i3SH * sqrt(3.0) / 2.0
        val i3SHmin = uVn * 1000 / (sqrt(3.0) * zSHmin)
        val i2SHmin = i3SHmin * sqrt(3.0) / 2.0

        val k = uKMax.pow(2.0) / uVn.pow(2.0)

        val rSHn = rSH * k
        val xSHn = xSH * k
        val zSHn = sqrt((rSHn.pow(2)) + (xSHn.pow(2)))

        val rSHnmin = rSHmin * k
        val xSHnmin = xSHmin * k
        val zSHnmin = sqrt((rSHnmin.pow(2)) + (xSHnmin.pow(2)))

        val i3SHn = uKMax * 1000 / (sqrt(3.0) * zSHn)
        val i2SHn = i3SHn * sqrt(3.0) / 2.0
        val i3SHnmin = uKMax * 1000 / (sqrt(3.0) * zSHnmin)
        val i2SHnmin = i3SHnmin * sqrt(3.0) / 2.0

        val l = 12.37
        val r0 = 0.64
        val x0 = 0.363
        val rL = l * r0
        val xL = l * x0

        val rSum = rL + rSHn
        val xSum = xL + xSHn
        val zSum = sqrt((rSum.pow(2)) + (xSum.pow(2)))
        val rSumMin = rL + rSHnmin
        val xSumMin = xL + xSHnmin
        val zSumMin = sqrt((rSumMin.pow(2)) + (xSumMin.pow(2)))

        val i3LN = uKMax * 1000 / (sqrt(3.0) * zSum)
        val i2LN = i3LN * sqrt(3.0) / 2
        val i3LNmin = uKMax * 1000 / (sqrt(3.0) * zSumMin)
        val i2LNmin = i3LNmin * sqrt(3.0) / 2

//        Log.d("Service", "i2SHnmin: $i2SHnmin")

        return arrayOf(i3LN, i2LN, i3LNmin, i2LNmin)
    }

}