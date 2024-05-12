package com.example.mycityisacleancity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPass: EditText = findViewById(R.id.user_pass)
        val btnReg: Button = findViewById(R.id.button_reg)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)


        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        btnReg.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()
            val initialScores = 0

            if(login == "" || pass == "")
                Toast.makeText(this, "Вы ввели не все данные", Toast.LENGTH_SHORT).show()
            else {
                val user = User(login, pass, initialScores)
                val db = DbHelper(this, null)
                db.addUser(user)
                Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_SHORT).show()

                userLogin.text.clear()
                userPass.text.clear()
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }
    }
}