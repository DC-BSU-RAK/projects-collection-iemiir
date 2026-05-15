package com.example.gymtracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvProfileName    = findViewById<TextView>(R.id.tvProfileName)
        val tvProfileEmail   = findViewById<TextView>(R.id.tvProfileEmail)
        val tvProfileSaved   = findViewById<TextView>(R.id.tvProfileSaved)
        val btnBack          = findViewById<Button>(R.id.btnBackHomeProfile)

        // Load user data from SharedPreferences
        val prefs: SharedPreferences = getSharedPreferences("GymPrefs", MODE_PRIVATE)
        val name  = prefs.getString("user_name", "Unknown") ?: "Unknown"
        val email = prefs.getString("user_email", "Unknown") ?: "Unknown"
        val saved = prefs.getString("saved_workouts", "") ?: ""

        // Count saved workouts
        val savedCount = if (saved.isEmpty()) 0 else saved.split(",").size

        // Display profile info
        tvProfileName.text  = "👤 Name: $name"
        tvProfileEmail.text = "📧 Email: $email"
        tvProfileSaved.text = "⭐ Workouts Saved: $savedCount"

        btnBack.setOnClickListener { finish() }
    }
}