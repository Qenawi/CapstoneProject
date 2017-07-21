package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.ttasker_capstone.ChatActivity;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapters.ProjectViewUserAdp;
import com.example.qenawi.ttasker_capstone.modle.taskItem;

import java.util.ArrayList;
public class ProjectViewUser extends Fragment implements ProjectViewUserAdp.onClickListner{
    private OnFragmentInteractionListener mListener;
    ArrayList<taskItem> taskItems;
    RecyclerView rv;
    ProjectViewUserAdp adapter;
    RecyclerView.LayoutManager ly;
    private FloatingActionButton Chat;

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
        taskItems.add(new taskItem("task0","BeGreat","21/7/2017","0"));
        taskItems.add(new taskItem("task1","BeGreat","21/8/2017","1"));
        taskItems.add(new taskItem("task2","BeGreat","21/9/2017","0"));
        taskItems.add(new taskItem("task3","BeGreat","21/10/2017","1"));
        rv=(RecyclerView) root.findViewById(R.id.project_tasks);
        ly=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(ly);
        adapter=new ProjectViewUserAdp(getContext(),this,taskItems,0);
        rv.setAdapter(adapter);
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });
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

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction4(Uri uri);
    }
}
