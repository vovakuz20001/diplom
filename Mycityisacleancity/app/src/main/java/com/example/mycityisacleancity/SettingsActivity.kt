package com.example.mycityisacleancity

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class SettingsActivity : AppCompatActivity() {

    private var intentUsername: String? = null
    private var intentPassword: String? = null
    private var newUsername: String? = null
    private var newPassword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        intentUsername = intent.getStringExtra("username")
        intentPassword = intent.getStringExtra("password")

        val db = DbHelper(this, null)

        val userLogin: EditText = findViewById(R.id.user_loginSet)
        val userPass: EditText = findViewById(R.id.user_passSet)
        val btnSetting: ImageButton = findViewById(R.id.button_setting)
        val btnSave: Button = findViewById(R.id.btn_save)

        val user = db.getUser(intentUsername ?: "", intentPassword ?: "")
        if (user != null) {
            userLogin.setText(user.login)
            userPass.setText(user.pass)
        } else {
            userLogin.setText(intentUsername ?: "")
            userPass.setText(intentPassword ?: "")
        }

        userLogin.setTextColor(Color.GRAY)
        userLogin.setTypeface(null, Typeface.ITALIC)

        userPass.setTextColor(Color.GRAY)
        userPass.setTypeface(null, Typeface.ITALIC)

        btnSetting.setOnClickListener {
            userLogin.isFocusableInTouchMode = true
            userLogin.requestFocus()
            userLogin.isFocusable = true
            userLogin.isClickable = true
            userLogin.setTextColor(Color.BLACK)
            userLogin.setTypeface(null, Typeface.NORMAL)

            userPass.isFocusableInTouchMode = true
            userPass.requestFocus()
            userPass.isFocusable = true
            userPass.isClickable = true
            userPass.setTextColor(Color.BLACK)
            userPass.setTypeface(null, Typeface.NORMAL)
        }

        btnSave.setOnClickListener {
            newUsername = userLogin.text.toString().trim()
            newPassword = userPass.text.toString().trim()

            val finalUsername = if (intentUsername == newUsername) intentUsername else newUsername
            val finalPassword = if (intentPassword == newPassword) intentPassword else newPassword

            if (finalUsername.isNullOrEmpty() || finalPassword.isNullOrEmpty()) {
                Toast.makeText(this, "Логин и пароль не могут быть пустыми", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = db.updateCredentials(intentUsername ?: "", intentPassword ?: "", finalUsername, finalPassword)
            if (success) {
                Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_SHORT).show()
                intentUsername = finalUsername
                intentPassword = finalPassword
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
