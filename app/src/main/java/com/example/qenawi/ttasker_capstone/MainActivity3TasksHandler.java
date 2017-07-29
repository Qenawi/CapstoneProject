package com.example.qenawi.ttasker_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.qenawi.ttasker_capstone.Fragments.CreateProject;
import com.example.qenawi.ttasker_capstone.Fragments.JoinProject;
import com.example.qenawi.ttasker_capstone.Fragments.MyProjects;
import com.example.qenawi.ttasker_capstone.idlelingResource.SimpleIdlingResource;
import com.example.qenawi.ttasker_capstone.modle.userprojectItem;

public class MainActivity3TasksHandler extends AppCompatActivity  implements CreateProject.OnFragmentInteractionListener,JoinProject.OnFragmentInteractionListener,MyProjects.OnFragmentInteractionListener
{    @Nullable
private SimpleIdlingResource simpleIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3_tasks_handler);
       if( getIntent().getExtras().getString("target").equals("1"))
       {Call_Create();}
       else if( getIntent().getExtras().getString("target").equals("2"))
       {Call_join();}
       else{Call_MyProjects();}
    }
    @Override
    public void onFragmentInteraction(Object uri)
    {

    }// create
    @Override
    public void onFragmentInteraction2(Object uri)
    {

    }//join
    @Override
    public void onFragmentInteraction3(Object uri,Object uri2)
    {
        Intent i=new Intent(this,ProjectView.class);
        i.putExtra("Access",(String) uri);
        i.putExtra("PKey",(userprojectItem)uri2);
        startActivity(i);
    }//My_Projects

    @Override
    public void espressoTest()
    {
    if(simpleIdlingResource!=null)
        simpleIdlingResource.setIdleState(true);
    }

    //--Frags Calls
 private void Call_Create() // status
    {
     CreateProject fragment = new CreateProject();
     FragmentManager fm = getSupportFragmentManager();
     FragmentTransaction transaction = fm.beginTransaction();
     transaction.replace(R.id.view_holder, fragment, "create_frag"); //Container -> R.id.contentFragment
     transaction.commit();
    }
    private void Call_join() // status
    {
        JoinProject fragment = new JoinProject();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.view_holder, fragment, "join_frag"); //Container -> R.id.contentFragment
        transaction.commit();
    }
    private void Call_MyProjects()//status
    {
        MyProjects fragment = new MyProjects();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.view_holder, fragment, "MyProjects"); //Container -> R.id.contentFragment
        transaction.commit();
    }



    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource()
    {
        if (simpleIdlingResource == null)
        {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;
    }


    }
