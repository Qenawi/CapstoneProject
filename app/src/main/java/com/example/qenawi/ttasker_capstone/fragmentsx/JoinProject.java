package com.example.qenawi.ttasker_capstone.fragmentsx;

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

import com.example.qenawi.ttasker_capstone.contractx.ContractAcc;
import com.example.qenawi.ttasker_capstone.contractx.ContractDepug;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.modle.pmemberitem;
import com.example.qenawi.ttasker_capstone.modle.projectsitem;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinProject extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Button JoinP;
    private EditText ProjectKey;
    private String pName;

    public JoinProject() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_join_project, container, false);
        JoinP = (Button) root.findViewById(R.id.joinProject);
        ProjectKey = (EditText) root.findViewById(R.id.ProjectNameET);
        JoinP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ProjectKey.getText().toString().equals("")) {

                    getPname();
                }
            }
        });
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction2(Object uri);
    }

    //-------------------------------------------------------------
    void JoinProject(String pkey)
    {

        ContractAcc acc = new ContractAcc();
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("pmember").child(pkey);
        final DatabaseReference fdbr2 = fdb.getReference().child("userproject").child(getStoredPair());
        String pushKey = fdbr.push().getKey();
        String pushKey2 = fdbr2.push().getKey();
        fdbr.child(pushKey).setValue(new pmemberitem(getStoredPair(), acc.get_username().getName()));

        fdbr2.child(pushKey2).setValue(new userprojectItem(pkey, pName))
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mListener.onFragmentInteraction2((String) ProjectKey.getText().toString());
                    }
                });
    }

    String getStoredPair() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("eTa", "null");
    }

    void getPname()
    {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("projects").child(ProjectKey.getText().toString());
        fdbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.getChildrenCount() <= 0)
                {
                    if (getActivity()!=null)
                  Toast.makeText(getActivity(), "no such project", Toast.LENGTH_SHORT).show();
                } else {

                    projectsitem pit = (projectsitem)dataSnapshot.getValue(projectsitem.class);
                    pName = pit.getPname();
                    Log.v(ContractDepug.PUBTAG,pit.getAdminKey()+" "+pit.getPname()+" "+pit.getChatroomKey());
                    JoinProject(ProjectKey.getText().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}