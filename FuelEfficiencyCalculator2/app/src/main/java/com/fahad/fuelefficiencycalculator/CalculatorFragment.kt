package com.fahad.fuelefficiencycalculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class CalculatorFragment : Fragment() {
    private lateinit var distanceInput: EditText
    private lateinit var fuelInput: EditText
    private lateinit var unitToggle: Switch
    private lateinit var resultText: TextView
    private lateinit var calculateButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        distanceInput = view.findViewById(R.id.input_distance)
        fuelInput = view.findViewById(R.id.input_fuel)
        unitToggle = view.findViewById(R.id.unit_toggle)
        resultText = view.findViewById(R.id.result_text)
        calculateButton = view.findViewById(R.id.btn_calculate)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        unitToggle.isChecked = prefs.getBoolean("useMetric", false)

        unitToggle.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("useMetric", isChecked).apply()
        }

        calculateButton.setOnClickListener {
            val distance = distanceInput.text.toString().toDoubleOrNull()
            val fuel = fuelInput.text.toString().toDoubleOrNull()

            if (distance == null || fuel == null || fuel == 0.0) {
                resultText.text = "Please enter valid values"
                return@setOnClickListener
            }

            val useMetric = unitToggle.isChecked
            val result = if (useMetric) {
                (fuel / distance) * 100 // L/100km
            } else {
                distance / fuel // MPG
            }

            val resultStr = if (useMetric) "Efficiency: %.2f L/100km".format(result)
            else "Efficiency: %.2f MPG".format(result)
            resultText.text = resultStr

            // Save to history
            val historyPrefs = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
            val oldHistory = historyPrefs.getString("entries", "") ?: ""
            val newHistory = "$resultStr\n$oldHistory"
            historyPrefs.edit().putString("entries", newHistory).apply()
        }

        return view
    }
}
