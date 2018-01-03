package com.example.shahidkhan.backupappver2.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shahidkhan.backupappver2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import static com.example.shahidkhan.backupappver2.R.string.email;

/**
 * Created by shahidkhan on 12/17/2017.
 */

// reference for firebase
//https://firebase.google.com/docs/android/setup?authuser=0

public class Login  extends AppCompatActivity{
    private ProgressDialog progressDialog;
    private static final String TAG ="TAG" ;
    private EditText email_login, password_login;
    private Button login, signup;
    private FirebaseAuth mAuth;
    private String password;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_gradkb);
        //setTitle("Login");

        progressDialog = new ProgressDialog(this);

        email_login = (EditText) findViewById(R.id.id_username);
        password_login = (EditText) findViewById(R.id.id_user_pass);
        login = (Button) findViewById(R.id.id_btn_login);
        signup = (Button) findViewById(R.id.id_btn_signup);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            //startActivity(new Intent(Login.this, MainActivity.class));
            //finish();
            Log.i("user","User is not login");
        }
        else {

        }
        Log.i("curuserrrr","u- "+user);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignupActivity.class));
            }
        });

        // login code for user
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_login.getText().toString();
                password = password_login.getText().toString();
                Log.i(TAG," email is "+email);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Address! ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password! ", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Logging.......");
               // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Log.i(TAG,"login success ");
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("key",email);
                            startActivity(intent);
                            Log.i(TAG,"after intent ");
//                                    finish();
                        }
                        else
                        {
                            Log.i(TAG,"login failed FB");
                                   // code androidhive
                                    if (password.length() < 6) {
                                        password_login.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(Login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
       // startActivity(new Intent(Login.this,Login.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }
}
