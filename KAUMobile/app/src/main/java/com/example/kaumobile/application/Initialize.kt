package com.example.kaumobile.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.kaumobile.ui.notifications.notification.NotificationHelper
import com.example.kaumobile.user.UserData
import com.google.firebase.firestore.auth.User

class Initialize:Application(), SharedPreferences.OnSharedPreferenceChangeListener {
    companion object{
        lateinit var ctx: Context
    }
    override fun onCreate() {
        super.onCreate()
        val noti = NotificationHelper()
        val user = UserData
        var sps = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        ctx = applicationContext
        noti.createNotificationChannel(applicationContext)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when(key){
            "key_prepare_lock"->{
                if(sharedPreferences.getBoolean(key,false)){

                }else{

                }
            }
            "key_prepare_notify"->{
                if(sharedPreferences.getBoolean(key,false)){
                    NotificationHelper().setAlarm(applicationContext, "예습",true)
                    Log.d("Alarmm","init ${UserData.subjectList}")
                }else{
                    NotificationHelper().setAlarm(applicationContext, "예습",false)
                }
            }
            "key_review_lock"->{
                if(sharedPreferences.getBoolean(key,false)){

                }else{

                }
            }
            "key_review_notify" -> {
                if(sharedPreferences.getBoolean(key,false)){
                    NotificationHelper().setAlarm(applicationContext, "복습",true)
                    Log.d("Alarmm","init ${UserData.subjectList}")
                }else{
                    NotificationHelper().setAlarm(applicationContext, "복습",false)
                }
            }
            "key_assign_lock"->{
                if(sharedPreferences.getBoolean(key,false)){

                }else{

                }
            }
            "key_assign_notify"->{
                if(sharedPreferences.getBoolean(key,false)){

                }else{

                }
            }
            "key_vibe"->{
                if(sharedPreferences.getBoolean(key,false)){

                }else{

                }
            }
            "key_lms"->{
                if(sharedPreferences.getBoolean(key,false)){

                }else{

                }
            }
        }
    }
}



