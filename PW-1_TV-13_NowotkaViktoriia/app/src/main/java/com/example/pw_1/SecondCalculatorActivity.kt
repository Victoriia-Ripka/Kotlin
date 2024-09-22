package com.example.pw_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pw_1.round as round

class SecondCalculatorActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_calculator_activity)

        val h2: EditText = findViewById(R.id.h2)
        val c2: EditText = findViewById(R.id.c2)
        val o2: EditText = findViewById(R.id.o2)
        val s2: EditText = findViewById(R.id.s2)
        val q2: EditText = findViewById(R.id.q2)
        val v2: EditText = findViewById(R.id.v2)
        val w2: EditText = findViewById(R.id.w2)
        val a2: EditText = findViewById(R.id.a2)

        val calculateBtn: Button = findViewById(R.id.do_task2)
        val backBtn: Button = findViewById(R.id.from_2_back)
        val errorText: TextView? = findViewById(R.id.error_text2)

        val burnWorkCoeff: TextView? = findViewById(R.id.burnWorkCoeff2)
        val dryWorkCoeff: TextView? = findViewById(R.id.dryWorkCoeff2)
        val workFuelPercents: TextView? = findViewById(R.id.workFuelPercents)
        val heatCombustion: TextView? = findViewById(R.id.heatCombustion2)

        fun checkRequirements(h: Double, c: Double, o: Double, s: Double): Boolean{
            return (h + c + s + o) == 100.0
        }

        fun showResult(burnWorkCoeffValue: Double, dryWorkCoeffValue: Double, workFuelPercentsValue: DoubleArray, heatCombustionValue: Double) {
            burnWorkCoeff?.text = "Коефіцієнт переходу від горючої до робочої маси становить: ${round(burnWorkCoeffValue)[0]}"
            dryWorkCoeff?.text = "Коефіцієнт переходу від сухої до робочої маси становить: ${round(dryWorkCoeffValue)[0]}"
            workFuelPercents?.text = "Відсотковий склад робочої маси мазуту: вуглецю ${workFuelPercentsValue[0]}%, водню ${workFuelPercentsValue[1]}%, кисню ${workFuelPercentsValue[2]}%, сірки ${workFuelPercentsValue[3]}%  золи ${workFuelPercentsValue[4]}% і ванадію ${workFuelPercentsValue[5]} мг/кг."
            heatCombustion?.text = "Нижча теплота згоряння для робочої маси за заданим складом компонентів мазуту становить: ${heatCombustionValue} кДж/кг."
        }

        fun calculateWorkFuelPercentage(burnWorkCoeffValue: Double, dryWorkCoeffValue: Double, c2Value: Double, h2Value: Double, o2Value: Double, s2Value: Double, a2Value: Double, v2Value: Double): DoubleArray {
            val workCarbon = c2Value * burnWorkCoeffValue
            val workHydrogen = h2Value * burnWorkCoeffValue
            val workOxygen = o2Value * burnWorkCoeffValue
            val workSulfur = s2Value * burnWorkCoeffValue
            val workAch = a2Value * dryWorkCoeffValue
            val workVanadium = v2Value * dryWorkCoeffValue

            return round(workCarbon, workHydrogen, workOxygen, workSulfur, workAch, workVanadium)
        }

        fun doCalculations(h2Value: Double, c2Value: Double, s2Value: Double, o2Value: Double, w2Value: Double, a2Value: Double, q2Value: Double, v2Value: Double) {
            val whc = (100 - w2Value - a2Value) / 100
            val burnWorkCoeffValue = round(whc)[0]

            val whc2 = (100 - w2Value ) / 100
            val dryWorkCoeffValue  = round(whc2)[0]

            val hc = q2Value * burnWorkCoeffValue - 0.025 * w2Value
            val heatCombustionValue = round(hc)[0]

            val workFuelPercentageValue = calculateWorkFuelPercentage(burnWorkCoeffValue, dryWorkCoeffValue, c2Value, h2Value, o2Value, s2Value, a2Value, v2Value)

            showResult(burnWorkCoeffValue, dryWorkCoeffValue, workFuelPercentageValue , heatCombustionValue)
        }

        calculateBtn.setOnClickListener{
            val h2Value = h2.text.toString().toDoubleOrNull()
            val c2Value = c2.text.toString().toDoubleOrNull()
            val o2Value = o2.text.toString().toDoubleOrNull()
            val s2Value = s2.text.toString().toDoubleOrNull()
            val q2Value = q2.text.toString().toDoubleOrNull()
            val v2Value = v2.text.toString().toDoubleOrNull()
            val w2Value = w2.text.toString().toDoubleOrNull()
            val a2Value = a2.text.toString().toDoubleOrNull()

            if (h2Value == null || c2Value == null || s2Value == null || v2Value == null || o2Value == null || w2Value == null || a2Value == null || q2Value == null) {
                errorText?.text = "Усі поля повинні містити числа"
                return@setOnClickListener
            }

            val requirement = checkRequirements(h2Value, c2Value, o2Value, s2Value)

            when(requirement){
                true -> doCalculations(h2Value, c2Value, s2Value, o2Value, w2Value, a2Value, q2Value, v2Value)
                false -> {
                    errorText?.text = "Сума відсоткового складу палива (h + c + s + o) повинна == 100"
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