package com.example.pw_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonTask1: Button = findViewById(R.id.task1)
        val buttonTask2: Button = findViewById(R.id.task2)

        buttonTask1.setOnClickListener{
            val intent = Intent(this, FirstCalculatorActivity::class.java)
            startActivity(intent)
        }

        buttonTask2.setOnClickListener{
            val intent = Intent(this, SecondCalculatorActivity::class.java)
            startActivity(intent)
        }

    }
}