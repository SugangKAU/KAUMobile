package com.example.kaumobile.ui.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.impl.foreground.SystemForegroundService
import com.example.kaumobile.MainActivity
import com.example.kaumobile.R

private val colorList = arrayOf("#FF3721", "#213EFF")

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        val buttonView = root.findViewById<LinearLayout>(R.id.view_notify)
        val dynamicClass = Array(20){ Button(requireContext()) }
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(0, changeDP(10), 0, changeDP(10))

        for (i in 0 .. 1) {
            dynamicClass[i].layoutParams = layoutParams
            dynamicClass[i].id = i
            dynamicClass[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_alarm, 0, 0, 0)
            dynamicClass[i].setPadding(30, 0, 0, 0)
            dynamicClass[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            dynamicClass[i].setText("현재 ${3}개의 미완료된 과제가 있습니다!")
            dynamicClass[i].setBackgroundColor(Color.parseColor(colorList[i % 2]))
            dynamicClass[i].setTextColor(Color.parseColor("#FFFFFF"))
            buttonView.addView(dynamicClass[i])

            dynamicClass[0].setOnClickListener {

            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "desp"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1000", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val btn = root.findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val _intent = PendingIntent.getActivity(requireContext(),0, Intent(requireContext(),MainActivity::class.java),PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = NotificationCompat.Builder(requireContext(),"1000")
               .setSmallIcon(R.drawable.ic_alarm)
               .setContentTitle("title")
               .setContentText("content")
               .setDefaults(Notification.DEFAULT_VIBRATE)
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .setAutoCancel(true)
               .setContentIntent(_intent)
            with(NotificationManagerCompat.from(requireContext())) {
                // notificationId is a unique int for each notification that you must define
                notify(1000, builder.build())
                Log.d("Alarm","5${builder}")
            }
        }


        return root
    }

    private fun changeDP(value : Int) : Int{
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }
}