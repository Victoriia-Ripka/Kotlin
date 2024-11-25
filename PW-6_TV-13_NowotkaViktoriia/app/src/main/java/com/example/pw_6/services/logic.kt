package com.example.pw_6.services

import android.util.Log
import com.example.pw_6.data.EPInput
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class CalculatorService{
    fun calculateNPK(epInputs: List<EPInput>): List<Double>{
        val nPK = epInputs.map { input ->
            val count = input.count.toIntOrNull() ?: 0
            val capacity = input.capacity.toDoubleOrNull() ?: 0.0
            val coefUsage = input.coefUsage.toDoubleOrNull() ?: 0.0

            count.toDouble() * capacity * coefUsage
        }
        return nPK
    }

    fun calculatePKTgSum(epInputs: List<EPInput>): Double {
        val nPK = calculateNPK(epInputs)

        val kptg = epInputs.mapIndexed { index, input ->
            val coeffReactPower = input.coeffReactPower.toDoubleOrNull() ?: 0.0
            coeffReactPower * nPK[index]
        }
//        Log.d("Service", "kptg: ${kptg.sum()}")
        return kptg.sum()
    }




    fun calculateNPh(epInputs: List<EPInput>): List<Double> {
        val NPhList = epInputs.map { input ->
            val count = input.count.toIntOrNull() ?: 0
            val capacity = input.capacity.toDoubleOrNull() ?: 0.0
            count * capacity
        }
        return NPhList
    }

    fun calculateSumNPh(NPhList: List<Double>): Double {
        val NPhSum = NPhList.sum()
        return NPhSum
    }

    fun calculateI(epInputs: List<EPInput>): List<Double> {
        return epInputs.map { input ->
            val count = input.count.toIntOrNull() ?: 0
            val capacity = input.capacity.toDoubleOrNull() ?: 0.0
            val voltage = input.voltage.toDoubleOrNull() ?: 0.0
            val coeffPower = input.coeffPower.toDoubleOrNull() ?: 0.0
            val coeffUsefulAct = input.coeffUsefulAct.toDoubleOrNull() ?: 0.0

            // Avoid division by zero
            if (voltage == 0.0 || coeffPower == 0.0 || coeffUsefulAct == 0.0) {
                0.0
            } else {
                val NPh = count * capacity
                NPh / (sqrt(3.0) * voltage * coeffPower * coeffUsefulAct)
            }
        }
    }

    fun calculateSumCount(epInputs: List<EPInput>): Int{
        return epInputs.sumOf { input ->
            input.count.toIntOrNull() ?: 0
        }
    }

    fun calculateGroupUtilizationCoeff(epInputs: List<EPInput>): Double {
        val nPK = calculateNPK(epInputs)

        val nP = epInputs.map { input ->
            val count = input.count.toIntOrNull() ?: 0
            val capacity = input.capacity.toDoubleOrNull() ?: 0.0

            count * capacity
        }

        val result = nPK.sum() / nP.sum()
        return result

    }

    fun calculateEfCount(NPh: Double, epInputs: List<EPInput>): Int {
        val sum = epInputs.map { input ->
            val count = input.count.toIntOrNull() ?: 0
            val capacity = input.capacity.toDoubleOrNull() ?: 0.0

            (count * capacity.pow(2))
        }
//        Log.d("Service", "sum: $sum")

        val result = (NPh.pow(2.0) / sum.sum()).roundToInt() + 1
//        Log.d("Service", "result: $result")
        return result
    }

    fun calculatePp(Kp: Double, epInputs: List<EPInput>): Double {
        val nPK = calculateNPK(epInputs)
        return Kp * nPK.sum()
    }

    fun calculateQp(en: Int, epInputs: List<EPInput>): Double {
        if(en <= 10) {
            val kptg = calculatePKTgSum(epInputs)
            return kptg * 1.1
        }
        else if (en > 10){
            val kptg = calculatePKTgSum(epInputs)
            return kptg
        }
        return 0.0
    }

    fun calculateSp(Pp: Double, Qp: Double): Double{
        return sqrt(Pp.pow(2.0) + Qp.pow(2.0))
    }

    fun calculateIp(Pp: Double, Up: Double): Double{
        return Pp / Up
    }

}