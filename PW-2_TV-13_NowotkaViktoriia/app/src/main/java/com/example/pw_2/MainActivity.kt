package com.example.pw_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import com.example.pw_2.round as round

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val errorText: TextView = findViewById(R.id.error_text)
        val doBtn: Button = findViewById(R.id.do_calculations)
        val resetBtn: Button = findViewById(R.id.reset)

        val coal: EditText = findViewById(R.id.coal)
        val fuel: EditText = findViewById(R.id.fuel)
        val gas: EditText = findViewById(R.id.gas)

        val kCoal: TextView = findViewById(R.id.k_coal)
        val eCoal: TextView = findViewById(R.id.e_coal)
        val kFuel: TextView = findViewById(R.id.k_fuel)
        val eFuel: TextView = findViewById(R.id.e_fuel)
        val kGas: TextView = findViewById(R.id.k_gas)
        val eGas: TextView = findViewById(R.id.e_gas)

        val qCoal = 20.47
        val gVynCoal = 1.5
        val aVynCoal = 0.8
        val aRCoal = 25.2
        val nZuCoal = 0.985
        val kHSCoal = 0.0

        val qFuel = 40.40
        val gVynFuel = 0.0
        val aVynFuel = 1.0
        val aRFuel = 0.15
        val nZuFuel = 0.985
        val kHSFuel = 0.0

        val qGas = 20.47
        val gVynGas = 1.5
        val aVynGas = 0.8
        val aRGas = 25.2
        val nZuGas = 0.985
        val kHSGas = 0.0

        fun calculateK(q: Double, aVyn: Double, aR: Double, gVyn: Double, nZu: Double, kHS: Double): Double {
            return (10.0.pow(6) * aVyn * aR) / ( q * (100 - gVyn)) * (1 - nZu) + kHS
        }

        fun calculateE(k: Double, b: Double, q: Double): Double {
            return 10.0.pow(-6) * k * b * q
        }

        fun showResult(kCoalValue: Double, eCoalValue: Double, kFuelValue: Double, eFuelValue: Double, kGasValue: Double, eGasValue: Double) {
            kCoal.text = "Показник емісії твердих частинок при спалюванні вугілля становитиме: ${round(kCoalValue)[0]} г/ГДж;"
            eCoal.text = "Валовий викид при спалюванні вугілля становитиме: ${round(eCoalValue)[0]} т."

            kFuel.text = "Показник емісії твердих частинок при спалюванні мазуту становитиме: ${round(kFuelValue)[0]} г/ГДж;"
            eFuel.text = "Валовий викид при спалюванні мазуту становитиме: ${round(eFuelValue)[0]} т."

            kGas.text = "Показник емісії твердих частинок при спалюванні природного газу становитиме: ${kGasValue} г/ГДж;"
            eGas.text = "Валовий викид при спалюванні природного газу становитиме: ${eGasValue} т."
        }

        doBtn.setOnClickListener {
            val bCoalValue = coal.text.toString().toDoubleOrNull()
            val bFuelValue= fuel.text.toString().toDoubleOrNull()
            val bGasValue= gas.text.toString().toDoubleOrNull()

            if (bCoalValue == null || bFuelValue == null || bGasValue == null) {
                errorText.text = "Усі поля повинні містити числа"
                return@setOnClickListener
            }

            errorText.text = ' '.toString()

            val kCoalValue = calculateK(qCoal, aVynCoal, aRCoal, gVynCoal, nZuCoal, kHSCoal)
            val eCoalValue = calculateE(kCoalValue, bCoalValue, qCoal)

            val kFuelValue = calculateK(qFuel, aVynFuel, aRFuel, gVynFuel, nZuFuel, kHSFuel)
            val eFuelValue = calculateE(kFuelValue, bFuelValue, qFuel)

            val kGasValue = 0.0
            val eGasValue = calculateE(kGasValue, bGasValue, qGas)

            showResult(kCoalValue, eCoalValue, kFuelValue, eFuelValue, kGasValue, eGasValue)
        }

        resetBtn.setOnClickListener{
            errorText.text = ' '.toString()
            coal.text.clear()
            fuel.text.clear()
            gas.text.clear()
            kCoal.text = ' '.toString()
            eCoal.text = ' '.toString()
            kFuel.text = ' '.toString()
            eFuel.text = ' '.toString()
            kGas.text = ' '.toString()
            eGas.text = ' '.toString()
        }
    }
}

