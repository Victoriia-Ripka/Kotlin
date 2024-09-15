public interface FuelCalculations {
    var workDryCoeff: Double
    var workBurnCoeff: Double
    var heatCombustion: Double
    var dryFuelHeatCombustion: Double
    var burnFuelHeatCombustion: Double

    fun calculateWorkDryCoeff()
    fun calculateWorkBurnCoeff()
    fun calculateHeatCombustion()
    fun calculateDryFuelHeatCombustion()
    fun calculateBurnFuelHeatCombustion()
}