package com.example.gymtracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Use SwitchCompat instead of Switch (fixes lint warning)
        val switchDarkMode     = findViewById<SwitchCompat>(R.id.switchDarkMode)
        val checkBoxMotivation = findViewById<CheckBox>(R.id.checkBoxMotivation)
        val btnSave            = findViewById<Button>(R.id.btnSaveSettings)
        val btnBack            = findViewById<Button>(R.id.btnBackHomeSettings)

        val prefs: SharedPreferences = getSharedPreferences("GymPrefs", MODE_PRIVATE)

        // Load saved preferences
        switchDarkMode.isChecked     = prefs.getBoolean("dark_mode", false)
        checkBoxMotivation.isChecked = prefs.getBoolean("motivation_enabled", true)

        // Save and apply settings
        btnSave.setOnClickListener {
            val isDarkMode   = switchDarkMode.isChecked
            val isMotivation = checkBoxMotivation.isChecked

            prefs.edit().apply {
                putBoolean("dark_mode", isDarkMode)
                putBoolean("motivation_enabled", isMotivation)
                apply()
            }

            // Apply dark/light mode immediately
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            Toast.makeText(this, "Settings Saved 💾", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener { finish() }
    }
}