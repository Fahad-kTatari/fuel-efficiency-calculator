package com.fahad.fuelefficiencycalculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val metricSwitch = view.findViewById<Switch>(R.id.switch_metric)
        val clearButton = view.findViewById<Button>(R.id.btn_clear_history)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        metricSwitch.isChecked = prefs.getBoolean("useMetric", false)

        metricSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("useMetric", isChecked).apply()
            Toast.makeText(requireContext(), "Metric setting updated", Toast.LENGTH_SHORT).show()
        }

        clearButton.setOnClickListener {
            val historyPrefs = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
            historyPrefs.edit().clear().apply()
            Toast.makeText(requireContext(), "History cleared", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
