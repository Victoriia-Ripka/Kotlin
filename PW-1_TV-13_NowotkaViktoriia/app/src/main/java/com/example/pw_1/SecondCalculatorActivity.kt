package com.example.pw_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondCalculatorActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_calculator_activity)

//        val errorText: TextView? = findViewById(R.id.error_text1)
        val calculateBtn: Button = findViewById(R.id.do_task2)
        val backBtn: Button = findViewById(R.id.from_2_back)

        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}