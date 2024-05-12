package com.example.mycityisacleancity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userPass: EditText = findViewById(R.id.user_pass_auth)
        val btnAuth: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)


        linkToReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnAuth.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if(login == "" || pass == "") {
                Toast.makeText(this, "Вы ввели не все данные", Toast.LENGTH_SHORT).show()
            } else {
                val db = DbHelper(this, null)

                if (login == "admin" && pass == "admin") {
                    val intent = Intent(this, AdminActivity::class.java)
                    intent.putExtra("username", login)
                    intent.putExtra("password", pass)
                    startActivity(intent)
                    return@setOnClickListener
                }

                val user = db.getUser(login, pass)

                if(user != null) {
                    Toast.makeText(this, "Пользователь '$login' авторизован", Toast.LENGTH_SHORT).show()
                    userLogin.text.clear()
                    userPass.text.clear()

                    val intent = Intent(this, MainMenu::class.java)

                    intent.putExtra("username", login)
                    intent.putExtra("password", pass)

                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Пользователь '$login' НЕ авторизован", Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}