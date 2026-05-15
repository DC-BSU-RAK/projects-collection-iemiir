package com.example.gymtracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etName     = findViewById<EditText>(R.id.etSignupName)
        val etEmail    = findViewById<EditText>(R.id.etSignupEmail)
        val etPassword = findViewById<EditText>(R.id.etSignupPassword)
        val btnCreate  = findViewById<Button>(R.id.btnCreateAccount)
        val btnBack    = findViewById<Button>(R.id.btnBackToLogin)

        btnCreate.setOnClickListener {
            val name     = etName.text.toString().trim()
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields ⚠️", Toast.LENGTH_SHORT).show()
            } else {
                val prefs: SharedPreferences = getSharedPreferences("GymPrefs", MODE_PRIVATE)
                prefs.edit().apply {
                    putString("user_name", name)
                    putString("user_email", email)
                    putString("user_password", password)
                    apply()
                }
                Toast.makeText(this, "Account Created! Please Login 🎉", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnBack.setOnClickListener { finish() }
    }
}