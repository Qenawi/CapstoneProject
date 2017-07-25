package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qenawi.ttasker_capstone.Contract.ContractDepug;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.modle.projectsitem;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        AddPToFireBase();
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
    //--------------------------------
    void AddPToFireBase()
    {

        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("projects");
        final DatabaseReference fdbr2 = fdb.getReference().child("userproject").child(getStoredPair());
        String pushKey = fdbr.push().getKey();
        String pushKey2 = fdbr2.push().getKey();
        Log.v(ContractDepug.PUBTAG,pushKey);
        fdbr.child(pushKey).setValue(new projectsitem(ProjectName.getText().toString(),getStoredPair(),"e589"));
        fdbr2.child(pushKey2).setValue(new userprojectItem(pushKey,ProjectName.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(getActivity(),"project Created Successfully ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    String getStoredPair()
    {
        return   PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("eTa","null");
    }
}
