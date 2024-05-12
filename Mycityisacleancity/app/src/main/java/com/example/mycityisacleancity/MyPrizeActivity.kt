package com.example.mycityisacleancity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyPrizeActivity : AppCompatActivity() {

    private lateinit var myPrizesAdapter: MyPrizesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_prize)

        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val db = DbHelper(this, null)

        val myPrizesRecyclerView: RecyclerView = findViewById(R.id.itemsListPrize)


        val myPrizesList = db.getMyPrizes(username)


        myPrizesAdapter = MyPrizesAdapter(myPrizesList, this)
        myPrizesRecyclerView.adapter = myPrizesAdapter
        myPrizesRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
