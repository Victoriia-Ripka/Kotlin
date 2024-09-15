// звідкт брати числа?
import math.round as round
import FuelCalculations as FuelCalculations


fun main () {
    val calculator = Task2()

    println("Коефіцієнт переходу від горючої до робочої маси становить: ${round(calculator.burnWorkCoeff!!)}")
    println("Коефіцієнт переходу від сухої до робочої маси становить: ${round(calculator.dryWorkCoeff!!)}")

    val workFuelPercents = calculator.calculateWorkFuelPercentage()

    println("""Відсотковий склад робочої маси мазуту:
        вуглецю ${workFuelPercents[0]}%, водню ${workFuelPercents[1]}%, кисню ${workFuelPercents[2]}%, сірки ${workFuelPercents[3]}%
        золи ${workFuelPercents[4]}% і ванадію ${workFuelPercents[5]} мг/кг.
    """.trimMargin())

    println("Нижча теплота згоряння для робочої маси за заданим складом компонентів мазуту становить: ${calculator.heatCombustion} кДж/кг.")

}


class Task2: FuelCalculations {
    val burn_carbon: Double = 85.5
    val burn_hydrogen: Double = 11.2
    val burn_oxygen: Double = 0.8
    val burn_sulfur: Double = 2.5
    val burn_water: Double = 2.0
    val burn_ach: Double = 0.15
    val burn_vanadium: Double = 333.3


    override var workDryCoeff: Double? = null
    override var workBurnCoeff: Double? = null
    override var burnWorkCoeff: Double? = 0.0
    override var dryWorkCoeff: Double? = 0.0
    override var heatCombustion: Double? = null
    override var workFuelHeatCombustion: Double? = 0.0
    override var dryFuelHeatCombustion: Double? = 0.0
    override var burnFuelHeatCombustion: Double? = 40400.00

    init{
        calculateBurnWorkCoeff()
        calculateDryWorkCoeff()
        calculateHeatCombustion()

        showFuelCompound()
    }

    override fun calculateBurnWorkCoeff(): Unit {
        val whc = (100 - burn_water - burn_ach) / 100
        burnWorkCoeff = round(whc)
    }

    override fun calculateDryWorkCoeff(): Unit {
        val whc = (100 - burn_water ) / 100
        dryWorkCoeff = round(whc)
    }

    fun calculateWorkFuelPercentage(): DoubleArray {
        val work_carbon = burn_carbon * burnWorkCoeff!!
        val work_hydrogen = burn_hydrogen * burnWorkCoeff!!
        val work_oxygen = burn_oxygen * burnWorkCoeff!!
        val work_sulfur = burn_sulfur * burnWorkCoeff!!

        val work_ach = burn_ach * dryWorkCoeff!!
        val work_vanadium = burn_vanadium * dryWorkCoeff!!

        return doubleArrayOf(round(work_carbon), round(work_hydrogen), round(work_oxygen), round(work_sulfur), round(work_ach), round(work_vanadium))
    }

    override fun calculateHeatCombustion(): Unit {
        val hc = burnFuelHeatCombustion!! * burnWorkCoeff!! - 0.025 * burn_water
        heatCombustion = round(hc)
    }

    fun showFuelCompound(): String {
        return "Мазут містить вуглецю $burn_carbon%, водню $burn_hydrogen%, сірки $burn_sulfur%, " +
                "кисню $burn_oxygen%, вологи $burn_water% і золи $burn_ach%, ванадію $burn_vanadium%, " +
                "а нижча теплота горіння $burnFuelHeatCombustion мДж/кг"
    }
}