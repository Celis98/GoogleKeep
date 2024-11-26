package com.example.googlekeep.ui.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.example.domain.model.TaskUi
import com.example.googlekeep.R
import com.example.googlekeep.ui.MainActivity
import com.example.googlekeep.ui.notification.ReminderReceiver.Companion.NOTIFICATION_DATA_KEY


class ReminderNotification(private val context: Context) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun sendTaskReminderNotification(taskUi: TaskUi) {

        val contentIntent = PendingIntent.getActivity(context, 0,
            Intent(context, MainActivity::class.java).apply {
                putExtra(NOTIFICATION_DATA_KEY, taskUi)
            }, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationTitle = "Tu tarea ${taskUi.title} ya se venció"
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentText(context.getString(R.string.app_name))
            .setContentTitle(notificationTitle)
            .setSmallIcon(R.drawable.task_icon)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(context.bitmapFromResource(R.drawable.dog))
            )
            .setContentIntent(contentIntent)
            .build()

        notificationManager.notify(taskUi.id, notification)
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId: Int
    ) = BitmapFactory.decodeResource(
        resources,
        resId
    )

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "tasks_reminders"
        const val NOTIFICATION_CHANNEL_NAME = "Recordatorios de tareas"
    }

}