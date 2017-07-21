package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.qenawi.ttasker_capstone.R;

public class JoinProject extends Fragment
{
    private OnFragmentInteractionListener mListener;
    Button JoinP;
    EditText ProjectName;
    public JoinProject()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_join_project, container, false);
        JoinP =(Button)root.findViewById(R.id.joinProject);
        ProjectName=(EditText)root.findViewById(R.id.ProjectNameET);
        JoinP.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View view)
    {
    if(!ProjectName.getText().toString().equals(""))
    {
        mListener.onFragmentInteraction2((String)ProjectName.getText().toString());
    }
    }
});
        return  root;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
            {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
            }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction2(Object uri);
    }
}
