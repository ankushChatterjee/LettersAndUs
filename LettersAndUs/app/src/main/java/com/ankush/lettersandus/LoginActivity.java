package com.ankush.lettersandus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {
    private ImageView popoImage, ankushImage;
    private ViewSwitcher viewSwitcher;
    private TextView titleText;
    private static final int ANKUSH = 1;
    private static final int POPO = 0;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button loginButton;
    private EditText passwordText;
    int selected = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        try {
            firebaseDatabase.setPersistenceEnabled(true);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusLogin));

        setContentView(R.layout.activity_login);
        titleText = (TextView)findViewById(R.id.title_text);

        //myRef.setValue("Hello, World!");

        Typeface font = Typeface.createFromAsset(getAssets(), "Vibur-Regular.ttf");
        titleText.setTypeface(font);

        popoImage = (ImageView)findViewById(R.id.popo_login);
        ankushImage = (ImageView)findViewById(R.id.ankush_login);
        viewSwitcher = (ViewSwitcher)findViewById(R.id.login_flow);

        popoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSwitcher.showNext();
                selected = POPO;
                Log.d("LNUS",selected+"");
            }
        });

        ankushImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSwitcher.showNext();
                selected = ANKUSH;
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(LoginActivity.this,"SIGNED IN",Toast.LENGTH_SHORT).show();
                    Log.d("LNS", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    FirebaseDbHelper firebaseDbHelper = new FirebaseDbHelper();
                    String id,idt;
                    if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("ac.ankush15@gmail.com")){
                        id = "ankush";
                        idt = "popo";
                    }
                    else {
                        id = "popo";
                        idt = "ankush";
                    }
                    firebaseDbHelper.writeNotificationKey(id, FirebaseInstanceId.getInstance().getToken());
                    finish();
                    startActivity(intent);
                } else {
                    // User is signed out
                    Toast.makeText(LoginActivity.this,"SIGNED OUT",Toast.LENGTH_SHORT).show();
                    Log.d("LNS", "onAuthStateChanged:signed_out");
                }
            }
        };

        loginButton = (Button)findViewById(R.id.loginButton);
        passwordText = (EditText)findViewById(R.id.passwordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passwordText.getText().toString();
                if(!password.equals("")){
                    signIn(password);
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    void signIn(String password){
        String email = "";

        if(selected==ANKUSH){
            email = "ac.ankush15@gmail.com";
        }
        else if(selected==POPO){
            email = "shreyadoodles1@gmail.com";
        }
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Signing In");
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LNS", "signInWithEmail:onComplete:" + task.isSuccessful());
                        dialog.hide();
                        dialog.dismiss();
                        if (!task.isSuccessful()) {
                            Log.w("LNS", "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "FAILED",
                                    Toast.LENGTH_SHORT).show();
                        }}
                });
    }
}
