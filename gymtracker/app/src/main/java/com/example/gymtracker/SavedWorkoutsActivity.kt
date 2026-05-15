package com.example.gymtracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SavedWorkoutsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        val tvSaved  = findViewById<TextView>(R.id.tvSavedList)
        val btnBack  = findViewById<Button>(R.id.btnBackHomeSaved)
        val btnClear = findViewById<Button>(R.id.btnClearSaved)

        val prefs: SharedPreferences = getSharedPreferences("GymPrefs", MODE_PRIVATE)

        // Load and display saved workouts
        fun loadSaved() {
            val savedWorkouts = prefs.getString("saved_workouts", "") ?: ""
            if (savedWorkouts.isEmpty()) {
                tvSaved.text = "No workouts saved yet.\nGo save one! 💪"
            } else {
                val workoutList = savedWorkouts.split(",")
                val display = StringBuilder()
                workoutList.forEach { key ->
                    val trimmed = key.trim()
                    val content = prefs.getString("saved_content_$trimmed", "No details saved.")
                    val notes   = prefs.getString("saved_notes_$trimmed", "No notes.")

                    // Friendly label from key e.g. "Push_Plan1" → "🔥 Push Day — Plan A"
                    val label = when {
                        trimmed.startsWith("Push") -> "🔥 PUSH DAY"
                        trimmed.startsWith("Pull") -> "💪 PULL DAY"
                        trimmed.startsWith("Leg")  -> "🦵 LEG DAY"
                        else -> trimmed
                    }
                    val planLabel = when {
                        trimmed.endsWith("1") -> "Plan A — Beginner"
                        trimmed.endsWith("2") -> "Plan B — Intermediate"
                        trimmed.endsWith("3") -> "Plan C — Advanced"
                        else -> ""
                    }

                    display.append("$label — $planLabel\n")
                    display.append("─────────────────────\n")
                    display.append("$content\n\n")
                    display.append("📝 Notes: $notes\n")
                    display.append("\n══════════════════════\n\n")
                }
                tvSaved.text = display.toString()
            }
        }

        loadSaved()

        // Clear all saved workouts with confirmation
        btnClear.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Clear Saved Workouts 🗑️")
                .setMessage("Are you sure you want to delete all saved workouts?")
                .setPositiveButton("Yes, Clear") { _, _ ->
                    val editor = prefs.edit()
                    editor.remove("saved_workouts")
                    // Clear all plan keys
                    listOf("Push_Plan1","Push_Plan2","Push_Plan3",
                        "Pull_Plan1","Pull_Plan2","Pull_Plan3",
                        "Leg_Plan1","Leg_Plan2","Leg_Plan3").forEach { key ->
                        editor.remove("saved_content_$key")
                        editor.remove("saved_notes_$key")
                    }
                    editor.apply()
                    Toast.makeText(this, "All workouts cleared 🗑️", Toast.LENGTH_SHORT).show()
                    loadSaved()
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        btnBack.setOnClickListener { finish() }
    }
}