package com.example.kaumobile.application

import android.app.Application
import android.util.Log
import com.example.kaumobile.ui.notifications.notification.NotificationHelper

class Initialize:Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationHelper().createNotificationChannel(applicationContext)
    }
}