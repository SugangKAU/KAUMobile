package com.example.kaumobile.ui.notifications.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val className = intent.getStringExtra("className") as String
        val profName = intent.getStringExtra("profName") as String
        val classRoom = intent.getStringExtra("classRoom") as String
        val type = intent.getStringExtra("type") as String

        Log.d("Alarmm", "received")
        NotificationHelper().createNotification(context, className, type)
    }
}