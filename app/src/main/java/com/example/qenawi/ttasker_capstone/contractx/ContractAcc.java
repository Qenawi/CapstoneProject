package com.example.qenawi.ttasker_capstone.contractx;

import com.example.qenawi.ttasker_capstone.modle.Usersdataitem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by QEnawi on 4/11/2017.
 */

public class ContractAcc {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public ContractAcc() {
    }

    public Usersdataitem get_username() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) return null;
        String mFireBaseImg = "NoPhoto";
        try {
            if (mFirebaseUser.getPhotoUrl() != null) {
                mFireBaseImg = mFirebaseUser.getPhotoUrl().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usersdataitem(mFirebaseUser.getEmail(), mFirebaseUser.getDisplayName(), mFireBaseImg);
    }
}
