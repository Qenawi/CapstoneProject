package com.example.qenawi.ttasker_capstone;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qenawi.ttasker_capstone.Contract.ContractAcc;
import com.example.qenawi.ttasker_capstone.Sign.SignInActivity;
import com.example.qenawi.ttasker_capstone.modle.taskItem;
import com.example.qenawi.ttasker_capstone.modle.users_data_modleitem;
import com.example.qenawi.ttasker_capstone.provider.ContractProvider;
import com.example.qenawi.ttasker_capstone.services.NotificationService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<taskItem> sync = new ArrayList<>();
    ArrayList<Pair<String, String>> pkey_taskkey = new ArrayList<>();
    TextView textView;
    private ProgressBar PG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView5);
        PG=(ProgressBar)findViewById(R.id.progressBar2);
        textView.setText("signing in.....");
        PG.setVisibility(View.VISIBLE);
        is_user_signed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // sign in finished
    //            Toast.makeText(this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                //   DoExtraWork();
                test();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void is_user_signed() {
        ContractAcc con = new ContractAcc();
        users_data_modleitem mUdm = con.get_username();
        if (mUdm == null) {
            // Not signed in, launch the Sign In activity
            startActivityForResult(new Intent(this, SignInActivity.class), 1);
        } else {
            Toast.makeText(this, "user is oReady signed in", Toast.LENGTH_SHORT).show();
            // Call main Activity Create,Join,myprojects
            //   DoExtraWork();
            test();
        }
    }

    void test()
    {
        textView.setText("Syncing.....");
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference fdbr = fdb.getReference().child("widgetdata").child(getStoredPair());
        fdbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Log.v("HERO", dataSnapshot.getChildrenCount() + "  A0");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.v("HERO", "project Key" + dataSnapshot1.getKey());
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        Log.v("HERO", "task key " + dataSnapshot2.getKey());
                        taskItem item = (taskItem) dataSnapshot2.getValue(taskItem.class);
                        Pair<String, String> pair = new Pair<>(dataSnapshot1.getKey(), dataSnapshot2.getKey());
                        pkey_taskkey.add(pair);
                        sync.add(item);
                        Log.v("HERO", item.getName());
                    }
                }
                Sync();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void Sync() {
        clean_add();

    }

    void DoExtraWork()
    {

        // check if shared prefs is empty
        //start service
        if (getIntent().getAction().equals("resetTasks")) {
            settasksCnt();
        } else if (getIntent().getAction().equals("resetChat")) {
            setchatCnt();
        }
        if (getStoredPair().equals("null"))
        {
            ContractAcc acc = new ContractAcc();
            final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
            final DatabaseReference fdbr = fdb.getReference().child("users");
            Query query = fdbr.orderByChild("email").equalTo(acc.get_username().getEmail());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        setSTored(dataSnapshot1.getKey().toString());
                    }
                    lunch();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        lunch();
    }

    void lunch()
    {
        PG.setEnabled(false);
        PG.setVisibility(View.GONE);
        startService(new Intent(this, NotificationService.class));
        startActivity(new Intent(this, MainActivity3Tasks.class));
    }

    void setSTored(String e) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("eTa", e).apply();
    }

    String getStoredPair() {
        String res = PreferenceManager.getDefaultSharedPreferences(this).getString("eTa", "null");
        return res;
    }

    void settasksCnt() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("eTa7", 0).apply();
    }

    void setchatCnt() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("eTa7", 0).apply();
    }

    @Override
    protected void onDestroy()
    {
        UpdateWedgie();
        super.onDestroy();
    }

    void clean_add() {
        //    get();
        getContentResolver().delete(ContractProvider.CONTENT_URI, "dummy", null);
        //   get();
        add_to_dp();
    }

    void add_to_dp() {
        //    addH(recipeItems.get(pos).getName());
        for (int i = 0; i < sync.size(); i++) {
            add(sync.get(i), pkey_taskkey.get(i));
        }
        DoExtraWork();
    }

    public void add(taskItem x, Pair<String, String> pktk) {
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

        Intent i = new Intent(this, MyTasksAppWidget.class);
        i.setAction(getString(R.string.Action0));
        sendBroadcast(i);
    }

}