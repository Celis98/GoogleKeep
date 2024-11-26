package com.example.googlekeep.ui.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.domain.model.TaskUi

class ReminderReceiver: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context?, intent: Intent?) {
        val scheduleNotificationService = context?.let { ReminderNotification(it) }
        intent?.getSerializableExtra(NOTIFICATION_DATA_KEY, TaskUi::class.java)?.let { task ->
            scheduleNotificationService?.sendTaskReminderNotification(task)
        }
    }

    companion object {
        const val NOTIFICATION_DATA_KEY = "NOTIFICATION_DATA_KEY"
    }

}