package com.salton123.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import com.salton123.eleph.R
import kt.getString

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/25 10:25
 * ModifyTime: 10:25
 * Description:
 */
class ForegroundNotificationHelper {
    // 通知渠道的id
    var channelId = R.string.notification_channel_id.getString()
    var channelPosition = 1
    var notifyTitle = R.string.notification_title.getString()
    var notifyContent = R.string.notification_content.getString()
    var resId = R.drawable.ic_launcher
    var pendingIntent: PendingIntent? = null

    //    private  String notifyNma = "主服务";
    fun startForegroundNotification(service: Service) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //启动前台服务而不显示通知的漏洞已在 API Level 25 修复
            val manager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelId, notifyTitle, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights(true) //设置提示灯
            channel.lightColor = Color.GREEN //设置提示灯颜色
            channel.setShowBadge(true) //显示logo
            channel.description = "" //设置描述
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC //设置锁屏可见 VISIBILITY_PUBLIC=可见
            channel.enableVibration(false)
            channel.setSound(null, null)
            manager.createNotificationChannel(channel)
            val notification = Notification.Builder(service, channelId)
                .setContentTitle(notifyTitle) //标题
                .setContentText(notifyContent) //内容
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(resId) //小图标一定需要设置,否则会报错(如果不设置它启动服务前台化不会报错,但是你会发现这个通知不会启动),如果是普通通知,不设置必然报错
                .setLargeIcon(BitmapFactory.decodeResource(service.resources, resId))
                .build()
            service.startForeground(channelPosition, notification)
            //服务前台化只能使用startForeground()方法,不能使用 notificationManager.notify(1,notification);
            // 这个只是启动通知使用的,使用这个方法你只需要等待几秒就会发现报错了
        } else {
            //利用漏洞在 API Level 18 及以上的 Android 系统中，启动前台服务而不显示通知
//            service.startForeground(Foreground_ID, new Notification());
            val notification = Notification.Builder(service)
                .setContentTitle(notifyTitle) //设置标题
                .setContentText(notifyContent) //设置内容
                .setWhen(System.currentTimeMillis()) //设置创建时间
                .setSmallIcon(resId) //设置状态栏图标
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(service.resources, resId)) //设置通知栏图标
                .build()
            service.startForeground(channelPosition, notification)
        }
    }

    fun deleteForegroundNotification(service: Service) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mNotificationManager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val mChannel = mNotificationManager.getNotificationChannel(channelId)
            if (null != mChannel) {
                mNotificationManager.deleteNotificationChannel(channelId)
            }
        } else {
            val notificationManager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(channelPosition)
        }
    }
}