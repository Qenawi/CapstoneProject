package com.example.qenawi.ttasker_capstone.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.qenawi.ttasker_capstone.MainActivity;
import com.example.qenawi.ttasker_capstone.MainActivity3Tasks;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.modle.NotificationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by QEnawi on 7/26/2017.
 * src https://www.codementor.io/sundayakinsete/firebase-real-time-notifications-app-to-app-opkwbo6ba
 */
public class NotificationService extends Service {
    static String TAG = "FirebaseService";
    public FirebaseDatabase mDatabase;
    FirebaseAuth firebaseAuth;
    Context context;
    int cnt1 = 1;
    int imgIcon = R.drawable.common_google_signin_btn_icon_dark;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        setupNotificationListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupNotificationListener() {

        mDatabase.getReference().child("Notification")
                .child(getStoredPair())
                .orderByChild("notfstate").equalTo("0")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot != null) {
                            NotificationItem notification = (NotificationItem) dataSnapshot.getValue(NotificationItem.class);
                            showNotification(context, notification, dataSnapshot.getKey());
                            //   notfy2(context,notification);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        //    Utilities.log("onChildChanged",dataSnapshot);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        //    Utilities.log("onChildRemoved",dataSnapshot);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        //   Utilities.log("onChildMoved",dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        Utilities.log("onCancelled",databaseError);
                    }
                });
    }

    private void showNotification(Context context, NotificationItem notification, String notification_key) {
        if (notification.getNotftaskkey().equals(getStoredPair())) {
            flagNotificationAsSent(notification_key);
            return;
        }
        int cntx = 0;
        Intent backIntent = new Intent(context, MainActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        backIntent.setAction(getString(R.string.resettasks));
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(getString(R.string.resetchat));
        /*  Use the notification type to switch activity to stack on the main activity*/
        if (notification.getNotftype().equals("tasktype")) {
            intent = new Intent(context, MainActivity3Tasks.class);
            cnt1 = 1;
            settasksCnt(getTc() + 1);
            cntx = getTc();
            imgIcon = R.drawable.task2;
            startService(new Intent(getApplicationContext(), UpdateWidget.class));
        } else {
            cnt1 = 2;
            setchatCnt(getCc() + 1);
            cntx = getCc();
            imgIcon = R.drawable.messege;
        }
        //--------------------------------
        final PendingIntent pendingIntent = PendingIntent.getActivities(context, 900,
                new Intent[]{backIntent}, PendingIntent.FLAG_ONE_SHOT);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        //-------------------
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(imgIcon)
                .setContentTitle(notification.getNotftitle())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText(notification.getNotfcontent())
                .setContentInfo(cntx + "")
                .setAutoCancel(true);
        //-----------------------------
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(cnt1, mBuilder.build());
        /* Update firebase set notifcation with this key to 1 so it doesnt get pulled by our notification listener*/
        flagNotificationAsSent(notification_key);
    }

    private void flagNotificationAsSent(String notification_key) {
        mDatabase.getReference().child(getString(R.string.notification)).child(getStoredPair())
                .child(notification_key)
                .child(getString(R.string.notfstate))
                .setValue("1");
    }

    String getStoredPair() {
        String res = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.eta), getString(R.string.N));
        return res;
    }

    int getTc() {
        int res = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.eta7), 0);
        return res;
    }

    int getCc() {
        int res = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.eta7x), 0);
        return res;
    }

    void settasksCnt(int x) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(getString(R.string.eta7), x).apply();
    }

    void setchatCnt(int x) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(getString(R.string.eta7x), x).apply();
    }
}
