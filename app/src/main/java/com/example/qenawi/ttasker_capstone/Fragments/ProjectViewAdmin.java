package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapters.RecyclerViewAdapterMainActivity;

import java.util.ArrayList;

public class ProjectViewAdmin extends Fragment implements RecyclerViewAdapterMainActivity.onClickListner {

    RecyclerViewAdapterMainActivity adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager ly;
    ArrayList<String> data = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public ProjectViewAdmin()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_project_view_admin, container, false);
        rv = (RecyclerView) root.findViewById(R.id.project_team);
        data.add("ahmed");
        data.add("mona");
        data.add("Alaa Omer");
        adapter = new RecyclerViewAdapterMainActivity(getContext(), this, data, 0);
        ly = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(ly);
        rv.setAdapter(adapter);
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
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onListItemClick(int Clickpos)
    {
      mListener.onFragmentInteraction5(data.get(Clickpos));
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction5(Object uri);
    }
}
