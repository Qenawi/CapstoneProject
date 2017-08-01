package com.example.qenawi.ttasker_capstone.services;

import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.Log;

import com.example.qenawi.ttasker_capstone.MyTasksAppWidget;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.modle.TaskItem;
import com.example.qenawi.ttasker_capstone.provider.ContractProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by QEnawi on 8/1/2017.
 */

public class UpdateWidget extends MyBaseTaskService {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        taskStarted();
        Log.v("helix", "initiate Air Patrol");
        try {
            test();
        } catch (Exception ignored) {
        }

        return START_REDELIVER_INTENT;
    }

    void test() {
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference fdbr = fdb.getReference().child(getString(R.string.widgetdata)).child(getStoredPair());
        fdbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Pair<String, String>> pkey_taskkey = new ArrayList<>();
                ArrayList<TaskItem> sync = new ArrayList<TaskItem>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        TaskItem item = (TaskItem) dataSnapshot2.getValue(TaskItem.class);
                        Pair<String, String> pair = new Pair<>(dataSnapshot1.getKey(), dataSnapshot2.getKey());
                        pkey_taskkey.add(pair);
                        sync.add(item);
                    }
                }
                Sync(pkey_taskkey, sync);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void Sync(ArrayList<Pair<String, String>> pkey_taskkey, ArrayList<TaskItem> sync) {
        clean_add(pkey_taskkey, sync);

    }

    void clean_add(ArrayList<Pair<String, String>> pkey_taskkey, ArrayList<TaskItem> sync) {
        getContentResolver().delete(ContractProvider.CONTENT_URI, getString(R.string.dum), null);
        add_to_dp(pkey_taskkey, sync);
    }

    void add_to_dp(ArrayList<Pair<String, String>> pkey_taskkey, ArrayList<TaskItem> sync) {
        //    addH(recipeItems.get(pos).getName());
        for (int i = 0; i < sync.size(); i++) {
            add(sync.get(i), pkey_taskkey.get(i));
        }
        UpdateWedgie();
    }

    public void add(TaskItem x, Pair<String, String> pktk) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractProvider.TaskTitle, x.getTaskName());
        contentValues.put(ContractProvider.TaskDate, x.getDate());
        contentValues.put(ContractProvider.TaskContent, x.getTaskDesc());
        contentValues.put(ContractProvider.Taskstate, x.getDone());
        contentValues.put(ContractProvider.TaskUey, pktk.second);
        contentValues.put(ContractProvider.ProjectKey, pktk.first);
        contentValues.put(ContractProvider.ProjectName, x.getName());
        contentValues.put(ContractProvider.Dummy, "dummy");
        getContentResolver().insert(ContractProvider.CONTENT_URI, contentValues);
    }

    void UpdateWedgie() {
        Log.v("helix", "Update Widget");
        Intent i = new Intent(getApplicationContext(), MyTasksAppWidget.class);
        i.setAction(getString(R.string.Action0));
        sendBroadcast(i);
        taskCompleted();
    }

    String getStoredPair() {
        String res = PreferenceManager.getDefaultSharedPreferences(this).getString("eTa", "null");
        return res;
    }
}
