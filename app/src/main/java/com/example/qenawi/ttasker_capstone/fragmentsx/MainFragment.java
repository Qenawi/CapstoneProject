package com.example.qenawi.ttasker_capstone.fragmentsx;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qenawi.ttasker_capstone.R;
public class MainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Button creat,join,myP;
    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root;
        root= inflater.inflate(R.layout.fragment_main, container, false);
        creat=(Button) root.findViewById(R.id.creat);
        join=(Button) root.findViewById(R.id.join);
        myP=(Button) root.findViewById(R.id.my_projects);
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
            mListener.onFragmentInteraction((String)"create");
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mListener.onFragmentInteraction((String)"join");

            }
        });
        myP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mListener.onFragmentInteraction((String)"MYP");
            }
        });
        return  root;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Object uri);
    }
}
