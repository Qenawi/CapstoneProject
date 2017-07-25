package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qenawi.ttasker_capstone.CallBack.Data_loadedMyProjects;
import com.example.qenawi.ttasker_capstone.Contract.ContractDepug;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapters.RecyclerViewAdapterMainActivity;
import com.example.qenawi.ttasker_capstone.modle.projectsitem;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyProjects extends Fragment implements  RecyclerViewAdapterMainActivity.onClickListner,Data_loadedMyProjects
{
    RecyclerViewAdapterMainActivity adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager ly;
    ArrayList<String>data=new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private  ArrayList<userprojectItem>datax=new ArrayList<>();
    private Data_loadedMyProjects mCallback;
    public MyProjects()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View root= inflater.inflate(R.layout.fragment_my_projects, container, false);
        mCallback=this;
        rv=(RecyclerView)root.findViewById(R.id.project_list);

        adapter=new RecyclerViewAdapterMainActivity(getContext(),this,data,0);
        ly=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(ly);
        rv.setAdapter(adapter);
        get_UserProjects();
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
//      mListener.onFragmentInteraction3(data.get(Clickpos));
        get_projectData(datax.get(Clickpos));
    }// adapter Call back
    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction3(Object uri,Object uri2);
    }
     void get_UserProjects()
     {
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
        DatabaseReference Fdbr = Fdb.getReference().child("userproject").child(getStoredPair());
        //Query query = Fdbr.g
        Fdbr.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Log.v(ContractDepug.PUBTAG," "+dataSnapshot.getChildrenCount());
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    userprojectItem myPair = dataSnapshot1.getValue(userprojectItem.class);
                    Log.v(ContractDepug.PUBTAG,myPair.getPkey()+ myPair.getPname());
                    data.add(myPair.getPname());
                    datax.add(new userprojectItem(myPair.getPkey(),myPair.getPname()));
                }
                 mCallback.data_arrived("0");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void get_projectData(final userprojectItem projectKey)
    {
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
        DatabaseReference Fdbr = Fdb.getReference().child("projects").child(projectKey.getPkey());

        Fdbr.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot!=null&&dataSnapshot.getChildrenCount()!=0)
                {
                    projectsitem myPair=new projectsitem(dataSnapshot.getChildren().iterator().next().getValue(String.class),dataSnapshot.getChildren().iterator().next().getValue(String.class),dataSnapshot.getChildren().iterator().next().getValue(String.class));
                    Log.v(ContractDepug.PUBTAG,myPair.getAdminKey());
                    mCallback.adminKeyArrived(myPair.getAdminKey(),projectKey);
                    return;
                }
                mCallback.adminKeyArrived(null,null);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
//-----------------------------------------------------------------
    @Override public void data_arrived(Object object)
    {
       adapter.notifyDataSetChanged();
    }
    @Override
    public void adminKeyArrived(Object object,Object object2)
    {
        Toast.makeText(getActivity(),(String)object+" | "+getStoredPair(),Toast.LENGTH_LONG).show();
        if (object==null) mListener.onFragmentInteraction3("0",(userprojectItem)object2);
        else
        {
            String res= getStoredPair().equals((String) object)?"1":"0";
            mListener.onFragmentInteraction3(res,(userprojectItem)object2);
        }
    }
    String getStoredPair()
    {
        return  PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("eTa","null");
    }
}
