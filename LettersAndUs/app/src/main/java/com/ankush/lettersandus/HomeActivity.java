package com.ankush.lettersandus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference dbRef;
    RecyclerView inboxList;
    RecyclerView.Adapter adapter;
    boolean loaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FirebaseDbHelper firebaseDbHelper = new FirebaseDbHelper();
        final String id,idt;
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("ac.ankush15@gmail.com")){
            id = "ankush";
            idt = "popo";
        }
        else {
            id = "popo";
            idt = "ankush";
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Date d= new Date();
//                firebaseDbHelper.writeMessage(new Message(id,idt,"Hello"+d.getTime(),"home.jpg","groot"),
//                        d.getTime()+id);
                Intent composeIntent = new Intent(HomeActivity.this,ComposeActivity.class);
                startActivity(composeIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d("LNUS",FirebaseInstanceId.getInstance().getToken());
        loaded = false;
        inboxList = (RecyclerView)findViewById(R.id.inboxList);
        inboxList.setLayoutManager(new LinearLayoutManager(this));
        inboxList.setHasFixedSize(true);
        inboxList.setLayoutFrozen(false);
        inboxList.setDrawingCacheEnabled(true);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        dbRef = firebaseDatabase.getReference().child("messages").child(idt);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Sweetness");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("LNUS","data loaded");
                GenericTypeIndicator<HashMap<String,Message>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, Message>>() {
                };
                HashMap<String,Message> frms = dataSnapshot.getValue(genericTypeIndicator);
                ArrayList<Message> frms1= new ArrayList<>();
                if(frms == null){
                    progressDialog.hide();
                    progressDialog.dismiss();
                    return;
                }
                SortedSet<String> keys = new TreeSet<>(frms.keySet());
                for (String key : keys) {
                    Message value = frms.get(key);
                    frms1.add(value);
                }
                progressDialog.hide();
                progressDialog.dismiss();
                Collections.reverse(frms1);
                adapter = new MessageAdapter(frms1,HomeActivity.this);
                inboxList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("BRAND", databaseError.toException());
            }
        };
        dbRef.addValueEventListener(vel);
        firebaseDbHelper.writeNotificationKey(id, FirebaseInstanceId.getInstance().getToken());
        //Toast.makeText(this, FirebaseInstanceId.getInstance().getToken(),Toast.LENGTH_SHORT).show();
    }
    void setLoaded(boolean b){ loaded = b; }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent login = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(login);
                break;
            case R.id.nav_sent_letter:
                Intent sent = new Intent(HomeActivity.this,SentActivity.class);
                startActivity(sent);
                break;
            case R.id.nav_about:
                Intent about = new Intent(HomeActivity.this,AboutActivity.class);
                startActivity(about);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
