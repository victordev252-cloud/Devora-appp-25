package com.devora.community

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val session = getSharedPreferences(
            "devora_session",
            MODE_PRIVATE
        )

        val username = session.getString(
            "username",
            "User"
        )

        findViewById<TextView>(
            R.id.welcome
        ).text = "Ku soo dhowow, $username"

        findViewById<Button>(
            R.id.community
        ).setOnClickListener {
            startActivity(
                Intent(this, CommunityActivity::class.java)
            )
        }

        findViewById<Button>(
            R.id.support
        ).setOnClickListener {
            startActivity(
                Intent(this, SupportActivity::class.java)
            )
        }

        findViewById<Button>(
            R.id.map
        ).setOnClickListener {
            startActivity(
                Intent(this, MapActivity::class.java)
            )
        }

        findViewById<Button>(
            R.id.admin
        ).setOnClickListener {

            if (
                session.getString("role", "user")
                    == "admin"
            ) {
                startActivity(
                    Intent(this, AdminActivity::class.java)
                )
            }
        }

        findViewById<Button>(
            R.id.logout
        ).setOnClickListener {

            session.edit().clear().apply()

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()
        }
    }
}
