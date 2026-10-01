package com.devora.community

import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        val input = findViewById<EditText>(
            R.id.supportMessage
        )

        findViewById<Button>(
            R.id.sendSupport
        ).setOnClickListener {

            val text = input.text.toString().trim()

            if (text.isEmpty()) return@setOnClickListener

            val session = getSharedPreferences(
                "devora_session",
                MODE_PRIVATE
            )

            val db = DatabaseHelper(this)

            val values = ContentValues().apply {
                put(
                    "user_id",
                    session.getInt("user_id", 0)
                )
                put("message", text)
                put(
                    "created_at",
                    System.currentTimeMillis()
                )
            }

            db.writableDatabase.insert(
                "support_messages",
                null,
                values
            )

            input.text.clear()

            Toast.makeText(
                this,
                "Fariinta Support-ka waa la diray.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
