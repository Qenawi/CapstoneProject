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
public class CreateProject extends Fragment
{
    private OnFragmentInteractionListener mListener;
    Button CreatP;
    EditText ProjectName;
    public CreateProject()
    {// Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_create_project, container, false);
        CreatP=(Button)root.findViewById(R.id.CreateProject);
        ProjectName=(EditText)root.findViewById(R.id.ProjectNameET);
CreatP.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View view)
    {
    if(!ProjectName.getText().toString().equals(""))
    {
        mListener.onFragmentInteraction((String)ProjectName.getText().toString());
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
        void onFragmentInteraction(Object uri);
    }
}
