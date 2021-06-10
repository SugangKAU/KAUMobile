package com.example.kaumobile.ui.notifications.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kaumobile.MainActivity
import com.example.kaumobile.R
import com.example.kaumobile.user.UserData.subjectList
import java.util.*


class NotificationHelper {
    private val NOTIFICATION_CHANNEL_ID = "alarm"
    private val NOTIFICATION_NAME = "study"
    val alarmList = mutableMapOf<String,Int>()
    var cnt = 0

    companion object{
        var inst: NotificationHelper? = null


        fun getInstance(): NotificationHelper{
            if (inst == null) {
                inst = NotificationHelper()
            }
            return inst!!
        }
    }



    fun createNotificationChannel(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notificationChannel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "alarm",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                notificationChannel.description = "푸시알림"
                notificationChannel.enableLights(true) // 화면활성화 설정
                //notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500) // 진동패턴 설정
                notificationChannel.enableVibration(true) // 진동 설정
                notificationManager.createNotificationChannel(notificationChannel) // channel 생성
            }
        } catch (nullException: NullPointerException) {
            Toast.makeText(context, "푸시 알림 채널 생성에 실패했습니다. 앱을 재실행하거나 재설치해주세요.", Toast.LENGTH_SHORT)
                .show()
            nullException.printStackTrace()
        }
    }


    fun setAlarm(ctx:Context, type: String, isCreate: Boolean){
        val weekList = arrayOf("일","월","화","수","목","금","토")
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA)
        val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval: Long = 1000 * 60 * 60 * 24 * 7
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
        var debug = true

        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND,0)
        if(isCreate){
            for (i in subjectList) {
                val intent = Intent(ctx, AlarmReceiver::class.java)
                intent.putExtra("className", "${i.className}")
                intent.putExtra("profName", "${i.profName}")
                intent.putExtra("classRoom", "${i.classRoom}")
                intent.putExtra("type", type)
                val pendingIntent = PendingIntent.getBroadcast(ctx, cnt, intent, 0)
                alarmList.put("${i.className}${type}", cnt)
                cnt++

                if (debug) {
                    debug = false
                    alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
                    Log.d("Alarmm", "${i.className}: ${calendar.get(Calendar.MONTH)} ${calendar.get(Calendar.DATE)}")
                }

                if (i.classTime == "#") continue
                else if (i.classTime.length <= 12) {
                    var alarmDay = weekList.indexOf("${i.classTime.substring(1..1)}") + 1
                    var d_Interval = (alarmDay - currentDay + 7) % 7
                    var day = calendar.get(Calendar.DATE) + d_Interval
                    when (type) {
                        "예습" -> {
                            calendar.set(Calendar.DATE, day)
                            calendar.set(
                                Calendar.HOUR_OF_DAY,
                                (i.classTime.substring(2..3).toInt()) - 1
                            )
                            calendar.set(Calendar.MINUTE, 60 - 10)
                            alarmManager.setRepeating(
                                AlarmManager.RTC,
                                calendar.timeInMillis,
                                interval,
                                pendingIntent
                            )
                        }
                        "복습" -> {
                            calendar.set(Calendar.DATE, day)
                            calendar.set(
                                Calendar.HOUR_OF_DAY,
                                (i.classTime.substring(6..7).toInt())
                            )
                            calendar.set(Calendar.MINUTE, 10)
                            alarmManager.setRepeating(
                                AlarmManager.RTC,
                                calendar.timeInMillis,
                                interval,
                                pendingIntent
                            )
                        }
                    }
                } else {
                    var alarmDay = weekList.indexOf("${i.classTime.substring(1..1)}") + 1
                    var d_Interval = (alarmDay - currentDay + 7) % 7
                    var day = calendar.get(Calendar.DATE) + d_Interval
                    var alarmDay2 = weekList.indexOf("${i.classTime.substring(11..11)}") + 1
                    var d_Interval2 = (alarmDay2 - currentDay + 7) % 7
                    var day2 = calendar.get(Calendar.DATE) + d_Interval2
                    when (type) {
                        "예습" -> {
                            calendar.set(Calendar.DATE, day)
                            calendar.set(
                                Calendar.HOUR_OF_DAY,
                                (i.classTime.substring(2..3).toInt()) - 1
                            )
                            calendar.set(Calendar.MINUTE, 60 - 10)
                            alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
                            calendar.set(Calendar.DATE, day2)
                            calendar.set(
                                Calendar.HOUR_OF_DAY,
                                (i.classTime.substring(12..13).toInt()) - 1
                            )
                            calendar.set(Calendar.MINUTE, 60 - 10)
                            alarmManager.setRepeating(
                                AlarmManager.RTC,
                                calendar.timeInMillis,
                                interval,
                                pendingIntent
                            )
                        }
                        "복습" -> {
                            calendar.set(Calendar.DATE, day)
                            calendar.set(
                                Calendar.HOUR_OF_DAY,
                                (i.classTime.substring(6..7).toInt())
                            )
                            calendar.set(Calendar.MINUTE, 10)
                            alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
                            calendar.set(Calendar.DATE, day2)
                            calendar.set(
                                Calendar.HOUR_OF_DAY,
                                (i.classTime.substring(16..17).toInt())
                            )
                            calendar.set(Calendar.MINUTE, 10)
                            alarmManager.setRepeating(
                                AlarmManager.RTC,
                                calendar.timeInMillis,
                                interval,
                                pendingIntent
                            )
                        }
                    }
                }
            }
        }else{
            val intent = Intent(ctx, AlarmReceiver::class.java)

            for( i in alarmList.keys){
                if(type in i){
                    val sender = PendingIntent.getBroadcast(ctx, alarmList[i]!!, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    alarmManager.cancel(sender)
                    sender.cancel()
                }
            }

        }
        checkAlarm(ctx)
    }


    fun createNotification(ctx: Context, className: String, type: String) {
        // 클릭 시 MainActivity 호출
        val intent = Intent(ctx, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) // 대기열에 이미 있다면 MainActivity가 아닌 앱 활성화
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val notificationManager =
            ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val _intent = PendingIntent.getActivity(ctx,0, Intent(ctx,MainActivity::class.java),PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder = NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("${className}-${type} 알림!!")
            .setContentText("${className} ${type}노트를 작성할 시간입니다.")
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(_intent)

        with(NotificationManagerCompat.from(ctx)) {
            // notificationId is a unique int for each notification that you must define
            notify(1000, notificationBuilder.build())
            Log.d("Alarmm","5${notificationBuilder}")
        }

        // Notificatoin을 이루는 공통 부분 정의
//        val notificationBuilder: NotificationCompat.Builder =
//            Builder(ctx, NOTIFICATION_CHANNEL_ID)
//        notificationBuilder.setSmallIcon(R.drawable.smile) // 기본 제공되는 이미지
//            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
//            .setAutoCancel(true) // 클릭 시 Notification 제거


    }

    fun checkAlarm(ctx: Context){
        for( i in alarmList.keys){
            Log.d("Alarmm", "check " + i + "Alarm: ${PendingIntent.getBroadcast(ctx, alarmList[i]!!, Intent(ctx, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)}")
        }
    }
}