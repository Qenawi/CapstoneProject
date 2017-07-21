package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapters.RecyclerViewAdapterMainActivity;

import java.util.ArrayList;

public class MyProjects extends Fragment implements  RecyclerViewAdapterMainActivity.onClickListner
{
    RecyclerViewAdapterMainActivity adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager ly;
    ArrayList<String>data=new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    public MyProjects()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View root= inflater.inflate(R.layout.fragment_my_projects, container, false);
        rv=(RecyclerView)root.findViewById(R.id.project_list);
        data.add("p1");
        data.add("p2");
        data.add("p3");
        adapter=new RecyclerViewAdapterMainActivity(getContext(),this,data,0);
        ly=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(ly);
        rv.setAdapter(adapter);
        return  root;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");}
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onListItemClick(int Clickpos)
    {
        Toast.makeText(getActivity(),data.get(Clickpos),Toast.LENGTH_SHORT).show();
        mListener.onFragmentInteraction3(data.get(Clickpos));
    }// adapter Call back
    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction3(Object uri);
    }
}
