package com.example.googlekeep.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.domain.model.TaskUi
import com.example.googlekeep.ui.notification.ReminderReceiver.Companion.NOTIFICATION_DATA_KEY
import java.util.Calendar

class ScheduleNotification {

    fun scheduleNotification(
        context: Context,
        taskUi: TaskUi
    ) {
        if (taskUi.dueDate != null && taskUi.dueTime != null) {
            val intent = Intent(context.applicationContext, ReminderReceiver::class.java).apply {
                putExtra(NOTIFICATION_DATA_KEY, taskUi)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context.applicationContext,
                taskUi.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val selectedDate = Calendar.getInstance().apply {
                timeInMillis = taskUi.dueDate!!
            }

            val year = selectedDate.get(Calendar.YEAR)
            val month = selectedDate.get(Calendar.MONTH)
            val day = selectedDate.get(Calendar.DAY_OF_MONTH)

            val hour = taskUi.dueTime!!.split(":").first().toInt()
            val minute = taskUi.dueTime!!.split(":").last().toInt()

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day, hour, minute)

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )

            Toast.makeText(context, "Recordatorio creado", Toast.LENGTH_SHORT).show()
        }
    }

}