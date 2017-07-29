package com.example.qenawi.ttasker_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.qenawi.ttasker_capstone.fragmentsx.MainFragment;

public class MainActivity3Tasks extends AppCompatActivity  implements MainFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3_tasks);
    }
    @Override
    public void onFragmentInteraction(Object uri) //MainFragment
    {
        switch ((String)uri)
        {
            case "create":
                Toast.makeText(this,"create",Toast.LENGTH_LONG).show();call("1"); break;
            case "join":
                Toast.makeText(this,"join",Toast.LENGTH_LONG).show();call("2");
                break;
            case "MYP":
                Toast.makeText(this,"MYP",Toast.LENGTH_LONG).show();call("3");
                break;
            default:break;
        }

    }
    void call(String E)
    {
        Intent intent=new Intent(this,MainActivity3TasksHandler.class);
        intent.putExtra("target",E);
        startActivity(intent);
    }
}
