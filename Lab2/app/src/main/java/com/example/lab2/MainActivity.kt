package com.example.lab2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var counter = 0
    private var step = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val output = findViewById<TextView>(R.id.textViewOutput)
        val addButton = findViewById<Button>(R.id.buttonAdd)
        val subtractButton = findViewById<Button>(R.id.buttonSubtract)
        val resetButton = findViewById<Button>(R.id.buttonReset)
        val stepButton = findViewById<Button>(R.id.buttonStep)

        addButton.setOnClickListener {
            counter += step
            output.text = counter.toString()
        }

        subtractButton.setOnClickListener {
            counter -= step
            output.text = counter.toString()
        }

        stepButton.setOnClickListener {
            step = 2
        }

        resetButton.setOnClickListener {
            counter = 0
            step = 1
            output.text = counter.toString()
        }
    }
}