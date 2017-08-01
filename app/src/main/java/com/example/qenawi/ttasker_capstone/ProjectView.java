package com.example.qenawi.ttasker_capstone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.qenawi.ttasker_capstone.fragmentsx.ProjectViewAdmin;
import com.example.qenawi.ttasker_capstone.fragmentsx.ProjectViewUser;

public class ProjectView extends AppCompatActivity implements ProjectViewUser.OnFragmentInteractionListener, ProjectViewAdmin.OnFragmentInteractionListener {
    private String pKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);
        //    pKey=(String)getIntent().getExtras().get("PKey");
        if (getIntent().getExtras().get(getString(R.string.acess)).equals("1")) {
            Call_Admin();
        } else {
            Call_User();
        }
    }

    private void Call_Admin() // status
    {
        ProjectViewAdmin fragment = new ProjectViewAdmin();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.view_holder, fragment, getString(R.string.adminfag)); //Container -> R.id.contentFragment
        transaction.commit();
    }

    private void Call_User() // status
    {
        ProjectViewUser fragment = new ProjectViewUser();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.view_holder, fragment, getString(R.string.userfrag)); //Container -> R.id.contentFragment
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction4(Uri uri) {

    }

    @Override
    public void onFragmentInteraction5(Object uri) {
        Intent intent = new Intent(this, MemberTaskViewAdmin.class);
        intent.putExtras((Bundle) uri);
        startActivity(intent);
    }
}
