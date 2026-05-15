package com.example.gymtracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail     = findViewById<EditText>(R.id.etLoginEmail)
        val etPassword  = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin    = findViewById<Button>(R.id.btnLogin)
        val btnGoSignUp = findViewById<Button>(R.id.btnGoSignUp)

        val prefs: SharedPreferences = getSharedPreferences("GymPrefs", MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val enteredEmail    = etEmail.text.toString().trim()
            val enteredPassword = etPassword.text.toString().trim()
            val savedEmail      = prefs.getString("user_email", "")
            val savedPassword   = prefs.getString("user_password", "")

            if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields ⚠️", Toast.LENGTH_SHORT).show()
            } else if (enteredEmail == savedEmail && enteredPassword == savedPassword) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Login Details ❌", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}