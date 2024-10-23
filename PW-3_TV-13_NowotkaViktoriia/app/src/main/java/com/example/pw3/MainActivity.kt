package com.example.pw3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.exp
import kotlin.math.PI
import kotlin.math.sqrt
import com.example.pw3.round as round

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Pc_input: EditText = findViewById(R.id.Pc)
        val sigma_input: EditText = findViewById(R.id.sigma)
        val B_input: EditText = findViewById(R.id.B)

        val error_text1: TextView = findViewById(R.id.error_text1)
        val error_text2: TextView = findViewById(R.id.error_text2)
        val error_text3: TextView = findViewById(R.id.error_text3)

        val doBtn: Button = findViewById(R.id.do_calculations)
        val resetBtn: Button = findViewById(R.id.reset)

        val profit: TextView = findViewById(R.id.profit)

        fun wCalculate(p: Double, sigmaWValue: Double, type: String): Double {
            return when (type) {
                "with" -> p * 24 * sigmaWValue
                "without" -> p * 24 * (1 - sigmaWValue)
                else -> 0.0
            }
        }

        fun profFineCalculate(w: Double, b: Double): Double {
            return w*b
        }

        fun trapezoidalRule(f: (Double) -> Double, a: Double, b: Double, n: Int): Double {
            val h = (b - a) / n    // Width of each trapezoid
            var sum = 0.5 * (f(a) + f(b)) // Start with half the endpoints
            for (i in 1 until n) {
                val x = a + i * h
                sum += f(x)
            }
            return h * sum
        }

        fun showResult(profitValue: Double) {
            profit.text = "Прибуток становитиме ${round(profitValue)[0]} грн"
        }

        fun calculateP(PcValue: Double, sigmaValue: Double, BValue: Double): Double {
            val (x, y) = Pair(PcValue * 0.95,PcValue * 1.05)

            val pdFunction = { p: Double -> exp(-((p - PcValue).pow(2))/(2*sigmaValue.pow(2))) / (sigmaValue * sqrt(2*PI))}
            val sigmaWValue = trapezoidalRule(pdFunction, x, y, 100)

            val w1 = wCalculate(PcValue, sigmaWValue, "with")
            val prof1 = profFineCalculate(w1, BValue)
            val w2 = wCalculate(PcValue, sigmaWValue, "without")
            val fine1 = profFineCalculate(w2, BValue)

            val pdFunctionUpdated = { p: Double -> exp(-((p - PcValue).pow(2))/(0.25.pow(2)*2)) / (0.25 * sqrt(2*PI))}
            val sigmaWValueUpdated = trapezoidalRule(pdFunctionUpdated, x, y, 100)

            val w3 = wCalculate(PcValue, sigmaWValueUpdated, "with")
            val prof2 = profFineCalculate(w3, BValue)
            val w4 = wCalculate(PcValue, sigmaWValueUpdated, "without")
            val fine2 = profFineCalculate(w4, BValue)


            val profitValue = prof2 - fine2
            return profitValue
        }

        doBtn.setOnClickListener {
            val PcValue = Pc_input.text.toString().toDoubleOrNull()
            val sigmaValue= sigma_input.text.toString().toDoubleOrNull()
            val BValue= B_input.text.toString().toDoubleOrNull()

            if (PcValue == null) {
                error_text1.text = "Середньодобова потужність повинна містити число"
                return@setOnClickListener
            }
            if (sigmaValue == null) {
                error_text2.text = "Середньоквадратичне відхилення повинно містити число"
                return@setOnClickListener
            }
            if (BValue == null) {
                error_text3.text = "Вартість повинна містити число"
                return@setOnClickListener
            }

            error_text1.text = ' '.toString()
            error_text2.text = ' '.toString()
            error_text3.text = ' '.toString()

            val profitValue = calculateP(PcValue, sigmaValue, BValue)

            showResult(profitValue)
        }

        resetBtn.setOnClickListener{
            error_text1.text = ' '.toString()
            error_text2.text = ' '.toString()
            error_text3.text = ' '.toString()
            Pc_input.text.clear()
            sigma_input.text.clear()
            B_input.text.clear()
        }
    }
}