package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.qenawi.ttasker_capstone.R;


public class AddTaskAdmin extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText Desc,Title,DateY,DateM,DateD;
    Button Add;
    public AddTaskAdmin()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View Root= inflater.inflate(R.layout.fragment_add_task_admin, container, false);
        Desc=(EditText)Root.findViewById(R.id.editText2);
        DateY=(EditText)Root.findViewById(R.id.editText);
        DateM=(EditText)Root.findViewById(R.id.editText4);
        DateD=(EditText)Root.findViewById(R.id.editText3);
        Title=(EditText)Root.findViewById(R.id.editText5);
        Add=(Button)Root.findViewById(R.id.button2);
        Add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
             // add task to fire base and reset fields
                Desc.setText("");
                DateY.setText("");
                DateM.setText("");
                DateD.setText("");
                Title.setText("");
            }
        });
        return  Root;
    }
    //TODO:Rename method,update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
