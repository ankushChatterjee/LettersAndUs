package com.ankush.lettersandus;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class SentActivity extends AppCompatActivity {
    RecyclerView sentList;
    DatabaseReference dbRef;
    RecyclerView.Adapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LNUS","sentactivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String id="",idt="";
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("ac.ankush15@gmail.com")){
            id = "ankush";
            idt = "popo";
        }
        else {
            id = "popo";
            idt = "ankush";
        }

        sentList = (RecyclerView)findViewById(R.id.sentList);
        sentList.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference().child("messages").child(id);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Sweetness");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,Message>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, Message>>() {
                };
                HashMap<String,Message> frms = dataSnapshot.getValue(genericTypeIndicator);
                if(frms==null){
                    progressDialog.hide();
                    progressDialog.dismiss();
                    return;
                }
                ArrayList<Message> frms1= new ArrayList<>();
                SortedSet<String> keys = new TreeSet<>(frms.keySet());
                for (String key : keys) {
                    Message value = frms.get(key);
                    frms1.add(value);
                }
                progressDialog.hide();
                progressDialog.dismiss();
                Collections.reverse(frms1);
                adapter = new MessageAdapter(frms1,SentActivity.this);
                sentList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LNUS", databaseError.toException());
            }
        };
        dbRef.addValueEventListener(vel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
