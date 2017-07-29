package com.example.sikefeng.testproject.notificationdemo;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.sikefeng.testproject.MainActivity;
import com.example.sikefeng.testproject.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */

public class NotificationBroadcast extends BroadcastReceiver{

    private Context mcontext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mcontext=context;
        String action = intent.getAction();
        String message=intent.getStringExtra("msg");
        if (action.equals("notification")){
            if (isForeground(context)){
                System.out.println("------------------message="+message);
            }else{
                displayNotification(message);
            }
        }
    }
    /**
     * 在顶部显示通知
     * @param message
     */
    private void displayNotification(final String message) {
        Intent notificationIntent = new Intent(mcontext, MainActivity.class);
        notificationIntent.putExtra("content","哈哈哈。。。。。。。。。");
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(mcontext, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(mcontext).setSmallIcon(
                R.mipmap.ic_launcher)
                .setContentTitle("推送标题")
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
    /**
     * 判断App是否在前台，如果是就弹出一个Dialog，否则在通知栏提示
     */
    private static boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : tasks) {
            if (ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance && packageName.equals(appProcess.processName)) {
                return true;
            }
        }
        return false;
    }
}
