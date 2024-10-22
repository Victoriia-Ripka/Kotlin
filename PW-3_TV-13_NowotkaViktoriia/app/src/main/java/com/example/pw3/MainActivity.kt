package com.example.pw3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pw3.R
import kotlin.math.pow

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


    }
}