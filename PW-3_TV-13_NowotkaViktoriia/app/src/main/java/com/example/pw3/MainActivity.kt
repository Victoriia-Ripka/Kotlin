package com.example.pw3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import kotlin.math.pow
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

        fun showResult(profitValue: Double) {
            profit.text = "Прибуток становитиме ${round(profitValue)[0]} грн"
        }

        fun calculateP(PcValue: Double, sigmaValue: Double, BValue: Double): Double {
            val profitValue =  0.0
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