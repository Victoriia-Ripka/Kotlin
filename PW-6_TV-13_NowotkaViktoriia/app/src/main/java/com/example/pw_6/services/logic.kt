package com.example.pw_6.services

import com.example.pw_6.data.EPInput
import kotlin.math.pow
import kotlin.math.sqrt

class CalculatorService{
    fun calculateNPh(epInputs: List<EPInput>): List<Double> {
        val results = epInputs.map { input ->
            val count = input.count.toIntOrNull() ?: 0
            val capacity = input.capacity.toDoubleOrNull() ?: 0.0
            count * capacity
        }
        return results
    }

//    fun calculateI(epInputs: List<EPInput>): List<Double> {
//        val NPhList = calculateNPh(count, capacityEP)
//        val I = NPh / (sqrt(3.0) * voltage * coeffPower * coeffUsefulAct)
//        return I
//    }

//    fun calculateGroupUtilizationCoeff(epInputs: List<EPInput>): List<Double> {
//        val KV = count * capacityEP * coefUsage / (count*capacityEP)
//        return KV
//    }

//    fun calculateEfCount(epInputs: List<EPInput>): List<Double> {
//        return (count * capacityEP).pow(2) / (count * capacityEP.pow(2))
//    }

//    fun calculatePp(Kp: Double, Kv: Double, epInputs: List<EPInput>): List<Double>{
//        return Kp * Kv * Ph
//    }

}