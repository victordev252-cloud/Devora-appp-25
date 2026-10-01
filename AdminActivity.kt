package com.devora.community

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val db = DatabaseHelper(this)

        val users = db.readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM users",
            null
        )

        users.moveToFirst()

        val count = users.getInt(0)

        users.close()

        findViewById<TextView>(
            R.id.stats
        ).text = """
            DEVORA ADMIN

            Users: $count

            Community moderation:
            ACTIVE

            Link protection:
            ACTIVE

            Word protection:
            ACTIVE

            Reports:
            READY

            Support:
            READY
        """.trimIndent()
    }
}
