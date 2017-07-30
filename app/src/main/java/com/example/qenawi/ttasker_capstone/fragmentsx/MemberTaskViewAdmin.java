package com.example.qenawi.ttasker_capstone.fragmentsx;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.ttasker_capstone.callbackx.MemberTaskViewAdminL;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapterx.MempertasksViewAdminAdp;
import com.example.qenawi.ttasker_capstone.modle.Pmemberitem;
import com.example.qenawi.ttasker_capstone.modle.TaskItem;
import com.example.qenawi.ttasker_capstone.modle.UserprojectItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberTaskViewAdmin extends Fragment implements MempertasksViewAdminAdp.onClickListner, MemberTaskViewAdminL {

    private OnFragmentInteractionListener mListener;
    private RecyclerView rv;
    private MempertasksViewAdminAdp adapter;
    private RecyclerView.LayoutManager ly;
    private ArrayList<TaskItem> taskItems;
    private FloatingActionButton Add;
    private Pmemberitem user;
    private UserprojectItem Pkey;
    private MemberTaskViewAdminL mCallback = this;
    private int lastFirstVisiblePosition = 0;

    public MemberTaskViewAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Root = inflater.inflate(R.layout.fragment_memper_task_view_admin, container, false);
        taskItems = new ArrayList<>();
        if (savedInstanceState != null) {
            Log.v("hugo", "restore");
            lastFirstVisiblePosition = savedInstanceState.getInt(getString(R.string.bundleK4));
            taskItems = savedInstanceState.getParcelableArrayList(getString(R.string.bundleK7));
        }
        Add = (FloatingActionButton) Root.findViewById(R.id.floatingActionButton);
        user = (Pmemberitem) getActivity().getIntent().getExtras().getParcelable("member data");
        Pkey = (UserprojectItem) getActivity().getIntent().getExtras().getParcelable("project data");
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
        if (savedInstanceState != null) {

            try {
                rv.scrollToPosition(lastFirstVisiblePosition);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            getMemberTasks();
        }
        return Root;
    }

    @Override
    public void onPause() {
        lastFirstVisiblePosition = ((LinearLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
        super.onPause();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(int Clickpos) {
        //do no thing
    }

    //-----------------------------------
    void getMemberTasks() {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("widgetdata").child(user.getKey()).child(Pkey.getPkey());
        fdbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TaskItem d = (TaskItem) dataSnapshot1.getValue(TaskItem.class);
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
    public void data_arrived(Object object) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.bundleK4), lastFirstVisiblePosition);
        outState.putParcelableArrayList(getString(R.string.bundleK7), taskItems);
        super.onSaveInstanceState(outState);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction6(Object uri);
    }
}
