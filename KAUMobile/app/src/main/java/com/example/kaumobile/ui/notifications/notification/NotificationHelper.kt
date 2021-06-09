package com.example.kaumobile.ui.notifications.notification

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.kaumobile.MainActivity


class NotificationHelper {
    private val NOTIFICATION_CHANNEL_ID = "alarm"
    private val NOTIFICATION_NAME = "study"
    private val WORK_A_NOTIFICATION_CODE = 0
    private val WORK_B_NOTIFICATION_CODE = 1

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

    fun createNotification(ctx: Context, workName: String) {
        // 클릭 시 MainActivity 호출
        val intent = Intent(ctx, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) // 대기열에 이미 있다면 MainActivity가 아닌 앱 활성화
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val notificationManager =
            ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("알림")
            .setContentText("내용")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Notificatoin을 이루는 공통 부분 정의
//        val notificationBuilder: NotificationCompat.Builder =
//            Builder(ctx, NOTIFICATION_CHANNEL_ID)
//        notificationBuilder.setSmallIcon(R.drawable.smile) // 기본 제공되는 이미지
//            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
//            .setAutoCancel(true) // 클릭 시 Notification 제거

        // A이벤트 알림을 생성한다면
        if (workName == NOTIFICATION_NAME) {
            // Notification 클릭 시 동작할 Intent 입력, 중복 방지를 위해 FLAG_CANCEL_CURRENT로 설정, CODE를 다르게하면 개별 생성
            // Code가 같으면 같은 알림으로 인식하여 갱신작업 진행
            val pendingIntent = PendingIntent.getActivity(
                ctx,
                WORK_A_NOTIFICATION_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            // Notification 제목, 컨텐츠 설정
            notificationBuilder.setContentTitle("WorkerA Notification")
                .setContentText("set a Notification contents")
                .setContentIntent(pendingIntent)
            notificationManager?.notify(WORK_A_NOTIFICATION_CODE, notificationBuilder.build())
        }
//        } else if (workName == WORK_B_NAME) {
//            val pendingIntent = PendingIntent.getActivity(
//                ctx,
//                WORK_B_NOTIFICATION_CODE,
//                intent,
//                PendingIntent.FLAG_CANCEL_CURRENT
//            )
//            notificationBuilder.setContentTitle("WorkerB Notification")
//                .setContentText("set a Notification contents")
//                .setContentIntent(pendingIntent)
//            notificationManager?.notify(WORK_B_NOTIFICATION_CODE, notificationBuilder.build())
//        }
    }
}