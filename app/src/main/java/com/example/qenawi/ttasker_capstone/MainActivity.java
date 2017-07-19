package com.example.qenawi.ttasker_capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.qenawi.ttasker_capstone.Contract.ContractAcc;
import com.example.qenawi.ttasker_capstone.Sign.SignInActivity;
import com.example.qenawi.ttasker_capstone.modle.users_data_modle;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        is_user_signed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                // sign in finished
                Toast.makeText(this,"Sign in successfully",Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED)
            {
                //Write your code if there's no result
            }
        }
    }
    private void is_user_signed()
    {
        ContractAcc con = new ContractAcc();
        users_data_modle mUdm = con.get_username();
        if (mUdm == null)
        {
            // Not signed in, launch the Sign In activity
            startActivityForResult(new Intent(this, SignInActivity.class), 1);
        }
        else
        {
            Toast.makeText(this,"user is oReady signed in",Toast.LENGTH_SHORT).show();
            // Call main Activity Create,Join,myprojects
        }
        }

    }