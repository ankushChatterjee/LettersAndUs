package com.ankush.lettersandus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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

public class StampActivity extends AppCompatActivity implements AdapterListener {
    DatabaseReference dbRef;
    RecyclerView.Adapter adapter;
    RecyclerView stampGrid;
    String fromID, toId, letterText, stampUrl,favourite,dbId;
    FirebaseDbHelper firebaseDbHelper;
    @Override
    public void sendIntent(String stamp) {
        stampUrl = stamp;
        firebaseDbHelper.writeMessage(new Message(fromID, toId,letterText,stampUrl,favourite),
                dbId);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stampGrid = (RecyclerView)findViewById(R.id.stampGrid);
        stampGrid.setLayoutManager(new GridLayoutManager(this,2));
        firebaseDbHelper = new FirebaseDbHelper();
        //get all intent values
        Intent intent = getIntent();
        fromID = intent.getStringExtra("ID");
        toId = intent.getStringExtra("IDT");
        letterText = intent.getStringExtra("LETTER_TEXT");
        favourite = intent.getStringExtra("FAVORITE");
        dbId = intent.getStringExtra("DB_ID");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference().child("stamps");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Sweetness");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,String>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, String>>() {
                };
                HashMap<String,String> frms = dataSnapshot.getValue(genericTypeIndicator);
                ArrayList<String> frms1= new ArrayList<>();
                SortedSet<String> keys = new TreeSet<>(frms.keySet());
                for (String key : keys) {
                    String value = frms.get(key);
                    frms1.add(value);
                }
                progressDialog.hide();
                progressDialog.dismiss();
                Collections.reverse(frms1);
                adapter = new StampAdapter(frms1,getApplicationContext(),StampActivity.this);
                stampGrid.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("BRAND", databaseError.toException());
            }
        };
        dbRef.addValueEventListener(vel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
