package com.example.qenawi.ttasker_capstone;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.qenawi.ttasker_capstone.Fragments.AddTaskAdmin;
import com.example.qenawi.ttasker_capstone.Fragments.memperTaskViewAdmin;

public class MemberTaskViewAdmin extends AppCompatActivity implements memperTaskViewAdmin.OnFragmentInteractionListener,AddTaskAdmin.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_task_view_admin);
        Call_tasks();
    }
    private void Call_tasks() // status
    {
        memperTaskViewAdmin fragment = new memperTaskViewAdmin();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.view_holder, fragment, "tasks_fragment"); //Container -> R.id.contentFragment
        transaction.commit();
    }
    private void Call_add_tasks() // status
    {
        AddTaskAdmin fragment = new AddTaskAdmin();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.view_holder, fragment, "Addtasks_fragment"); //Container -> R.id.contentFragment
        transaction.commit();}
    @Override
    public void onFragmentInteraction6(Object uri)
    {
           Call_add_tasks();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //stack over Flow
        AddTaskAdmin myFragment = (AddTaskAdmin)getSupportFragmentManager().findFragmentByTag("Addtasks_fragment");
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0&&myFragment!=null&&myFragment.isVisible())
        {
            Log.d("CDA", "onKeyDown Called");
            Call_tasks();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}