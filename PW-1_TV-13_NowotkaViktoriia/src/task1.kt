import math.round as round
import FuelCalculations as FuelCalculations


fun main() {
    val calculator = Task1()

    println("Коефіцієнт переходу від робочої до сухої маси становить: ${round(calculator.workDryCoeff)}")
    println("Коефіцієнт переходу від робочої до горючої маси становить: ${round(calculator.workBurnCoeff)}")

    val dryFuelPercents = calculator.calculateDryFuelPercentage()
    val burnFuelPercents = calculator.calculateBurnFuelPercentage()

    println("""Відсотковий склад сухої маси палива:
        вуглецю ${dryFuelPercents[0]}%, водню ${dryFuelPercents[1]}%, сірки ${dryFuelPercents[2]}%
        азоту ${dryFuelPercents[3]}%, кисню ${dryFuelPercents[4]}%, золи ${dryFuelPercents[5]}% і помилка ${dryFuelPercents[6]}.
    """.trimMargin())

    println("""Відсотковий склад горючої маси палива: 
        вуглецю ${burnFuelPercents[0]}%, водню ${burnFuelPercents[1]}%, сірки ${burnFuelPercents[2]}%
        азоту ${burnFuelPercents[3]}%, кисню ${burnFuelPercents[4]}%% і помилка ${burnFuelPercents[5]}.
    """.trimMargin())

    println("Нижча теплота згоряння для робочої маси за заданим складом компонентів палива становить: ${calculator.heatCombustion} кДж/кг.")

    println("Нижча теплота згоряння для сухої маси за заданим складом компонентів палива становить: ${calculator.dryFuelHeatCombustion} кДж/кг.")

    println("Нижча теплота згоряння для горючої маси за заданим складом компонентів палива становить: ${calculator.burnFuelHeatCombustion} кДж/кг.")
}


class Task1: FuelCalculations {
    var carbon: Double = 0.0
    var hydrogen: Double = 0.0
    var sulfur: Double = 0.0
    var nitrogen: Double = 0.0
    var oxygen: Double = 0.0
    var water: Double = 0.0
    var ach: Double = 0.0
    
    override var workDryCoeff = 0.0
    override var workBurnCoeff = 0.0
    override var heatCombustion = 0.0
    override var dryFuelHeatCombustion = 0.0
    override var burnFuelHeatCombustion = 0.0
    
    init {
        do {
            println("Введіть відсоткове значення складу палива; \nСума відсотків складових палива повинна дорівнювати 100.")
            try {
                print("Відсоток вуглецю ")
                carbon = readln().toDouble()
                print("Відсоток водню ")
                hydrogen = readln().toDouble()
                print("Відсоток сірки ")
                sulfur = readln().toDouble()
                print("Відсоток азоту ")
                nitrogen = readln().toDouble()
                print("Відсоток кисню ")
                oxygen = readln().toDouble()
                print("Відсоток вологи ")
                water = readln().toDouble()
                print("Відсоток золи ")
                ach = readln().toDouble()
            } catch (e: NumberFormatException) {
                println("Помилка: Введіть правильне числове значення.")
            }
            catch (e: Exception) {
                println(e.message)
            }

        } while (carbon + hydrogen + sulfur + nitrogen + oxygen + water + ach !== 100.0)

        calculateWorkDryCoeff()
        calculateWorkBurnCoeff()

        calculateHeatCombustion()
        calculateDryFuelHeatCombustion()
        calculateBurnFuelHeatCombustion()

        showFuelCompound()
    }

    override fun calculateWorkDryCoeff(): Unit {
        workDryCoeff = 100/(100- water)
    }

    override fun calculateWorkBurnCoeff(): Unit {
        workBurnCoeff = 100/(100- water - ach)
    }

    override fun calculateHeatCombustion(): Unit {
        val hc = 339 * carbon + 1030 * hydrogen - 108.8 * ( oxygen - sulfur ) - 25 * water
        heatCombustion = round(hc)
    }

    override fun calculateDryFuelHeatCombustion(): Unit {
        val dhc = (heatCombustion + 0.025 * water) * workDryCoeff
        dryFuelHeatCombustion = round(dhc)
    }

    override fun calculateBurnFuelHeatCombustion(): Unit {
        val bhc = (heatCombustion + 0.025 * water) * workBurnCoeff
        burnFuelHeatCombustion = round(bhc)
    }

    fun calculateDryFuelPercentage(): DoubleArray {
        val dry_carbon = carbon * workDryCoeff
        val dry_hydrogen = hydrogen * workDryCoeff
        val dry_sulfur = sulfur * workDryCoeff
        val dry_nitrogen = nitrogen * workDryCoeff
        val dry_oxygen = oxygen * workDryCoeff
        val dry_ach = ach * workDryCoeff

        val error = 100.0 - (dry_carbon + dry_hydrogen + dry_sulfur + dry_nitrogen + dry_oxygen + dry_ach)

        return doubleArrayOf(round(dry_carbon), round(dry_hydrogen), round(dry_sulfur), round(dry_nitrogen), round(dry_oxygen), round(dry_ach), round(error))
    }

    fun calculateBurnFuelPercentage(): DoubleArray {
        val burn_carbon = carbon * workBurnCoeff
        val burn_hydrogen = hydrogen * workBurnCoeff
        val burn_sulfur = sulfur * workBurnCoeff
        val burn_nitrogen = nitrogen * workBurnCoeff
        val burn_oxygen = oxygen * workBurnCoeff

        val error = 100.0 - (burn_carbon + burn_hydrogen + burn_sulfur + burn_nitrogen + burn_oxygen)

        return doubleArrayOf(round(burn_carbon), round(burn_hydrogen), round(burn_sulfur), round(burn_nitrogen), round(burn_oxygen), round(error))
    }

    fun showFuelCompound(): String {
        return "Паливо містить вуглецю $carbon%, водню $hydrogen%, сірки $sulfur%, " +
                "азоту $nitrogen%, кисню $oxygen%, вологи $water% і золи $ach%."
    }
}

