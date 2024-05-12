package com.example.mycityisacleancity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast


class MainMenu : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    companion object {
        const val UPLOAD_REQUEST_CODE = 1
    }
    private lateinit var btnScore: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        val db = DbHelper(this, null)
        val btnReport: Button = findViewById(R.id.button_report)
        val btnUpload: Button = findViewById(R.id.button_upload)
        val btnPrizes: Button = findViewById(R.id.button_prizes)
        btnScore = findViewById(R.id.button_scores)
        val btnAccount: ImageButton = findViewById(R.id.button_account)
        val nameText: TextView = findViewById(R.id.name_text)
        val passText: TextView = findViewById(R.id.pass_text)

        nameText.text = username
        passText.text = password

        val name = nameText.text.toString().trim()
        val pass = passText.text.toString().trim()

        passText.visibility = View.INVISIBLE

        val user = db.getUser(name, pass)

        if (user != null) {
            val userScores = user.scores
            btnScore.text = userScores.toString()
        } else {
            Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
        }

        btnReport.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        }

        btnUpload.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        }

        btnPrizes.setOnClickListener {
            val intent = Intent(this, PrizesActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        }

        btnAccount.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val updatedScore = data.getStringExtra("updatedScore")
            btnScore.text = updatedScore
        }
    }


}