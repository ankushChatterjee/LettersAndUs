package com.ankush.lettersandus;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.solver.SolverVariable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class ComposeActivity extends AppCompatActivity {
    FirebaseDbHelper firebaseDbHelper;
    EditText letterEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseDbHelper = new FirebaseDbHelper();
        String id="",idt="";
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("ac.ankush15@gmail.com")){
            id = "ankush";
            idt = "popo";
        }
        else {
            id = "popo";
            idt = "ankush";
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final String finalId = id;
        final String finalIdt = idt;
        letterEditText = (EditText)findViewById(R.id.letter_text);
        Typeface font = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        letterEditText.setTypeface(font);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date d= new Date();
//               firebaseDbHelper.writeMessage(new Message(finalId, finalIdt,letterEditText.getText().toString(),"home.png","groot"),
//                        d.getTime()+ finalId);
                Intent selectStanp = new Intent(ComposeActivity.this,StampActivity.class);
                selectStanp.putExtra("ID",finalId);
                selectStanp.putExtra("IDT",finalIdt);
                selectStanp.putExtra("LETTER_TEXT",letterEditText.getText().toString());
                selectStanp.putExtra("FAVORITE","groot");
                selectStanp.putExtra("DB_ID",d.getTime()+ finalId);
                finish();
                startActivity(selectStanp);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
