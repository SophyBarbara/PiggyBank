package com.example.best

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val CHANNEL_ID = "product_expiry_channel"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_MUTABLE
        )

        val notifications = Db.getMyDb(applicationContext).getMyDao().getNotif()

        val currentTimestamp = Calendar.getInstance().timeInMillis

        val tommorowNot = notifications.filter { it.date < currentTimestamp + 86400000 }

        val notificationList = mutableListOf<Notification>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        for (notifications in tommorowNot) {
            val todayDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val currentDate = todayDateFormat.format(Date())

            if (todayDateFormat.format(notifications.date) == currentDate ||
                todayDateFormat.format( notifications.date - 86400000) == currentDate) {
                val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)

                if (todayDateFormat.format(notifications.date) == currentDate) {
                    builder
                        .setContentTitle("Напоминаем!")
                        .setContentText("Время оплатить: ${notifications.name} " +
                                "\nна сумму ${notifications.value}!")
                } else {
                    builder
                        .setContentTitle("Напоминаем!")
                        .setContentText("Не забудьте оплатить: ${notifications.name}"+
                            "\nна сумму ${notifications.value}!")
                }

                val notification = builder.build()
                notificationList.add(notification)
            }
        }

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        for (notification in notificationList) {
            notificationManager.notify(generateUniqueNotificationId(), notification)
        }

        Result.success()
    }

    private fun createNotificationChannel() {
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                "Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Text"
            }
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private val notificationIdCounter = AtomicInteger(0)

    private fun generateUniqueNotificationId(): Int {
        return notificationIdCounter.getAndIncrement()
    }
}
