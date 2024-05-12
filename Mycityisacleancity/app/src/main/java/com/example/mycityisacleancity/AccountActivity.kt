package com.example.mycityisacleancity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val btnSetting: Button = findViewById(R.id.button_setting)
        val btnPrizes: Button = findViewById(R.id.button_myPrizes)
        val btnActivity: Button = findViewById(R.id.button_myActivity)

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        btnSetting.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        }

        btnPrizes.setOnClickListener {
            val intent = Intent(this, MyPrizeActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        }

        btnActivity.setOnClickListener {
            val intent = Intent(this, MyHistoryActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        }
    }
}