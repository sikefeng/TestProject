//package com.example.sikefeng.testproject.notificationdemo;
//
//import android.app.ActivityManager;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.app.NotificationCompat;
//import android.util.Log;
//
//import com.example.sikefeng.testproject.MainActivity;
//import com.example.sikefeng.testproject.R;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2017/7/1.
// */
//
//public class PushListenerService extends Service {
//
//    private static final String LOG_TAG = PushListenerService.class.getSimpleName();
//
//    // Intent action used in local broadcast
//    public static final String ACTION_SNS_NOTIFICATION = "sns-notification";
//    // Intent keys
//    public static final String INTENT_SNS_NOTIFICATION_FROM = "from";
//    public static final String INTENT_SNS_NOTIFICATION_DATA = "data";
//
//    /**
//     * Helper method to extract SNS message from bundle.
//     *
//     * @param data bundle
//     * @return message string from SNS push notification
//     */
//    public static String getMessage(Bundle data) {
//        // If a push notification is sent as plain text, then the message appears in "default".
//        // Otherwise it's in the "message" for JSON format.
//        return data.containsKey("default") ? data.getString("default") : data.getString(
//                "message", "");
//    }
//
//    /**
//     * 判断App是否在前台，如果是就弹出一个Dialog，否则在通知栏提示
//     */
//    private static boolean isForeground(Context context) {
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
//
//        final String packageName = context.getPackageName();
//        for (ActivityManager.RunningAppProcessInfo appProcess : tasks) {
//            if (ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance
//                    && packageName.equals(appProcess.processName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 在顶部显示通知
//     * @param message
//     */
//    private void displayNotification(final String message) {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        int requestID = (int) System.currentTimeMillis();
//        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        // Display a notification with an icon, message as content, and default sound. It also
//        // opens the app when the notification is clicked.
//        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this).setSmallIcon(
//                R.mipmap.ic_launcher)
//                .setContentTitle("推送标题")
//                .setContentText(message)
//                .setDefaults(Notification.DEFAULT_SOUND)
//                .setAutoCancel(true)
//                .setContentIntent(contentIntent);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(
//                Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, builder.build());
//    }
//
//    private void broadcast(final String from, final Bundle data) {
//        Intent intent = new Intent(ACTION_SNS_NOTIFICATION);
//        intent.putExtra(INTENT_SNS_NOTIFICATION_FROM, from);
//        intent.putExtra(INTENT_SNS_NOTIFICATION_DATA, data);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//    }
//
//    /**
//     *当接收到推送消息时  Todo 这里处理逻辑
//     */
//    @Override
//    public void onMessageReceived(final String from, final Bundle data) {
//        String message = getMessage(data);
//        Log.d(LOG_TAG, "From: " + from);
//        Log.d(LOG_TAG, "Message: " + message);
//        displayNotification(message);
////        if (isForeground(this)) {
////            // broadcast notification
////            broadcast(from, data);
////        } else {
////            displayNotification(message);
////        }
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
