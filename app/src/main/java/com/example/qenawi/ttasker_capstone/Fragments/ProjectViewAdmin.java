package com.example.qenawi.ttasker_capstone.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.ttasker_capstone.CallBack.Data_loadedMyProjectsViewAdmin;
import com.example.qenawi.ttasker_capstone.ChatActivity;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapters.RecyclerViewAdapterMainActivity;
import com.example.qenawi.ttasker_capstone.modle.pmemberitem;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectViewAdmin extends Fragment implements RecyclerViewAdapterMainActivity.onClickListner,Data_loadedMyProjectsViewAdmin {

    RecyclerViewAdapterMainActivity adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager ly;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<pmemberitem> datax = new ArrayList<>();
    FloatingActionButton Chat;
    private OnFragmentInteractionListener mListener;
    private userprojectItem Pkey;
    private  Data_loadedMyProjectsViewAdmin mCallBack;
    public ProjectViewAdmin()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_project_view_admin, container, false);
        Pkey =(userprojectItem) getActivity().getIntent().getExtras().getSerializable("PKey");//project key
        Chat=(FloatingActionButton)root.findViewById(R.id.floatingActionButton2);
        mCallBack=this;
        rv = (RecyclerView) root.findViewById(R.id.project_team);
        adapter = new RecyclerViewAdapterMainActivity(getContext(), this, data, 0);
        ly = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(ly);
        rv.setAdapter(adapter);
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("Alpha",Pkey);
                startActivity(intent);
            }
        });
        get_UserProjects();
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
       Bundle bundle=new Bundle();
        bundle.putSerializable("member data",datax.get(Clickpos));
        bundle.putSerializable("project data",Pkey);
      mListener.onFragmentInteraction5(bundle);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction5(Object uri);
    }
    //----------------------------------
    void get_UserProjects()
    {
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
        Log.v("assasin",Pkey.getPkey());
        DatabaseReference Fdbr = Fdb.getReference().child("pmember").child(Pkey.getPkey());
        //Query query = Fdbr.g
        Fdbr.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Log.v("assasin",dataSnapshot.getChildrenCount()+" ");
                if (dataSnapshot.getChildrenCount()<=0){return;}
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    pmemberitem myPair = dataSnapshot1.getValue(pmemberitem.class);
                    Log.v("assasin",myPair.getName());
                    data.add(myPair.getName());
                    datax.add(new pmemberitem(myPair.getKey(),myPair.getName()));
                }
                mCallBack.data_arrived("0");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void data_arrived(Object object)
    {
        adapter.notifyDataSetChanged();
    }
}
