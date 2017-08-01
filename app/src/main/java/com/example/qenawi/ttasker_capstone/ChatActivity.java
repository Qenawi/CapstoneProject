package com.example.qenawi.ttasker_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.qenawi.ttasker_capstone.adapterx.ChatAdp;
import com.example.qenawi.ttasker_capstone.contractx.ContractAcc;
import com.example.qenawi.ttasker_capstone.modle.Smsitem;
import com.example.qenawi.ttasker_capstone.modle.UserprojectItem;
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
    ContractAcc acc = new ContractAcc();
    // private FireBaseListener mCallback;
    private ArrayList<Smsitem> data;
    private RecyclerView rv;
    private ChatAdp adapter;
    private String RoomKey;
    private int children_cnt = 0;
    private int Min;   // opjects
    private WhatsAppPanel mBottomPanel;
    private CircleImageView img;
    private TextView status;
    private UserprojectItem Pkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Pkey = (UserprojectItem) getIntent().getExtras().getParcelable(getString(R.string.alpha));
    //    Log.v("hugo", Pkey.getPkey() + " " + Pkey.getPname());
        mBottomPanel = new WhatsAppPanel(this, this, R.color.colorPrimary);
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
        sendmsg(mBottomPanel.getText());
    }

    void getMesseges() {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child(getString(R.string.chat)).child(Pkey.getPkey());
        fdbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                data.add((Smsitem) dataSnapshot.getValue(Smsitem.class));
                adapter.notifyDataSetChanged();
                rv.scrollToPosition(adapter.getItemCount() - 1);
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

    void sendmsg(String msg) {
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
        final DatabaseReference fdbr = fdb.getReference().child(getString(R.string.chat)).child(Pkey.getPkey());
        String pushKey = fdbr.push().getKey();
        Smsitem s = new Smsitem(msg, "camo", localTime, acc.get_username().getName());
        fdbr.child(pushKey).setValue(s);
        notfy(s);

    }

    void notfy(Smsitem s) {
        Intent intent = new Intent(this, NotfyUsers.class);
        intent.putExtra(getString(R.string.smsitem), s);
        intent.putExtra(getString(R.string.userPitem), Pkey);
        startService(intent);
    }
}
