package com.ankush.lettersandus;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by nEW u on 9/13/2017.
 */

public class LnUsFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        FirebaseDbHelper firebaseDbHelper = new FirebaseDbHelper();
        String id=null,idt=null;
        if(FirebaseAuth.getInstance().getCurrentUser()==null) return;
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("u1email@domain.com")){
            id = "u1";
            idt = "u2";
        }
        else {
            id = "u1";
            idt = "u2";
        }
        if(id==null) return;
        firebaseDbHelper.writeNotificationKey(id, FirebaseInstanceId.getInstance().getToken());
    }
}
