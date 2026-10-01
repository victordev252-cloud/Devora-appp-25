package com.devora.community

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DatabaseHelper(this)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val signup = findViewById<Button>(R.id.signup)

        login.setOnClickListener {

            val e = email.text.toString().trim()
            val p = Security.hashPassword(
                password.text.toString()
            )

            val cursor = db.readableDatabase.rawQuery(
                "SELECT id,name,role,banned FROM users WHERE email=? AND password=?",
                arrayOf(e, p)
            )

            if (cursor.moveToFirst()) {

                val banned = cursor.getInt(3)

                if (banned == 1) {
                    Toast.makeText(
                        this,
                        "Account-ka waa la xannibay.",
                        Toast.LENGTH_LONG
                    ).show()

                    cursor.close()
                    return@setOnClickListener
                }

                getSharedPreferences(
                    "devora_session",
                    MODE_PRIVATE
                ).edit()
                    .putBoolean("logged_in", true)
                    .putInt("user_id", cursor.getInt(0))
                    .putString("username", cursor.getString(1))
                    .putString("role", cursor.getString(2))
                    .apply()

                cursor.close()

                startActivity(
                    Intent(this, HomeActivity::class.java)
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Email ama password waa khaldan.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            cursor.close()
        }

        signup.setOnClickListener {
            startActivity(
                Intent(this, SignupActivity::class.java)
            )
        }
    }
}
