package com.example.pw_1

public interface FuelCalculations {
    var workDryCoeff: Double?
    var workBurnCoeff: Double?
    var burnWorkCoeff: Double?
    var dryWorkCoeff: Double?
    var heatCombustion: Double?
    var workFuelHeatCombustion: Double?
    var dryFuelHeatCombustion: Double?
    var burnFuelHeatCombustion: Double?

    fun calculateWorkDryCoeff() {}
    fun calculateWorkBurnCoeff() {}
    fun calculateBurnWorkCoeff() {}
    fun calculateDryWorkCoeff() {}
    fun calculateHeatCombustion() {}
    fun calculateDryFuelHeatCombustion() {}
    fun calculateBurnFuelHeatCombustion() {}
}