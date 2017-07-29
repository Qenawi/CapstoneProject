package com.example.qenawi.ttasker_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.qenawi.ttasker_capstone.Contract.ContractAcc;
import com.example.qenawi.ttasker_capstone.adapters.ChatAdp;
import com.example.qenawi.ttasker_capstone.modle.smsitem;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;
import com.example.qenawi.ttasker_capstone.services.NotfyUsers;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import br.com.instachat.emojilibrary.controller.WhatsAppPanel;
import br.com.instachat.emojilibrary.model.layout.EmojiCompatActivity;
import br.com.instachat.emojilibrary.model.layout.WhatsAppPanelEventListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends EmojiCompatActivity implements WhatsAppPanelEventListener {
 // private FireBaseListener mCallback;
    private ArrayList<smsitem> data;
    private RecyclerView rv;
    private ChatAdp adapter;
    private String RoomKey;
    private int children_cnt = 0;
    private int Min;   // opjects
    private WhatsAppPanel mBottomPanel;
    private CircleImageView img;
    private TextView status;
    private userprojectItem Pkey;
    ContractAcc acc = new ContractAcc();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Pkey=(userprojectItem)getIntent().getExtras().getParcelable("Alpha");
        mBottomPanel=new WhatsAppPanel(this,this,R.color.colorPrimary);
        data = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.chat);
        adapter = new ChatAdp(data, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        getMesseges();
    }
    @Override
    public void onSendClicked()
    {
     //   data.add(new smsitem(mBottomPanel.getText(),null,"21/7/2017", acc.get_username().getEmail()));
      //  adapter.notifyDataSetChanged();
       // rv.scrollToPosition(adapter.getItemCount()-1);
       // sendNotificationToUser("huf","Hal");
       // FirebaseMessaging.getInstance().subscribeToTopic("project1");
        sendmsg(mBottomPanel.getText());
    }
    void getMesseges()
    {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("chat").child(Pkey.getPkey());
            fdbr.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                data.add((smsitem)dataSnapshot.getValue(smsitem.class));
                adapter.notifyDataSetChanged();
                rv.scrollToPosition(adapter.getItemCount()-1);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void sendmsg(String msg)
    {
        ContractAcc acc = new ContractAcc();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+12:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yy:MM:dd:HH:mm:ss a");
        DateFormat date2 = new SimpleDateFormat("HH:mm a");
        DateFormat date3 = new SimpleDateFormat("MM:dd");
// you can get seconds by adding  "...:ss" to it
        String localTime = date.format(currentLocalTime);
        String localTime2 = date2.format(currentLocalTime);
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("chat").child(Pkey.getPkey());
        String pushKey = fdbr.push().getKey();
        smsitem s=new smsitem(msg,"camo",localTime, acc.get_username().getName());
        fdbr.child(pushKey).setValue(s);
      notfy(s);

    }
    void notfy(smsitem s)
    {
        Intent intent=new Intent(this,NotfyUsers.class);
        intent.putExtra("smsitem",s);
        intent.putExtra("userprojectItem",Pkey);
        startService(intent);
    }
}
