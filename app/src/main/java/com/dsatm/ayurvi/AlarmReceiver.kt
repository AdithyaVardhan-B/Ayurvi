package com.dsatm.ayurvi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Get the custom message passed via the intent
        val message = intent?.getStringExtra("ALARM_MESSAGE") ?: "Alarm!"

        // Play a sound when the alarm is triggered
        val mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound) // Add your alarm sound to res/raw
        mediaPlayer.start()

        // Display a notification with the custom message
        val channelId = "alarm_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a NotificationChannel for Android 8.0 and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_alarm) // Replace with your icon
            .setContentTitle("Alarm")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(0, notification)
    }
}
