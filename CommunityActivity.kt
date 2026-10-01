package com.devora.community

import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CommunityActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var messages: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        db = DatabaseHelper(this)

        messages = findViewById(R.id.messages)

        loadMessages()

        findViewById<Button>(
            R.id.bir
        ).setOnClickListener {

            val input = findViewById<EditText>(
                R.id.message
            )

            val text = input.text.toString().trim()

            if (text.isEmpty()) return@setOnClickListener

            when (Moderation.check(text)) {

                Moderation.Result.BLOCK_WORD -> {
                    Toast.makeText(
                        this,
                        "Fariinta waxaa laga helay eray aan la oggolayn.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                Moderation.Result.BLOCK_LINK -> {
                    Toast.makeText(
                        this,
                        "Links lama oggola Community-ga.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                Moderation.Result.ALLOW -> {}
            }

            val session = getSharedPreferences(
                "devora_session",
                MODE_PRIVATE
            )

            val values = ContentValues().apply {
                put(
                    "user_id",
                    session.getInt("user_id", 0)
                )
                put(
                    "username",
                    session.getString(
                        "username",
                        "User"
                    )
                )
                put("message", text)
                put(
                    "created_at",
                    System.currentTimeMillis()
                )
            }

            db.writableDatabase.insert(
                "messages",
                null,
                values
            )

            input.text.clear()

            loadMessages()
        }
    }

    private fun loadMessages() {

        messages.removeAllViews()

        val cursor = db.readableDatabase.rawQuery(
            "SELECT username,message FROM messages ORDER BY id DESC",
            null
        )

        while (cursor.moveToNext()) {

            val textView = TextView(this)

            textView.text =
                "${cursor.getString(0)}\n${cursor.getString(1)}"

            textView.textSize = 16f
            textView.setPadding(
                24,
                18,
                24,
                18
            )

            messages.addView(textView)
        }

        cursor.close()
    }
}
