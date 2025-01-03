package com.teamteam.knowing.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_FCM_TOKEN_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.sp

import com.teamteam.knowing.ui.view.main.LoadingActivity



class FCMClass : FirebaseMessagingService() {
    override fun onNewToken(s: String) {
        super.onNewToken(s)

        //fcm토큰 sp에 저장
        val editor = sp.edit()
        editor.putString(USER_FCM_TOKEN_KEY,s)
        editor.apply()

        Log.d("FCM_TEST", s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        println("notification: ${remoteMessage.notification}")
        val title = remoteMessage.data["title"] //firebase에서 보낸 메세지의 title
        val message = remoteMessage.data["body"] //firebase에서 보낸 메세지의 내용
        val test = remoteMessage.data["test"]
        val intent = Intent(this, LoadingActivity::class.java)
        intent.putExtra("test", test)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = "채널"
            val channel_nm = "채널명"
            val notichannel = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channelMessage = NotificationChannel(
                channel, channel_nm,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channelMessage.description = "채널에 대한 설명."
            channelMessage.enableLights(true)
            channelMessage.enableVibration(true)
            channelMessage.setShowBadge(false)
            channelMessage.vibrationPattern = longArrayOf(1000, 1000)
            notichannel.createNotificationChannel(channelMessage)

            //푸시알림을 Builder를 이용하여 만듭니다.
            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(this, channel)
                    .setSmallIcon(R.mipmap.ic_knowing)
                    .setContentTitle(title) //푸시알림의 제목
                    .setContentText(message) //푸시알림의 내용
                    .setChannelId(channel)
                    .setAutoCancel(true) //선택시 자동으로 삭제되도록 설정.
                    .setContentIntent(pendingIntent) //알림을 눌렀을때 실행할 인텐트 설정.
                    .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(9999, notificationBuilder.build())
        } else {
            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(this, "")
                    .setSmallIcon(R.mipmap.ic_knowing)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(9999, notificationBuilder.build())
        }
    }
}