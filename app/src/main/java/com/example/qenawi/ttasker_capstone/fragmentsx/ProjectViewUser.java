package com.example.qenawi.ttasker_capstone.fragmentsx;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.ttasker_capstone.ChatActivity;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapterx.ProjectViewUserAdp;
import com.example.qenawi.ttasker_capstone.callbackx.ProjectViewUserL;
import com.example.qenawi.ttasker_capstone.modle.TaskItem;
import com.example.qenawi.ttasker_capstone.modle.UserprojectItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectViewUser extends Fragment implements ProjectViewUserAdp.onClickListner, ProjectViewUserL {
    ArrayList<TaskItem> taskItems;
    ArrayList<String> taskKey = new ArrayList<>();
    RecyclerView rv;
    ProjectViewUserAdp adapter;
    RecyclerView.LayoutManager ly;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton Chat;
    private UserprojectItem Pkey;
    private ProjectViewUserL mCallback = this;
    private int lastFirstVisiblePosition;

    public ProjectViewUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_project_view_user, container, false);
        if (savedInstanceState != null) {
            Log.v("hugo", "restore");
            lastFirstVisiblePosition = savedInstanceState.getInt(getString(R.string.bundleK4));
            taskItems = savedInstanceState.getParcelableArrayList(getString(R.string.bundleK7));
            taskKey = savedInstanceState.getStringArrayList(getString(R.string.bundleK5));
        }
        Chat = (FloatingActionButton) root.findViewById(R.id.floatingActionButton2);
        taskItems = new ArrayList<>();
        Pkey = (UserprojectItem) getActivity().getIntent().getExtras().getParcelable("PKey");//project key
        rv = (RecyclerView) root.findViewById(R.id.project_tasks);
        ly = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(ly);
        adapter = new ProjectViewUserAdp(getContext(), this, taskItems, 0);
        rv.setAdapter(adapter);
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("Alpha", (Parcelable) Pkey);
                startActivity(intent);
            }
        });
        try {
            getMemberTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
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
    public void onPause() {
        lastFirstVisiblePosition = ((LinearLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
        super.onPause();
    }

    @Override
    public void onListItemClick(int Clickpos) {

        changeState(Clickpos);

    }


    private void changeState(int clickpos) {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("userTaskstate").child(getStoredPair()).child(taskKey.get(clickpos));
        final DatabaseReference fdbr2 = fdb.getReference().child("widgetdata").child(getStoredPair()).child(Pkey.getPkey()).child(taskKey.get(clickpos));
        String val = taskItems.get(clickpos).getDone().equals("1") ? "0" : "1";
        taskItems.get(clickpos).setDone(val);
        fdbr.child("state").setValue(taskItems.get(clickpos).getDone()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        fdbr2.child("done").setValue(taskItems.get(clickpos).getDone());

    }

    void getMemberTasks() throws  Exception {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("widgetdata").child(getStoredPair()).child(Pkey.getPkey());
        fdbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TaskItem d = (TaskItem) dataSnapshot1.getValue(TaskItem.class);
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
    public void data_arrived(Object object) {
        adapter.notifyDataSetChanged();
    }

    String getStoredPair() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("eTa", "null");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.bundleK4), lastFirstVisiblePosition);
        outState.putParcelableArrayList(getString(R.string.bundleK7), taskItems);
        outState.putStringArrayList(getString(R.string.bundleK5), taskKey);
        super.onSaveInstanceState(outState);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction4(Uri uri);
    }
}
