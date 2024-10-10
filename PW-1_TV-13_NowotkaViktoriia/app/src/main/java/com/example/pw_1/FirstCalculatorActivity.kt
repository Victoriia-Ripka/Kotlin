package com.example.pw_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pw_1.round as round

class FirstCalculatorActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_calculator_activity)

        val h1: EditText = findViewById(R.id.h1)
        val c1: EditText = findViewById(R.id.c1)
        val s1: EditText = findViewById(R.id.s1)
        val n1: EditText = findViewById(R.id.n1)
        val o1: EditText = findViewById(R.id.o1)
        val w1: EditText = findViewById(R.id.w1)
        val a1: EditText = findViewById(R.id.a1)

        val calculateBtn: Button = findViewById(R.id.do_task1)
        val backBtn: Button = findViewById(R.id.from_1_back)
        val errorText: TextView? = findViewById(R.id.error_text1)

        val workDryCoeff: TextView? = findViewById(R.id.workDryCoeff)
        val workBurnCoeff: TextView? = findViewById(R.id.workBurnCoeff)
        val dryFuelPercents: TextView? = findViewById(R.id.dryFuelPercents)
        val burnFuelPercents: TextView? = findViewById(R.id.burnFuelPercents)
        val heatCombustion: TextView? = findViewById(R.id.heatCombustion)
        val dryFuelHeatCombustion: TextView? = findViewById(R.id.dryFuelHeatCombustion)
        val burnFuelHeatCombustion: TextView? = findViewById(R.id.burnFuelHeatCombustion)

        fun checkRequirements(h: Double, c: Double, s: Double, n: Double, o: Double, w: Double, a: Double): Boolean{
            return (h + c + s + n + o + w + a) == 100.0
        }

        fun calculateDryFuelPercentage(workDryCoeffValue: Double, h1Value: Double, c1Value: Double, s1Value: Double, n1Value: Double, o1Value: Double, a1Value: Double): DoubleArray {
            val dry_carbon = c1Value * workDryCoeffValue
            val dry_hydrogen = h1Value * workDryCoeffValue
            val dry_sulfur = s1Value * workDryCoeffValue
            val dry_nitrogen = n1Value * workDryCoeffValue
            val dry_oxygen = o1Value * workDryCoeffValue
            val dry_ach = a1Value * workDryCoeffValue

            val error = 100.0 - (dry_carbon + dry_hydrogen + dry_sulfur + dry_nitrogen + dry_oxygen + dry_ach)

            return round(dry_carbon, dry_hydrogen, dry_sulfur, dry_nitrogen, dry_oxygen, dry_ach, error)
        }

        fun calculateBurnFuelPercentage(workBurnCoeffValue: Double, h1Value: Double, c1Value: Double, s1Value: Double, n1Value: Double, o1Value: Double): DoubleArray {
            val burn_carbon = c1Value * workBurnCoeffValue
            val burn_hydrogen = h1Value * workBurnCoeffValue
            val burn_sulfur = s1Value * workBurnCoeffValue
            val burn_nitrogen = n1Value * workBurnCoeffValue
            val burn_oxygen = o1Value * workBurnCoeffValue

            val error = 100.0 - (burn_carbon + burn_hydrogen + burn_sulfur + burn_nitrogen + burn_oxygen)

            return round(burn_carbon, burn_hydrogen, burn_sulfur, burn_nitrogen, burn_oxygen, error)
        }

        fun showResult(workDryCoeffValue: Double, workBurnCoeffValue: Double, dryFuelPercentsValue: DoubleArray, burnFuelPercentsValue: DoubleArray, heatCombustionValue: Double, dryFuelHeatCombustionValue: Double, burnFuelHeatCombustionValue: Double) {
            workDryCoeff?.text = "Коефіцієнт переходу від робочої до сухої маси становить: ${round(workDryCoeffValue)[0]}"
            workBurnCoeff?.text = "Коефіцієнт переходу від робочої до горючої маси становить: ${round(workBurnCoeffValue)[0]}"

            dryFuelPercents?.text = """Відсотковий склад сухої маси палива: вуглецю ${dryFuelPercentsValue[0]}%, водню ${dryFuelPercentsValue[1]}%, сірки ${dryFuelPercentsValue[2]}% азоту ${dryFuelPercentsValue[3]}%, кисню ${dryFuelPercentsValue[4]}%, золи ${dryFuelPercentsValue[5]}% і помилка ${dryFuelPercentsValue[6]}."""
            burnFuelPercents?.text = """Відсотковий склад горючої маси палива: вуглецю ${burnFuelPercentsValue[0]}%, водню ${burnFuelPercentsValue[1]}%, сірки ${burnFuelPercentsValue[2]}% азоту ${burnFuelPercentsValue[3]}%, кисню ${burnFuelPercentsValue[4]}%% і помилка ${burnFuelPercentsValue[5]}."""

            heatCombustion?.text = "Нижча теплота згоряння для робочої маси за заданим складом компонентів палива становить: ${heatCombustionValue} кДж/кг."
            dryFuelHeatCombustion?.text = "Нижча теплота згоряння для сухої маси за заданим складом компонентів палива становить: ${dryFuelHeatCombustionValue} кДж/кг."
            burnFuelHeatCombustion?.text = "Нижча теплота згоряння для горючої маси за заданим складом компонентів палива становить: ${burnFuelHeatCombustionValue} кДж/кг."
        }

        fun doCalculations(h1Value: Double, c1Value: Double, s1Value: Double, n1Value: Double, o1Value: Double, w1Value: Double, a1Value: Double) {
            val workDryCoeffValue = 100/(100- w1Value)
            val workBurnCoeffValue = 100/(100- w1Value - a1Value)

            val dryFuelPercentageValues = calculateDryFuelPercentage(workDryCoeffValue, h1Value, c1Value, s1Value, n1Value, o1Value, a1Value)
            val burnFuelPercentageValues = calculateBurnFuelPercentage(workBurnCoeffValue, h1Value, c1Value, s1Value, n1Value, o1Value)

            val hc = 339 * c1Value + 1030 * h1Value - 108.8 * ( o1Value - s1Value ) - 25 * w1Value
            val heatCombustionValue = round(hc)[0]

            val dhc = (heatCombustionValue + 0.025 * w1Value) * workDryCoeffValue
            val dryFuelHeatCombustionValue = round(dhc)[0]

            val bhc = (heatCombustionValue + 0.025 * w1Value) * workBurnCoeffValue
            val burnFuelHeatCombustionValue = round(bhc)[0]

            showResult(workDryCoeffValue, workBurnCoeffValue, dryFuelPercentageValues, burnFuelPercentageValues, heatCombustionValue, dryFuelHeatCombustionValue, burnFuelHeatCombustionValue)
        }

        calculateBtn.setOnClickListener{
            val h1Value = h1.text.toString().toDoubleOrNull()
            val c1Value = c1.text.toString().toDoubleOrNull()
            val s1Value = s1.text.toString().toDoubleOrNull()
            val n1Value = n1.text.toString().toDoubleOrNull()
            val o1Value = o1.text.toString().toDoubleOrNull()
            val w1Value = w1.text.toString().toDoubleOrNull()
            val a1Value = a1.text.toString().toDoubleOrNull()

            if (h1Value == null || c1Value == null || s1Value == null || n1Value == null || o1Value == null || w1Value == null || a1Value == null) {
                errorText?.text = "Усі поля повинні містити числа"
                return@setOnClickListener
            }

            val requirement = checkRequirements(h1Value, c1Value, s1Value, n1Value, o1Value, w1Value, a1Value)

            when(requirement){
                true -> doCalculations(h1Value, c1Value, s1Value, n1Value, o1Value, w1Value, a1Value)
                false -> {
                    errorText?.text = "Сума відсоткового складу палива повинна дорівнювати 100"
                    return@setOnClickListener
                }
            }
        }

        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}