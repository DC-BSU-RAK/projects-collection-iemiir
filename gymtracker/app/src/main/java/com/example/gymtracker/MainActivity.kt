package com.example.gymtracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply dark mode preference BEFORE setContentView
        val prefs: SharedPreferences = getSharedPreferences("GymPrefs", MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(R.layout.activity_main)

        // Find views
        val btnPushDay       = findViewById<Button>(R.id.btnPushDay)
        val btnPullDay       = findViewById<Button>(R.id.btnPullDay)
        val btnLegDay        = findViewById<Button>(R.id.btnLegDay)
        val btnSavedWorkouts = findViewById<Button>(R.id.btnSavedWorkouts)
        val btnSettings      = findViewById<Button>(R.id.btnSettings)
        val btnInfo          = findViewById<Button>(R.id.btnInfo)
        val btnProfile       = findViewById<Button>(R.id.btnProfile)
        val btnLogout        = findViewById<Button>(R.id.btnLogout)
        val tvDailyQuote     = findViewById<TextView>(R.id.tvDailyQuote)
        val tvWelcomeUser    = findViewById<TextView>(R.id.tvWelcomeUser)

        // Welcome user by name
        val userName = prefs.getString("user_name", "Athlete")
        tvWelcomeUser.text = "Welcome, $userName! 💪"

        // Random daily motivational quote
        val quotes = listOf(
            "\"Push yourself because no one else will.\" 🔥",
            "\"The body achieves what the mind believes.\" 💪",
            "\"Wake up. Work out. Be the best.\" ⚡",
            "\"Sweat is just fat crying.\" 😤",
            "\"No pain, no gain. Shut up and train.\" 🏋️",
            "\"Your only limit is you.\" 🚀",
            "\"Train insane or remain the same.\" 💥"
        )
        tvDailyQuote.text = quotes.random()

        // Push Day — opens workout selection
        btnPushDay.setOnClickListener {
            showWorkoutPlanDialog("Push")
        }

        btnPullDay.setOnClickListener {
            showWorkoutPlanDialog("Pull")
        }

        btnLegDay.setOnClickListener {
            showWorkoutPlanDialog("Leg")
        }

        btnSavedWorkouts.setOnClickListener {
            startActivity(Intent(this, SavedWorkoutsActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnInfo.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("ℹ️ About MIRR'S GYM")
                .setMessage("MIRR'S GYM helps beginners explore workout routines, save favorite workouts, and manage simple fitness preferences 💪")
                .setPositiveButton("Let's Go! 💪") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout 🚪")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    // Show a dialog to pick which workout plan (Plan A, B, or C)
    private fun showWorkoutPlanDialog(type: String) {
        val emoji = when (type) { "Push" -> "🔥" "Pull" -> "💪" else -> "🦵" }
        val plans = arrayOf("Plan A — Beginner", "Plan B — Intermediate", "Plan C — Advanced")

        AlertDialog.Builder(this)
            .setTitle("$emoji $type Day — Choose Plan")
            .setItems(plans) { _, which ->
                val plan = which + 1 // 1, 2, or 3
                val intent = Intent(this, WorkoutActivity::class.java)
                intent.putExtra("workout_type", type)
                intent.putExtra("workout_plan", plan)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}