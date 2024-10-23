package com.example.pw3

import java.util.Locale

fun round(vararg nums: Int): DoubleArray {
    val newNums = mutableListOf<Double>()
    for (num in nums) {
        var newNum = String.format(Locale.US, "%.2f", num).toDouble()
        newNums.add(newNum)
    }
    return newNums.toDoubleArray()
}

fun round(vararg nums: Double): DoubleArray {
    val newNums = mutableListOf<Double>()
    for (num in nums) {
        var newNum = String.format(Locale.US, "%.2f", num).toDouble()
        newNums.add(newNum)
    }
    return newNums.toDoubleArray()
}

fun round(vararg nums: Float): FloatArray {
    val newNums = mutableListOf<Float>()
    for (num in nums) {
        var newNum = String.format(Locale.US, "%.2f", num).toFloat()
        newNums.add(newNum)
    }
    return newNums.toFloatArray()
}