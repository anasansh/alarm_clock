package com.example.myapplication.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.myapplication.ui.MainActivity


class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmId = intent?.getIntExtra(AlarmUtils.EXTRA_ALARM_ID, -1)
        val alarmLabel = intent?.getStringExtra(AlarmUtils.EXTRA_ALARM_LABEL)
        if (alarmId != -1) {
            val resultIntent = Intent(
                context,
                MainActivity::class.java
            )
            var pendingIntent: PendingIntent? = null
            pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(
                    context, 0, resultIntent, PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getActivity(
                    context,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            val id = "my_channel_01"
            val name: CharSequence = "Alarm Channel"
            val description = "Alarm clock"

            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(id, name, importance)
            mChannel.description = description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)

            val notificationBuilder = NotificationCompat.Builder(context, id)
                .setContentTitle(alarmLabel)
                .setContentText("Alarm ID: $alarmId")
                .setSmallIcon(com.example.myapplication.R.drawable.ic_clock)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setChannelId(id)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            if (alarmId != null) {
                notificationManager.notify("", alarmId, notificationBuilder.build())
            }
        }
    }
}