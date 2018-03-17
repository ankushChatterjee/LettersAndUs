package com.ankush.lettersandus;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by nEW u on 9/14/2017.
 */

public class FirebaseDbHelper {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseDbHelper(){
        database = FirebaseDatabase.getInstance();
    }
    public void writeNotificationKey(String id, String key){
        myRef = database.getReference("userData/"+id);
        final boolean[] b = new boolean[1];
        myRef.setValue(new UserData(id, key)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.d("LNUS","Error uploading notification key");
                }
                else {
                    Log.d("LNUS","DONE uploading notification key");
                }
            }
        });

    }

    public void writeMessage(Message letter,String pushId){
        String id;
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("u1email@domain.com")){
            id = "u1";
        }
        else {
            id = "u2";
        }
        myRef = database.getReference("messages/"+id+"/"+pushId);
        myRef.setValue(letter).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.d("LNUS","Error writing message");
                }
                else {
                    Log.d("LNUS","DONE error writing message");
                }
            }
        });

    }
}
