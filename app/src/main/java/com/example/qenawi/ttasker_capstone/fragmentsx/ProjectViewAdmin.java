package com.example.qenawi.ttasker_capstone.fragmentsx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.ttasker_capstone.callbackx.DataLoadedMyProjectsViewAdminL;
import com.example.qenawi.ttasker_capstone.ChatActivity;
import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.adapterx.AdminViewAdapter;
import com.example.qenawi.ttasker_capstone.modle.Pmemberitem;
import com.example.qenawi.ttasker_capstone.modle.UserprojectItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectViewAdmin extends Fragment implements AdminViewAdapter.onClickListner, DataLoadedMyProjectsViewAdminL {

    AdminViewAdapter adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager ly;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<Pmemberitem> datax = new ArrayList<>();
    FloatingActionButton Chat;
    private OnFragmentInteractionListener mListener;
    private UserprojectItem Pkey;
    private DataLoadedMyProjectsViewAdminL mCallBack;
    private int lastFirstVisiblePosition = 0;

    public ProjectViewAdmin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.adminv_view_share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        shareKey();
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_project_view_admin, container, false);
        if (savedInstanceState != null) {
            data = savedInstanceState.getStringArrayList(getString(R.string.bundleK5));
            datax = savedInstanceState.getParcelableArrayList(getString(R.string.bundleK1));
            lastFirstVisiblePosition = savedInstanceState.getInt(getString(R.string.bundleK4));
        }
        Pkey = (UserprojectItem) getActivity().getIntent().getExtras().getParcelable(getString(R.string.pkey));//project key
        Chat = (FloatingActionButton) root.findViewById(R.id.floatingActionButton2);
        mCallBack = this;
        rv = (RecyclerView) root.findViewById(R.id.project_team);
        adapter = new AdminViewAdapter(getContext(), this, data, 0);
        ly = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(ly);
        rv.setAdapter(adapter);
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(getString(R.string.alpha), Pkey);
                startActivity(intent);
            }
        });
        if (savedInstanceState == null) {
            try {
                get_UserProjects();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                rv.scrollToPosition(lastFirstVisiblePosition);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(int Clickpos) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.memperdata), datax.get(Clickpos));
        bundle.putParcelable(getString(R.string.projectdata), Pkey);
        mListener.onFragmentInteraction5(bundle);
    }

    //----------------------------------
    void get_UserProjects() throws  Exception
    {
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
        Log.v("assasin", Pkey.getPkey());
        DatabaseReference Fdbr = Fdb.getReference().child("pmember").child(Pkey.getPkey());
        //Query query = Fdbr.g
        Fdbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("assasin", dataSnapshot.getChildrenCount() + " ");
                if (dataSnapshot.getChildrenCount() <= 0) {
                    return;
                }
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Pmemberitem myPair = dataSnapshot1.getValue(Pmemberitem.class);
                    Log.v("assasin", myPair.getName());
                    data.add(myPair.getName());
                    datax.add(new Pmemberitem(myPair.getKey(), myPair.getName()));
                }
                mCallBack.data_arrived("0");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPause() {
        lastFirstVisiblePosition = ((LinearLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
        super.onPause();
    }

    @Override
    public void data_arrived(Object object) {
        adapter.notifyDataSetChanged();
    }

    void shareKey() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, Pkey.getPname() + '\n' + "Key : " + Pkey.getPkey());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.bundleK4), lastFirstVisiblePosition);
        outState.putStringArrayList(getString(R.string.bundleK5), data);
        outState.putParcelableArrayList(getString(R.string.bundleK1), datax);
        super.onSaveInstanceState(outState);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction5(Object uri);
    }
}
