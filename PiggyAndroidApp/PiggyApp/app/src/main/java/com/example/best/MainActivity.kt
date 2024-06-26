package com.example.best

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.best.adapter.Lk
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preference = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val first = preference.getString("FirstTimeInstall", "")

        if (first.equals("Yes")) {
            val intent = Intent(this, Lk::class.java)
            this.finishAffinity()
            startActivity(intent)

        } else {
            val Dao = Db.getMyDb(this).getMyDao()

            val button: Button = findViewById(R.id.button)

            button.setOnClickListener {
                if(!findViewById<EditText>(R.id.nameEditText).text.toString().isNullOrEmpty() &&!findViewById<EditText>(R.id.depositEditText).text.toString().isNullOrEmpty()&&!findViewById<EditText>(R.id.depositEditText).text.toString().equals("."))
                {
                    val name: String = findViewById<EditText>(R.id.nameEditText).text.toString()

                val deposit = findViewById<EditText>(R.id.depositEditText).text.toString().trim()
                val calendar = Calendar.getInstance()
                val currentDate = calendar.get(Calendar.DATE)
                val currentMonth = calendar.get(Calendar.MONTH)
                val currentyear = calendar.get(Calendar.YEAR) + 1
                val user = User(
                    null,
                    name,
                    deposit.toDouble(),
                    currentMonth,
                    currentyear
                )
                val cat = Category(
                    "Остаток по счету",
                    deposit.toDouble()
                )
                val saved = Category(
                    "Сбережения",
                    0.0
                )
                val income = Category(
                    "Поступления",
                    0.0
                )
                    val credit = Category(
                        "Кредитная карта",
                        0.0
                    )
                Thread {
                    Dao.insertUser(user)
                    Dao.InsertCategory(cat)
                    Dao.InsertCategory(saved)
                    Dao.InsertCategory(income)
                    Dao.InsertCategory(credit)
                }.start()

                val editor = preference.edit()
                editor.putString("FirstTimeInstall", "Yes")
                editor.apply()
                val intent = Intent(this, Lk::class.java)
                this.finishAffinity()
                startActivity(intent)}
                else
                {
                    Toast.makeText(this@MainActivity,"Заполните все поля", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}