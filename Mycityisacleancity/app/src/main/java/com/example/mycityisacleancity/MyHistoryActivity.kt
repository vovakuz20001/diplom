package com.example.mycityisacleancity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_history)

        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val dbHelper = DbHelper(this, null)
        val historyList = dbHelper.getMyHistory(username)

        val addressText: TextView = findViewById(R.id.address)
        addressText.text = if (historyList.isNotEmpty()) {
            "Моя активность"
        } else {
            "Нет данных об истории"
        }

        val myHistoryRecyclerView: RecyclerView = findViewById(R.id.itemsListHistory)
        val myHistoryAdapter = MyHistoryAdapter(historyList, this) // Pass context here
        myHistoryRecyclerView.adapter = myHistoryAdapter
        myHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
