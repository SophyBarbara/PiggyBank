package com.example.best

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.best.adapter.Lk
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationsDobav : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications_dobav)

        val buttonAdd : Button = findViewById(R.id.button_addNot)
        val buttonGoNot : Button = findViewById(R.id.button_goNot)
        val exit : TextView = findViewById(R.id.back)
        val Dao = Db.getMyDb(this).getMyDao()
        val dateNot = findViewById<DatePicker>(R.id.dateNot)

        val today = Calendar.getInstance()
        dateNot.setMinDate(today.timeInMillis)

        buttonAdd.setOnClickListener {
            val nameNot = findViewById<EditText>(R.id.nameNot)
            val valueNot = findViewById<EditText>(R.id.valueNot)
            val dateNot = findViewById<DatePicker>(R.id.dateNot)
            val day = dateNot.dayOfMonth
            val month = dateNot.month
            val year = dateNot.year

            if(!nameNot.text.toString().equals("") && !valueNot.text.toString().equals("")&&!nameNot.text.toString().equals(".") && !valueNot.text.toString().equals(".")) {
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)

                val formattedDate = calendar.timeInMillis

                val notifications = Notifications (null,
                    nameNot.text.toString(),
                    valueNot.text.toString().toDouble(),
                    formattedDate
                )
                Thread {
                    Dao.InsertNot(notifications)
                }.start()
                val currentTime = System.currentTimeMillis()
                val calendarR = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 10)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                if (calendarR.timeInMillis > currentTime) {
                    val delay = calendarR.timeInMillis - currentTime
                    val notificationWorkRequest =
                        OneTimeWorkRequestBuilder<NotificationWorker>()
                            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                            .build()
                    WorkManager.getInstance(this).enqueue(notificationWorkRequest)
                }
                Toast.makeText(this, "Уведомление было создано!", Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show()
        }
        exit.setOnClickListener {
            val intent = Intent(this, Lk::class.java)
            startActivity(intent)
        }
        buttonGoNot.setOnClickListener {
            val intent = Intent(this, notiflist::class.java)
            startActivity(intent)
        }
    }
}
