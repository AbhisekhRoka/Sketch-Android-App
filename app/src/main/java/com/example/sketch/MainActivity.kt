package com.example.sketch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val doodleView: DoodleView = findViewById(R.id.doodleView)
        val colorWheel: ColorWheelView = findViewById(R.id.colorWheel)
        val colorButton: Button = findViewById(R.id.color)

        val sizeSpinner: Spinner = findViewById(R.id.sizeSpinner)

        val currentPage: TextView = findViewById(R.id.page)
        val nextButton: Button = findViewById(R.id.next)
        val previousButton: Button = findViewById(R.id.previous)
        val brushButton: Button = findViewById(R.id.brush)

        val brushSizes = arrayOf("1", "5", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "150", "200")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, brushSizes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizeSpinner.adapter = adapter

        sizeSpinner.setSelection(3)

        sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedSize = brushSizes[position].toFloat()
                doodleView.setNewBrushSize(selectedSize)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        colorButton.setOnClickListener {
            if(colorWheel.visibility == View.VISIBLE)
                colorWheel.visibility = View.INVISIBLE
            else
                colorWheel.visibility = View.VISIBLE
        }

        brushButton.setOnClickListener {
            doodleView.changeStrokeCap()
            brushButton.setText(doodleView.currentStrokeCap.toString())
            doodleView.changeColor("#000000")
            colorButton.setBackgroundColor(Color.BLACK)
        }

        nextButton.setOnClickListener {
            var page = currentPage.text.toString().toInt()
            var newPage = page + 1
            doodleView.saveAndGet(newPage)
            currentPage.setText(newPage.toString())
        }
        previousButton.setOnClickListener {
            var page = currentPage.text.toString().toInt()
            if (page == 0){
                return@setOnClickListener
            }
            var newPage = page - 1
            doodleView.saveAndGet(newPage)
            currentPage.setText(newPage.toString())
        }

        colorWheel.colorSelectedListener = { color ->
            doodleView.changeColor("#${Integer.toHexString(color).uppercase()}")
            colorButton.setBackgroundColor(color)
        }
    }
}