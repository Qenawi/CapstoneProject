package com.example.qenawi.ttasker_capstone;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.qenawi.ttasker_capstone.Contract.ContractAcc;
import com.example.qenawi.ttasker_capstone.adapters.ChatAdp;
import com.example.qenawi.ttasker_capstone.modle.smsitem;

import java.util.ArrayList;

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
    private int Min;    // opjects
    private WhatsAppPanel mBottomPanel;
    private CircleImageView img;
    private TextView status;
    ContractAcc acc = new ContractAcc();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mBottomPanel=new WhatsAppPanel(this,this,R.color.colorPrimary);
        data = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.chat);
        adapter = new ChatAdp(data, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
    @Override
    public void onSendClicked()
    {
        data.add(new smsitem(mBottomPanel.getText(),null,"21/7/2017", acc.get_username().getEmail()));
        adapter.notifyDataSetChanged();
        rv.scrollToPosition(adapter.getItemCount()-1);
    }
}
