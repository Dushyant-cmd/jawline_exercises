package com.bytezaptech.jawlineexercise_faceyoga.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.ui.splash.SplashActivity

class AlarmNotificationWorkRequest(val context: Context, workParam: WorkerParameters) :
    CoroutineWorker(context, workParam) {

    override suspend fun doWork(): ListenableWorker.Result {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                context.packageName,
                "notification_channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            val pendingIntent = PendingIntent.getActivity(context, 101, Intent(context, SplashActivity::class.java), PendingIntent.FLAG_IMMUTABLE)

            //Build notification
            val notification = Notification.Builder(context, context.packageName)
                .setContentTitle("ByteZapTech")
                .setContentText("Hey its time to sharp your jawline.")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.canva_logo)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setColor(context.resources.getColor(R.color.black, context.resources.newTheme()))
                .build()

            setForegroundAsync(ForegroundInfo(101, notification))//set as foreground with notification manager

            val notificationService = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationService.createNotificationChannel(notificationChannel)

            with(NotificationManagerCompat.from(context)){
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notify(101, notification)
                }
            }
        }
        return Result.success()
    }
}