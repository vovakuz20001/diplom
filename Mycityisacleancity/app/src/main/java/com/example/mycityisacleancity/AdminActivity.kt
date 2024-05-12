package com.example.mycityisacleancity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        val btnUserList: Button = findViewById(R.id.button_user_list)
        val btnUserPoint: Button = findViewById(R.id.button_user_point)


    }
}