package com.example.mycityisacleancity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycityisacleancity.PrizeItem

class PrizesActivity : AppCompatActivity() {

    lateinit var btnScore: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prizes)

        val itemsList: RecyclerView = findViewById(R.id.itemsList)
        val items = arrayListOf<PrizeItem>()

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val db = DbHelper(this, null)
        btnScore = findViewById(R.id.button_scores_pr)

        val nameText: TextView = findViewById(R.id.name_textPr)
        val passText: TextView = findViewById(R.id.pass_textPr)

        nameText.text = username
        passText.text = password

        val name = nameText.text.toString().trim()
        val pass = passText.text.toString().trim()

        val user = db.getUser(name, pass)

        val currentScore = if (user != null) {
            user.scores
        } else {
            Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
            0
        }

        btnScore.text = currentScore.toString()

        items.add(PrizeItem(1, "prize1", "Prize1", "PrizeDesc1", "TEST", 100))
        items.add(PrizeItem(2, "prize1", "Prize2", "PrizeDesc2", "TEST1", 200))

        itemsList.layoutManager = LinearLayoutManager(this)
        val adapter = PrizesAdapter(items, this, currentScore, btnScore.text.toString(), name, pass, db)
        itemsList.adapter = adapter
    }

    fun updateScoreText(newScore: String) {
        btnScore.text = newScore
    }
}


