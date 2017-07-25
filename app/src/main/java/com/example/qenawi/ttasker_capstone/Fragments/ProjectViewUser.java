package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.ttasker_capstone.CallBack.ProjectView_user;
import com.example.qenawi.ttasker_capstone.ChatActivity;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapters.ProjectViewUserAdp;
import com.example.qenawi.ttasker_capstone.modle.taskItem;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class ProjectViewUser extends Fragment implements ProjectViewUserAdp.onClickListner, ProjectView_user {
    private OnFragmentInteractionListener mListener;
    ArrayList<taskItem> taskItems;
    ArrayList<String>taskKey=new ArrayList<>();
    RecyclerView rv;
    ProjectViewUserAdp adapter;
    RecyclerView.LayoutManager ly;
    private FloatingActionButton Chat;
    private userprojectItem Pkey;
    private ProjectView_user mCallback=this;

    public ProjectViewUser()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_project_view_user, container, false);
        Chat=(FloatingActionButton)root.findViewById(R.id.floatingActionButton2);
        taskItems=new ArrayList<>();
        Pkey =(userprojectItem) getActivity().getIntent().getExtras().getSerializable("PKey");//project key

        rv=(RecyclerView) root.findViewById(R.id.project_tasks);
        ly=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(ly);
        adapter=new ProjectViewUserAdp(getContext(),this,taskItems,0);
        rv.setAdapter(adapter);
        Chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });
        getMemberTasks();
        return  root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onListItemClick(int Clickpos)
    {

     changeState(Clickpos);

    }


    private void changeState(int clickpos)
    {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("userTaskstate").child(getStoredPair()).child(taskKey.get(clickpos));
        final DatabaseReference fdbr2 = fdb.getReference().child("widgetdata").child(getStoredPair()).child(Pkey.getPkey()).child(taskKey.get(clickpos));
        String val=taskItems.get(clickpos).getDone().equals("1")?"0":"1";
        taskItems.get(clickpos).setDone(val);
        fdbr.child("state").setValue(taskItems.get(clickpos).getDone()).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {

            }
        });
        fdbr2.child("done").setValue(taskItems.get(clickpos).getDone());

    }
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction4(Uri uri);
    }
    void getMemberTasks()
    {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("widgetdata").child(getStoredPair()).child(Pkey.getPkey());
        fdbr.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    taskItem d= (taskItem)dataSnapshot1.getValue(taskItem.class);
                    taskItems.add(d);
                    taskKey.add(dataSnapshot1.getKey());
                }
                mCallback.data_arrived(null);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void data_arrived(Object object)
    {
adapter.notifyDataSetChanged();
    }
    String getStoredPair()
    {
        return  PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("eTa","null");
    }
}