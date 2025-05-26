package com.fahad.fuelefficiencycalculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val historyText = view.findViewById<TextView>(R.id.history_text)

        val prefs = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val entries = prefs.getString("entries", "No history available")

        historyText.text = entries

        return view
    }
}
