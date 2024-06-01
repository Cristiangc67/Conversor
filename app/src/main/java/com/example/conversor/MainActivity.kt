package com.example.conversor

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    lateinit var selectedRadioButton: RadioButton

    lateinit var decimal: EditText
    lateinit var redText: TextView
    lateinit var radios: RadioGroup
    lateinit var binary: RadioButton
    lateinit var complement: RadioButton
    lateinit var representation: TextView
    lateinit var result8Bit: TextView
    lateinit var calculate: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initComponents()

        initListeners()
    }

    private fun initComponents() {
        decimal = findViewById(R.id.decimal)
        redText = findViewById(R.id.redText)
        radios = findViewById(R.id.radios)
        binary = findViewById(R.id.binary)
        complement = findViewById(R.id.complement)
        representation = findViewById(R.id.representation)
        result8Bit = findViewById(R.id.result8Bit)
        calculate = findViewById(R.id.calculate)
        selectedRadioButton = complement
    }

    private fun initListeners() {
        calculate.setOnClickListener { if (decimal.text.toString() != "" && decimal.text.toString() !="-") calBinCom() }
        radios.setOnCheckedChangeListener { _, checkedId ->
            selectedRadioButton = findViewById(checkedId)
            if(selectedRadioButton == binary){
                representation.text="BINARIO"
                result8Bit.text="-"

            }else{
                representation.text = "COMPLEMENTO"
                result8Bit.text="-"
            }
        }
    }

    private fun calBinCom() {

        val decimalInt = decimal.text.toString()
        var number: Int = decimalInt.toInt()
        if (selectedRadioButton == binary) {
            if (number <= 127 && number >= -128) {
                redText.visibility = View.INVISIBLE
                if (number < 0) {
                    val absolut: Int = -number
                    result8Bit.text = getComplement(absolut)
                } else {
                    result8Bit.text = binarySum(number.toString(2), "0")
                }
            }else{
                redText.visibility = View.VISIBLE
            }

        }
        if (selectedRadioButton == complement) {
            redText.visibility = View.INVISIBLE
            if (number <= 127 && number >= -128) {
                result8Bit.text = getComplement(number)
            } else {
                redText.visibility = View.VISIBLE
            }

        }
    }


    private fun binarySum(binaryString1: String, binaryString2: String): String {
        val num1: Int = binaryString1.toInt(2)
        val num2: Int = binaryString2.toInt(2)
        val sum: Int = num1 + num2
        return sum.toString(2).padStart(8, '0').takeLast(8)


    }

    private fun getComplement(number: Int): String {
        val binary = Integer.toBinaryString(number).padStart(8, '0')
        var invertedBinary = binary.map { if (it == '0') '1' else '0' }.joinToString("")
        var binaryFinal = binarySum(invertedBinary, "1")
        return binaryFinal
    }
}


