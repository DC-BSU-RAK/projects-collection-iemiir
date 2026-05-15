package com.example.gymtracker

import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class WorkoutActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null
    private var timerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        // Find views
        val tvWorkoutTitle   = findViewById<TextView>(R.id.tvWorkoutTitle)
        val tvWorkoutContent = findViewById<TextView>(R.id.tvWorkoutContent)
        val tvMotivation     = findViewById<TextView>(R.id.tvWorkoutMotivation)
        val tvProgress       = findViewById<TextView>(R.id.tvProgress)
        val tvTimer          = findViewById<TextView>(R.id.tvTimer)
        val btnStartTimer    = findViewById<Button>(R.id.btnStartTimer)
        val btnSave          = findViewById<Button>(R.id.btnSaveWorkout)
        val btnBack          = findViewById<Button>(R.id.btnBackHomeWorkout)
        val etNotes          = findViewById<EditText>(R.id.etWorkoutNotes)
        val cb1              = findViewById<CheckBox>(R.id.cbExercise1)
        val cb2              = findViewById<CheckBox>(R.id.cbExercise2)
        val cb3              = findViewById<CheckBox>(R.id.cbExercise3)
        val cb4              = findViewById<CheckBox>(R.id.cbExercise4)
        val cb5              = findViewById<CheckBox>(R.id.cbExercise5)

        val prefs: SharedPreferences = getSharedPreferences("GymPrefs", MODE_PRIVATE)
        val motivationEnabled = prefs.getBoolean("motivation_enabled", true)

        // Get workout type and plan from intent
        val workoutType = intent.getStringExtra("workout_type") ?: "Push"
        val workoutPlan = intent.getIntExtra("workout_plan", 1)

        val planLabel = when (workoutPlan) { 1 -> "Plan A — Beginner" 2 -> "Plan B — Intermediate" else -> "Plan C — Advanced" }

        // ─────────────────────────────────────────────
        // PUSH DAY PLANS
        // ─────────────────────────────────────────────
        val pushPlanA = listOf(
            "Bench Press — 3 x 10",
            "Shoulder Press — 3 x 10",
            "Incline Dumbbell Press — 3 x 10",
            "Tricep Pushdown — 3 x 12",
            "Push Ups — 2 sets to failure"
        )
        val pushContentA = "💪 PUSH DAY — PLAN A (Beginner)\n─────────────────────\n\n🏋️ Bench Press\n   3 sets x 10 reps\n\n🏋️ Shoulder Press\n   3 sets x 10 reps\n\n🏋️ Incline Dumbbell Press\n   3 sets x 10 reps\n\n🏋️ Tricep Pushdown\n   3 sets x 12 reps\n\n🏋️ Push Ups\n   2 sets to failure\n\n─────────────────────\nRest 90 seconds between sets."

        val pushPlanB = listOf(
            "Bench Press — 4 x 8",
            "Arnold Press — 3 x 12",
            "Cable Fly — 3 x 12",
            "Skull Crushers — 3 x 10",
            "Dips — 3 x 10"
        )
        val pushContentB = "💪 PUSH DAY — PLAN B (Intermediate)\n─────────────────────\n\n🏋️ Bench Press\n   4 sets x 8 reps\n\n🏋️ Arnold Press\n   3 sets x 12 reps\n\n🏋️ Cable Fly\n   3 sets x 12 reps\n\n🏋️ Skull Crushers\n   3 sets x 10 reps\n\n🏋️ Dips\n   3 sets x 10 reps\n\n─────────────────────\nRest 75 seconds between sets."

        val pushPlanC = listOf(
            "Incline Bench Press — 5 x 5",
            "Overhead Press — 4 x 6",
            "Weighted Dips — 4 x 8",
            "Lateral Raises — 4 x 15",
            "Tricep Overhead Extension — 3 x 12"
        )
        val pushContentC = "💪 PUSH DAY — PLAN C (Advanced)\n─────────────────────\n\n🏋️ Incline Bench Press\n   5 sets x 5 reps\n\n🏋️ Overhead Press\n   4 sets x 6 reps\n\n🏋️ Weighted Dips\n   4 sets x 8 reps\n\n🏋️ Lateral Raises\n   4 sets x 15 reps\n\n🏋️ Tricep Overhead Extension\n   3 sets x 12 reps\n\n─────────────────────\nRest 60 seconds between sets."

        // ─────────────────────────────────────────────
        // PULL DAY PLANS
        // ─────────────────────────────────────────────
        val pullPlanA = listOf(
            "Lat Pulldown — 3 x 10",
            "Seated Cable Row — 3 x 10",
            "Dumbbell Row — 3 x 12",
            "Dumbbell Curl — 3 x 12",
            "Hammer Curl — 2 x 15"
        )
        val pullContentA = "💪 PULL DAY — PLAN A (Beginner)\n─────────────────────\n\n🏋️ Lat Pulldown\n   3 sets x 10 reps\n\n🏋️ Seated Cable Row\n   3 sets x 10 reps\n\n🏋️ Dumbbell Row\n   3 sets x 12 reps\n\n🏋️ Dumbbell Curl\n   3 sets x 12 reps\n\n🏋️ Hammer Curl\n   2 sets x 15 reps\n\n─────────────────────\nRest 90 seconds between sets."

        val pullPlanB = listOf(
            "Barbell Row — 4 x 8",
            "Pull Ups — 3 x 8",
            "Face Pulls — 3 x 15",
            "Incline Dumbbell Curl — 3 x 12",
            "Reverse Curl — 3 x 12"
        )
        val pullContentB = "💪 PULL DAY — PLAN B (Intermediate)\n─────────────────────\n\n🏋️ Barbell Row\n   4 sets x 8 reps\n\n🏋️ Pull Ups\n   3 sets x 8 reps\n\n🏋️ Face Pulls\n   3 sets x 15 reps\n\n🏋️ Incline Dumbbell Curl\n   3 sets x 12 reps\n\n🏋️ Reverse Curl\n   3 sets x 12 reps\n\n─────────────────────\nRest 75 seconds between sets."

        val pullPlanC = listOf(
            "Weighted Pull Ups — 5 x 5",
            "Pendlay Row — 4 x 6",
            "Cable Row — 4 x 10",
            "Spider Curl — 4 x 10",
            "Shrugs — 4 x 15"
        )
        val pullContentC = "💪 PULL DAY — PLAN C (Advanced)\n─────────────────────\n\n🏋️ Weighted Pull Ups\n   5 sets x 5 reps\n\n🏋️ Pendlay Row\n   4 sets x 6 reps\n\n🏋️ Cable Row\n   4 sets x 10 reps\n\n🏋️ Spider Curl\n   4 sets x 10 reps\n\n🏋️ Shrugs\n   4 sets x 15 reps\n\n─────────────────────\nRest 60 seconds between sets."

        // ─────────────────────────────────────────────
        // LEG DAY PLANS
        // ─────────────────────────────────────────────
        val legPlanA = listOf(
            "Squats — 3 x 10",
            "Leg Press — 3 x 12",
            "Leg Extension — 3 x 12",
            "Leg Curl — 3 x 12",
            "Calf Raises — 3 x 20"
        )
        val legContentA = "💪 LEG DAY — PLAN A (Beginner)\n─────────────────────\n\n🏋️ Squats\n   3 sets x 10 reps\n\n🏋️ Leg Press\n   3 sets x 12 reps\n\n🏋️ Leg Extension\n   3 sets x 12 reps\n\n🏋️ Leg Curl\n   3 sets x 12 reps\n\n🏋️ Calf Raises\n   3 sets x 20 reps\n\n─────────────────────\nRest 90 seconds between sets."

        val legPlanB = listOf(
            "Barbell Squat — 4 x 8",
            "Romanian Deadlift — 3 x 10",
            "Walking Lunges — 3 x 12",
            "Leg Curl — 3 x 12",
            "Seated Calf Raises — 4 x 20"
        )
        val legContentB = "💪 LEG DAY — PLAN B (Intermediate)\n─────────────────────\n\n🏋️ Barbell Squat\n   4 sets x 8 reps\n\n🏋️ Romanian Deadlift\n   3 sets x 10 reps\n\n🏋️ Walking Lunges\n   3 sets x 12 reps\n\n🏋️ Leg Curl\n   3 sets x 12 reps\n\n🏋️ Seated Calf Raises\n   4 sets x 20 reps\n\n─────────────────────\nRest 75 seconds between sets."

        val legPlanC = listOf(
            "Front Squat — 5 x 5",
            "Hack Squat — 4 x 8",
            "Bulgarian Split Squat — 4 x 8",
            "Nordic Curl — 3 x 8",
            "Standing Calf Raises — 5 x 20"
        )
        val legContentC = "💪 LEG DAY — PLAN C (Advanced)\n─────────────────────\n\n🏋️ Front Squat\n   5 sets x 5 reps\n\n🏋️ Hack Squat\n   4 sets x 8 reps\n\n🏋️ Bulgarian Split Squat\n   4 sets x 8 reps\n\n🏋️ Nordic Curl\n   3 sets x 8 reps\n\n🏋️ Standing Calf Raises\n   5 sets x 20 reps\n\n─────────────────────\nRest 60 seconds between sets."

        // ─────────────────────────────────────────────
        // Pick the right plan and set content
        // ─────────────────────────────────────────────
        val exercises: List<String>
        val fullContent: String
        val emoji: String

        when (workoutType) {
            "Push" -> {
                emoji = "🔥"
                tvWorkoutTitle.text = "Push Day 🔥 — $planLabel"
                when (workoutPlan) {
                    1 -> { exercises = pushPlanA; fullContent = pushContentA }
                    2 -> { exercises = pushPlanB; fullContent = pushContentB }
                    else -> { exercises = pushPlanC; fullContent = pushContentC }
                }
                if (motivationEnabled) tvMotivation.text = "Time to build strength! Push through every rep 💪🔥"
            }
            "Pull" -> {
                emoji = "💪"
                tvWorkoutTitle.text = "Pull Day 💪 — $planLabel"
                when (workoutPlan) {
                    1 -> { exercises = pullPlanA; fullContent = pullContentA }
                    2 -> { exercises = pullPlanB; fullContent = pullContentB }
                    else -> { exercises = pullPlanC; fullContent = pullContentC }
                }
                if (motivationEnabled) tvMotivation.text = "Pull harder, grow bigger! You've got this 💪"
            }
            else -> {
                emoji = "🦵"
                tvWorkoutTitle.text = "Leg Day 🦵 — $planLabel"
                when (workoutPlan) {
                    1 -> { exercises = legPlanA; fullContent = legContentA }
                    2 -> { exercises = legPlanB; fullContent = legContentB }
                    else -> { exercises = legPlanC; fullContent = legContentC }
                }
                if (motivationEnabled) tvMotivation.text = "Never skip leg day! Beast mode activated 🦵🔥"
            }
        }

        // Set workout text
        tvWorkoutContent.text = fullContent

        // Set checkbox labels
        val checkboxes = listOf(cb1, cb2, cb3, cb4, cb5)
        checkboxes.forEachIndexed { index, checkBox ->
            checkBox.text = exercises[index]
            checkBox.setTextColor(android.graphics.Color.WHITE)
        }

        // Progress tracker
        val updateProgress = {
            val done = checkboxes.count { it.isChecked }
            tvProgress.text = "Progress: $done / 5 exercises done ✅"
        }
        checkboxes.forEach { it.setOnCheckedChangeListener { _, _ -> updateProgress() } }
        updateProgress()

        // 60 second rest timer
        btnStartTimer.setOnClickListener {
            if (timerRunning) {
                countDownTimer?.cancel()
                timerRunning = false
                btnStartTimer.text = "Start Rest Timer ⏱️"
                tvTimer.text = "60"
            } else {
                timerRunning = true
                btnStartTimer.text = "Cancel Timer ❌"
                countDownTimer = object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        tvTimer.text = (millisUntilFinished / 1000).toString()
                    }
                    override fun onFinish() {
                        tvTimer.text = "GO! 💪"
                        btnStartTimer.text = "Start Rest Timer ⏱️"
                        timerRunning = false
                        Toast.makeText(this@WorkoutActivity, "Rest over! Get back to it 💪", Toast.LENGTH_SHORT).show()
                    }
                }.start()
            }
        }

        // Save workout
        btnSave.setOnClickListener {
            val notes = etNotes.text.toString().trim()
            val saveKey = "${workoutType}_Plan${workoutPlan}"
            val editor = prefs.edit()
            editor.putString("saved_content_$saveKey", fullContent)
            editor.putString("saved_notes_$saveKey", notes.ifEmpty { "No notes added." })

            // Add to saved list if not already there
            val existing = prefs.getString("saved_workouts", "") ?: ""
            if (!existing.contains(saveKey)) {
                val updated = if (existing.isEmpty()) saveKey else "$existing,$saveKey"
                editor.putString("saved_workouts", updated)
            }
            editor.apply()
            Toast.makeText(this, "Workout Saved 💪", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}