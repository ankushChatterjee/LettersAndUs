package com.ankush.lettersandus;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {
    TextView letterShow;
    FloatingActionButton replyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        letterShow = (TextView)findViewById(R.id.letter_show);
        Intent intent = getIntent();
        String letterText = intent.getStringExtra("LETTER_TEXT");
        Typeface font = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        letterShow.setText(letterText);
        letterShow.setTypeface(font);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        replyButton = (FloatingActionButton)findViewById(R.id.replyButton);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent composeIntent = new Intent(ShowActivity.this,ComposeActivity.class);
                startActivity(composeIntent);
            }
        });
    }

}
