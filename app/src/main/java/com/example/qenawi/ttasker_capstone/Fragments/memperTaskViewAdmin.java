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

import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapters.MempertasksViewAdminAdp;
import com.example.qenawi.ttasker_capstone.modle.taskItem;

import java.util.ArrayList;

public class memperTaskViewAdmin extends Fragment  implements MempertasksViewAdminAdp.onClickListner{

    private OnFragmentInteractionListener mListener;
    RecyclerView rv;
    MempertasksViewAdminAdp adapter;
    RecyclerView.LayoutManager ly;
    ArrayList<taskItem> taskItems;
    FloatingActionButton Add;
    public memperTaskViewAdmin()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View Root= inflater.inflate(R.layout.fragment_memper_task_view_admin, container, false);
        Add=(FloatingActionButton)Root.findViewById(R.id.floatingActionButton);
        taskItems=new ArrayList<>();
        taskItems.add(new taskItem("task0","BeGreat","21/7/2017","0"));
        taskItems.add(new taskItem("task1","BeGreat","21/8/2017","1"));
        taskItems.add(new taskItem("task2","BeGreat","21/9/2017","0"));
        taskItems.add(new taskItem("task3","BeGreat","21/10/2017","1"));
          rv=(RecyclerView)Root.findViewById(R.id.tasks_list);
          ly=new LinearLayoutManager(getContext());
        rv.setLayoutManager(ly);
        adapter=new MempertasksViewAdminAdp(getContext(),this,taskItems,0);
        rv.setAdapter(adapter);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mListener.onFragmentInteraction6("add");
            }
        });
        return  Root;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
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
      //do no thing
    }
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction6(Object uri);
    }
    //-----------------------------------

}
