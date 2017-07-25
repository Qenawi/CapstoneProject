package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.ttasker_capstone.CallBack.MempertaskViewAdmin;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapters.MempertasksViewAdminAdp;
import com.example.qenawi.ttasker_capstone.modle.pmemberitem;
import com.example.qenawi.ttasker_capstone.modle.taskItem;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class memperTaskViewAdmin extends Fragment implements MempertasksViewAdminAdp.onClickListner,MempertaskViewAdmin {

    private OnFragmentInteractionListener mListener;
    private RecyclerView rv;
    private MempertasksViewAdminAdp adapter;
    private RecyclerView.LayoutManager ly;
    private ArrayList<taskItem> taskItems;
    private FloatingActionButton Add;
    private pmemberitem user;
    private userprojectItem Pkey;
    private  MempertaskViewAdmin mCallback=this;

    public memperTaskViewAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View Root = inflater.inflate(R.layout.fragment_memper_task_view_admin, container, false);
        Add = (FloatingActionButton) Root.findViewById(R.id.floatingActionButton);
        taskItems = new ArrayList<>();
         user = (pmemberitem) getActivity().getIntent().getExtras().getSerializable("member data");
        Pkey =(userprojectItem) getActivity().getIntent().getExtras().getSerializable("project data");
        rv = (RecyclerView) Root.findViewById(R.id.tasks_list);
        ly = new LinearLayoutManager(getContext());
        rv.setLayoutManager(ly);
        adapter = new MempertasksViewAdminAdp(getContext(), this, taskItems, 0);
        rv.setAdapter(adapter);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction6("add");
            }
        });
        getActivity().setTitle(Pkey.getPname());
        getMemberTasks();
        return Root;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onListItemClick(int Clickpos) {
        //do no thing
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction6(Object uri);
    }
    //-----------------------------------
    void getMemberTasks()
    {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("widgetdata").child(user.getKey()).child(Pkey.getPkey());
        fdbr.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    taskItem d= (taskItem)dataSnapshot1.getValue(taskItem.class);
                    taskItems.add(d);
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
}
