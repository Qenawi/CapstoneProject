package com.example.qenawi.ttasker_capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.qenawi.ttasker_capstone.Contract.ContractAcc;
import com.example.qenawi.ttasker_capstone.Sign.SignInActivity;
import com.example.qenawi.ttasker_capstone.modle.users_data_modleitem;
import com.example.qenawi.ttasker_capstone.services.NotificationService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        is_user_signed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                // sign in finished
                Toast.makeText(this,"Sign in successfully",Toast.LENGTH_SHORT).show();
                DoExtraWork();
            }
            if (resultCode == Activity.RESULT_CANCELED)
            {
                //Write your code if there's no result
            }
        }
    }
    private void is_user_signed()
    {
        ContractAcc con = new ContractAcc();
        users_data_modleitem mUdm = con.get_username();
        if (mUdm == null)
        {
            // Not signed in, launch the Sign In activity
            startActivityForResult(new Intent(this, SignInActivity.class), 1);
        }
        else
        {
            Toast.makeText(this,"user is oReady signed in",Toast.LENGTH_SHORT).show();
            // Call main Activity Create,Join,myprojects
            DoExtraWork();
        }
        }
        void DoExtraWork()
        {
            // check if shared prefs is empty
            //start service
            if (getIntent().getAction().equals("resetTasks"))
            {
                settasksCnt();
            }
            else if(getIntent().getAction().equals("resetChat"))
            {
                setchatCnt();
            }
            if(getStoredPair().equals("null"))
            {
                ContractAcc acc=new ContractAcc();
                final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
                final DatabaseReference fdbr = fdb.getReference().child("users");
                Query query=fdbr.orderByChild("email").equalTo(acc.get_username().getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            setSTored(dataSnapshot1.getKey().toString() );
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
            startService(new Intent(this, NotificationService.class));
            startActivity(new Intent(this,MainActivity3Tasks.class));
        }
    void setSTored(String e)
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("eTa",e).apply();
    }
    String getStoredPair()
    {
        String res= PreferenceManager.getDefaultSharedPreferences(this).getString("eTa","null");
        return  res;
    }

    void settasksCnt()
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("eTa7",0).apply();
    }
    void setchatCnt()
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("eTa7",0).apply();
    }
    }