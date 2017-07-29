package com.example.qenawi.ttasker_capstone.fragmentsx;

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
import com.example.qenawi.ttasker_capstone.modle.NotificationItem;
import com.example.qenawi.ttasker_capstone.modle.pmemberitem;
import com.example.qenawi.ttasker_capstone.modle.taskItem;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddTaskAdmin extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText Desc,Title,DateY,DateM,DateD;
    Button Add;
    private pmemberitem user;
    private userprojectItem Pkey;

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
        user = (pmemberitem) getActivity().getIntent().getExtras().getParcelable("member data");
        Pkey =(userprojectItem) getActivity().getIntent().getExtras().getParcelable("project data");
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
                addTask();
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
    void addTask()
    {

        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("widgetdata").child(user.getKey()).child(Pkey.getPkey());
      //  final DatabaseReference fdbr2 = fdb.getReference().child("userproject").child(getStoredPair());
        final String pushKey = fdbr.push().getKey();
        //String pushKey2 = fdbr2.push().getKey();
   //     Log.v(ContractDepug.PUBTAG,pushKey);
        setTaskState(pushKey);
        final taskItem item=new taskItem(Title.getText().toString(),Desc.getText().toString(),DateY.getText().toString()+"/"+DateM.getText().toString()+"/"+DateD.getText().toString(),"0", Pkey.getPname());
        fdbr.child(pushKey).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                Desc.setText("");
                DateY.setText("");
                DateM.setText("");
                DateD.setText("");
                Title.setText("");
                notfy(item,pushKey);
            }
        });
    }
    void setTaskState(String taskKey)
    {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("userTaskstate").child(user.getKey()).child(taskKey);
        fdbr.child("state").setValue("0");
    }

    private void notfy(taskItem item, String pushKey)
    {
        FirebaseDatabase fdb=FirebaseDatabase.getInstance();
        DatabaseReference dbr=fdb.getReference().child("Notification").child(user.getKey());
        dbr.child(dbr.push().getKey()).setValue(new NotificationItem("tasktype",item.getTaskDesc(),item.getTaskName(),Pkey.getPkey(),pushKey, "ServerValue.TIMESTAMP","0"));

    }
}
