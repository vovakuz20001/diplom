package com.example.mycityisacleancity

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app_1", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE user1 (id INTEGER PRIMARY KEY, login TEXT, pass TEXT, scores INTEGER DEFAULT 0)"
        db?.execSQL(query)

        val query2 = "CREATE TABLE my_prizes (id INTEGER PRIMARY KEY, login TEXT, prize_title TEXT, prize_price INTEGER)"
        db?.execSQL(query2)

        val query3 = "CREATE TABLE my_history (id INTEGER PRIMARY KEY, login TEXT, address TEXT, type TEXT, point INTEGER)"
        db?.execSQL(query3)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS user1")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues().apply {
            put("login", user.login)
            put("pass", user.pass)
            put("scores", user.scores)
        }

        val db = writableDatabase
        db.insert("user1", null, values)
        db.close()
    }

    fun getUser(login: String, pass: String): User? {
        val db = readableDatabase
        val result = db.rawQuery("SELECT * FROM user1 WHERE login = '$login' AND pass = '$pass'", null)
        return if (result.moveToFirst()) {
            val scoresIndex = result.getColumnIndex("scores")
            val scores = if (scoresIndex != -1) result.getInt(scoresIndex) else 0
            User(login, pass, scores)
        } else {
            null
        }
    }

    fun updateScores(login: String, pass: String, newScores: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("scores", newScores)
        }
        db.update("user1", values, "login = ? AND pass = ?", arrayOf(login, pass))
        db.close()
    }

    fun updateCredentials(oldUsername: String, oldPassword: String, newUsername: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, newUsername)
            put(COLUMN_PASSWORD, newPassword)
        }
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(oldUsername, oldPassword)
        val success = db.update(TABLE_USERS, values, selection, selectionArgs)
        db.close()
        return success > 0
    }

    fun addMyPrize(username: String, prizeTitle: String, prizePrice: Int) {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PRIZE_TITLE, prizeTitle)
            put(COLUMN_PRIZE_PRICE, prizePrice)
        }
        writableDatabase.insert(TABLE_MY_PRIZES, null, values)
    }

    fun getMyPrizes(username: String): List<MyPrizeItem> {
        val prizesList = mutableListOf<MyPrizeItem>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_MY_PRIZES WHERE $COLUMN_USERNAME = ?", arrayOf(username))
        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                val prizeTitleIndex = cursor.getColumnIndex(COLUMN_PRIZE_TITLE)
                val prizePointsIndex = cursor.getColumnIndex(COLUMN_PRIZE_PRICE)
                if (prizeTitleIndex != -1 && prizePointsIndex != -1) {
                    val prizeTitle = cursor.getString(prizeTitleIndex)
                    val prizePoints = cursor.getInt(prizePointsIndex)
                    prizesList.add(MyPrizeItem(prizeTitle, prizePoints.toString()))
                }
            }
        }
        return prizesList
    }

    fun addMyHistory(username: String, address: String, type: String, point: Int) {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_ADDRESS, address)
            put(COLUMN_TYPE, type)
            put(COLUMN_POINT, point)
        }
        writableDatabase.insert(TABLE_MY_HISTORY, null, values)
        Log.d("DbHelper", "Added to my_history: username=$username, address=$address, type=$type, point=$point")
    }


    fun getMyHistory(username: String): List<MyHistoryItem> {
        val historyList = mutableListOf<MyHistoryItem>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_MY_HISTORY WHERE $COLUMN_USERNAME = ?", arrayOf(username))
        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                val addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS)
                val typeIndex = cursor.getColumnIndex(COLUMN_TYPE)
                val pointIndex = cursor.getColumnIndex(COLUMN_POINT)
                if (addressIndex != -1 && typeIndex != -1 && pointIndex != -1) {
                    val address = cursor.getString(addressIndex)
                    val type = cursor.getString(typeIndex)
                    val point = cursor.getInt(pointIndex).toString()
                    historyList.add(MyHistoryItem(address, type, point))
                }
            }
        }
        for (item in historyList) {
            Log.d("DbHelper", "Address: ${item.address}, Type: ${item.type}, Point: ${item.point}")
        }

        return historyList
    }

    companion object {
        const val COLUMN_USERNAME = "login"
        const val COLUMN_PASSWORD = "pass"
        const val TABLE_USERS = "user1"
        const val TABLE_MY_PRIZES = "my_prizes"
        const val COLUMN_PRIZE_TITLE = "prize_title"
        const val COLUMN_PRIZE_PRICE = "prize_price"
        const val COLUMN_PRIZE_DESC = "prize_desc"
        const val TABLE_MY_HISTORY = "my_history"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_TYPE = "type"
        const val COLUMN_POINT = "point"
    }
}

