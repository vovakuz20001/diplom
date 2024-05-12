package com.example.mycityisacleancity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class ReportActivity : AppCompatActivity() {

    companion object {
        const val PICK_PHOTO_REQUEST_CODE = 1
    }

    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        image = findViewById(R.id.imageViewReport)
        val btnScore: Button = findViewById(R.id.button_scores_rep)
        val btnSend: Button = findViewById(R.id.btn_sendReport)
        val editAddress: EditText = findViewById(R.id.report_address)

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val db = DbHelper(this, null)

        val nameText: TextView = findViewById(R.id.name_textRep)
        val passText: TextView = findViewById(R.id.pass_textRep)

        nameText.text = username
        passText.text = password

        val name = nameText.text.toString().trim()
        val pass = passText.text.toString().trim()

        nameText.visibility = View.INVISIBLE
        passText.visibility = View.INVISIBLE

        val user = db.getUser(name, pass)

        if (user != null) {
            val userScores = user.scores
            btnScore.text = userScores.toString()
        } else {
            Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
        }

        image.setOnClickListener {
            openGallery()
        }

        btnSend.setOnClickListener {
            val address = editAddress.text.toString()
            val type = "Сообщение"
            val point = 50
            val usernames = nameText.text.toString().trim()

            db.addMyHistory(usernames, address, type, point)

            val currentScore = btnScore.text.toString().toIntOrNull() ?: 0
            val newScore = currentScore + 50
            btnScore.text = newScore.toString()

            db.updateScores(name, pass, newScore)
            val intent = Intent(this, MainMenu::class.java)
            intent.putExtra("address", address)
            intent.putExtra("type", type)
            intent.putExtra("point", point)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            image.setImageURI(selectedImageUri)
        }
    }

    private fun openGallery() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhotoIntent, PICK_PHOTO_REQUEST_CODE)
    }
}
