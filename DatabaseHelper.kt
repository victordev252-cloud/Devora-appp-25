package com.devora.community

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "devora.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                role TEXT DEFAULT 'user',
                banned INTEGER DEFAULT 0,
                created_at INTEGER
            )
        """)

        db.execSQL("""
            CREATE TABLE messages (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                username TEXT,
                message TEXT,
                created_at INTEGER
            )
        """)

        db.execSQL("""
            CREATE TABLE reports (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                message_id INTEGER,
                reporter_id INTEGER,
                reason TEXT,
                status TEXT DEFAULT 'pending',
                created_at INTEGER
            )
        """)

        db.execSQL("""
            CREATE TABLE blocked_words (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                word TEXT UNIQUE
            )
        """)

        db.execSQL("""
            CREATE TABLE support_messages (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                message TEXT,
                reply TEXT,
                status TEXT DEFAULT 'open',
                created_at INTEGER
            )
        """)

        db.execSQL("""
            CREATE TABLE warnings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                reason TEXT,
                created_at INTEGER
            )
        """)

        db.execSQL("""
            CREATE TABLE locations (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                latitude REAL,
                longitude REAL,
                created_at INTEGER
            )
        """)

        db.execSQL("""
            CREATE TABLE admin_logs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                action TEXT,
                created_at INTEGER
            )
        """)

        db.execSQL("""
            CREATE TABLE settings (
                key TEXT PRIMARY KEY,
                value TEXT
            )
        """)

        val words = listOf(
            "gus",
            "siil",
            "naas",
            "dawo",
            "wasmo",
            "hoyada",
            "silkeed",
            "h.w",
            "e.w",
            "idhuuq",
            "dhuuq",
            "is was"
        )

        for (word in words) {
            val values = ContentValues()
            values.put("word", word)
            db.insert("blocked_words", null, values)
        }
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS messages")
        db.execSQL("DROP TABLE IF EXISTS reports")
        db.execSQL("DROP TABLE IF EXISTS blocked_words")
        db.execSQL("DROP TABLE IF EXISTS support_messages")
        db.execSQL("DROP TABLE IF EXISTS warnings")
        db.execSQL("DROP TABLE IF EXISTS locations")
        db.execSQL("DROP TABLE IF EXISTS admin_logs")
        db.execSQL("DROP TABLE IF EXISTS settings")

        onCreate(db)
    }
    }
