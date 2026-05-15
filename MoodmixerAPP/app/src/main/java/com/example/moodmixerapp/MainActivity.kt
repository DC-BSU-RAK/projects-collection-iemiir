package com.example.moodmixerapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isHappySelected    = false
    private var isAngrySelected    = false
    private var isSleepySelected   = false
    private var isStressedSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnHappy    = findViewById<Button>(R.id.btnHappy)
        val btnAngry    = findViewById<Button>(R.id.btnAngry)
        val btnSleepy   = findViewById<Button>(R.id.btnSleepy)
        val btnStressed = findViewById<Button>(R.id.btnStressed)
        val btnMix      = findViewById<Button>(R.id.btnMix)
        val btnInfo     = findViewById<Button>(R.id.btnInfo)
        val tvResult    = findViewById<TextView>(R.id.tvResult)

        // Toggle mood button: changes background, text color, and adds a checkmark
        fun toggle(button: Button, current: Boolean, label: String): Boolean {
            val next = !current
            if (next) {
                button.setBackgroundResource(R.drawable.btn_selected)
                button.setTextColor(Color.WHITE)
                button.text = "✅ $label"
            } else {
                button.setBackgroundResource(R.drawable.btn_default)
                button.setTextColor(Color.parseColor("#AAAAAA"))
                button.text = label
            }
            return next
        }

        btnHappy.setOnClickListener    { isHappySelected    = toggle(btnHappy,    isHappySelected,    "Happy 😊") }
        btnAngry.setOnClickListener    { isAngrySelected    = toggle(btnAngry,    isAngrySelected,    "Angry 😡") }
        btnSleepy.setOnClickListener   { isSleepySelected   = toggle(btnSleepy,   isSleepySelected,   "Sleepy 😴") }
        btnStressed.setOnClickListener { isStressedSelected = toggle(btnStressed, isStressedSelected, "Stressed 😵") }

        btnMix.setOnClickListener  { tvResult.text = getMoodResult() }
        btnInfo.setOnClickListener { showInfoDialog() }
    }

    private fun getMoodResult(): String {
        val h = isHappySelected
        val a = isAngrySelected
        val s = isSleepySelected
        val t = isStressedSelected

        // No mood selected
        if (!h && !a && !s && !t)
            return "Please select at least one mood to get started! 🎭"

        // All four moods
        if (h && a && s && t)
            return "You're happy, angry, sleepy, and stressed all at once? 😵\nYour emotions need a group meeting. Take a long nap and start over. 🛏️"

        // Three mood combos
        if (h && a && s)
            return "You're in a great mood but also irritated and can barely keep your eyes open. 😅\nMaybe get some rest before talking to people today."

        if (h && a && t)
            return "You're enjoying life but something is really bothering you and you have too much on your plate. 😤\nTake care of that one annoying thing first, then enjoy your day!"

        if (h && s && t)
            return "You're in a good mood despite being tired and overwhelmed. 😊\nThat's actually impressive — treat yourself to a break, you've earned it!"

        if (a && s && t)
            return "You're exhausted, overwhelmed, and frustrated all at once. 😞\nThis is your sign to stop everything, drink some water, and rest. Nothing is worth burning out over."

        // Two mood combos
        if (h && a)
            return "You're happy overall but something specific is annoying you right now. 😊😡\nIdentify what's bothering you, fix it, and get back to enjoying your day!"

        if (h && s)
            return "You're in a good mood but your body is running low on energy. 😊😴\nEven a 20 minute nap will make your good mood even better!"

        if (h && t)
            return "You're feeling positive but you have a lot going on right now. 😊📋\nMake a to-do list, tackle it one step at a time, and you'll be fine!"

        if (a && s)
            return "You're tired and frustrated — a dangerous combination. 😡😴\nDon't make any big decisions right now. Sleep first, everything looks better after rest."

        if (a && t)
            return "You're stressed and angry, which means something is really overwhelming you. 😡😵\nStep away from the situation, take 10 deep breaths, and come back with a clear head."

        if (s && t)
            return "You're mentally overloaded and physically drained. 😴😵\nYour brain is telling you to slow down. Rest is not laziness — it's necessary!"

        // Single moods
        if (h)
            return "You're in a great mood today! 😊\nSpread that energy around — call a friend, be kind to a stranger, and enjoy every moment!"

        if (a)
            return "Something has clearly upset you today. 😡\nTry to identify the root cause. Is it worth your energy? If not, let it go. If yes, address it calmly."

        if (s)
            return "Your body is asking for rest. 😴\nTry to squeeze in a nap or at least step away from screens for a bit. You'll feel much more human afterward!"

        if (t)
            return "You're feeling overwhelmed right now. 😵\nBreak your tasks into smaller steps, focus on just one thing at a time, and remember — you've handled hard days before!"

        return "Hmm, something went wrong. Try selecting a mood! 🎭"
    }

    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle("How to Use 🎭")
            .setMessage(
                "Welcome to Mood Mixer!\n\n" +
                        "1️⃣  Tap one or more mood buttons that match how you feel right now.\n\n" +
                        "2️⃣  Selected moods will turn purple with a ✅.\n\n" +
                        "3️⃣  Press Mix Mood ✨ to get a personalised result based on your moods.\n\n" +
                        "4️⃣  Tap a selected mood again to deselect it.\n\n" +
                        "You can mix up to 4 moods together for a unique result! 😊"
            )
            .setPositiveButton("Got it! 👍") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}