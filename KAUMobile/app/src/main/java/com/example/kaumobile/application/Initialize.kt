package com.example.kaumobile.application

import android.app.Application
import android.util.Log
import com.example.kaumobile.ui.notifications.notification.NotificationHelper
import com.example.kaumobile.user.UserData
import com.google.firebase.firestore.auth.User

class Initialize:Application() {

    override fun onCreate() {
        super.onCreate()
        val noti = NotificationHelper()
        val user = UserData
        noti.createNotificationChannel(applicationContext)
    }
}