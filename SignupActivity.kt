package com.devora.community

import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        db = DatabaseHelper(this)

        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val confirm = findViewById<EditText>(R.id.confirm)
        val create = findViewById<Button>(R.id.create)

        create.setOnClickListener {

            val n = name.text.toString().trim()
            val e = email.text.toString().trim()
            val p = password.text.toString()
            val c = confirm.text.toString()

            if (n.isEmpty() || e.isEmpty() || p.isEmpty()) {
                Toast.makeText(
                    this,
                    "Fadlan dhammaan meelaha buuxi.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (p != c) {
                Toast.makeText(
                    this,
                    "Passwords-ku isku mid ma aha.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val values = ContentValues().apply {
                put("name", n)
                put("email", e)
                put(
                    "password",
                    Security.hashPassword(p)
                )
                put("role", "user")
                put("created_at", System.currentTimeMillis())
            }

            try {

                db.writableDatabase.insertOrThrow(
                    "users",
                    null,
                    values
                )

                Toast.makeText(
                    this,
                    "Account waa la sameeyay.",
                    Toast.LENGTH_LONG
                ).show()

                finish()

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Email-kan hore ayaa loo isticmaalay.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
