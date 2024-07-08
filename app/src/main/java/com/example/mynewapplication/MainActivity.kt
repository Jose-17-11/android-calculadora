package com.example.mynewapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        // Referenciar los componentes
        val textView = findViewById<TextView>(R.id.textView)
        val button0 = findViewById<Button>(R.id.buttonCero)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val buttonSuma = findViewById<Button>(R.id.buttonSuma)
        val buttonResta = findViewById<Button>(R.id.buttonRestas)
        val buttonMultiplicacion = findViewById<Button>(R.id.buttonMultiplicacion)
        val buttonResultado = findViewById<Button>(R.id.buttonResultado)
        val buttonDivision = findViewById<Button>(R.id.buttonDivision)
        val buttonAc = findViewById<Button>(R.id.buttonAc)
        var i = true

        // Manejar los clics de los botones
        val buttons = listOf(button0, button1, button2, button3, button4,
            button5, button6, button7, button8,
            button9, buttonSuma, buttonResta, buttonMultiplicacion,
            buttonResultado, buttonDivision, buttonAc)
        buttons.forEach { button ->
            button.setOnClickListener {
                try {
                    if (button.text.toString().matches(Regex("\\d"))) {
                        // Si es un número, agregarlo al TextView
                        textView.text = "${textView.text}${button.text}"
                    } else if (esOperador(button.text.toString()) && i == true) {
                        textView.text = "${textView.text}${button.text}"
                        i = false
                    } else if (button.text.toString() == "=") {
                        // Verifica que el TextView contenga una expresión válida
                        if (textView.text.contains(Regex("[+\\-*/]"))) {
                            val (var1, operador, var2) = parseExpression(textView.text.toString())
                            val resultado = realizarOperacion(var1, operador, var2)
                            textView.text = resultado.toString()
                        } else {
                            textView.text = "Expresión inválida"
                        }
                    } else if(button.text.toString() == "AC") {
                        textView.text = ""
                        i = true
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error: ${e.message}")
                    Log.e("MainActivity", "Error stack trace:", e)
                    textView.text = e.message
                }
            }
        }
    }

    fun esOperador(caracter: String): Boolean {
        return caracter == "+" || caracter == "-" || caracter == "*" || caracter == "/"
    }
    fun esOperadorChar(caracter: Char): Boolean {
        return caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/'
    }
    fun parseExpression(cadena: String): Triple<Double, Char, Double> {
        var var1 = ""
        var operador: Char? = null
        var var2 = ""
        var pass = true
        for (i in cadena.indices) {
            val char = cadena[i]

            if (char.isDigit() && pass) {
                var1 += char
            } else if (esOperadorChar(char)) {
                operador = char
                pass = false
            } else if (!pass) {
                var2 += char
            }else if (char=='=') {
                break;
            }
        }

        if (operador == null || var1.isEmpty() || var2.isEmpty()) {
            throw IllegalArgumentException("Expresión inválida: var1=$var1, operador=$operador, var2=$var2")
        }

        return Triple(var1.toDouble(), operador, var2.toDouble())
    }

    fun realizarOperacion(var1: Double, operador: Char, var2: Double): Double {
        return when (operador) {
            '+' -> var1 + var2
            '-' -> var1 - var2
            '*' -> var1 * var2
            '/' -> if (var2 != 0.0) var1 / var2 else throw IllegalArgumentException("División por cero")
            else -> throw IllegalArgumentException("Operador no soportado: $operador")
        }
    }
}
