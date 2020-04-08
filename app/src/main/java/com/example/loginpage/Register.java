package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    EditText emailId,pass;
    Button btnSignIn;
    FirebaseAuth mFirebaseAuth;
    Button btn1signUp;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailId);
        pass = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login);
        btn1signUp = findViewById(R.id.register);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

                if(mFirebaseUser != null){
                    Toast.makeText(Register.this,"You Are logged in", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Register.this,HomeScreen.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(Register.this,"Please Login/SignUp", Toast.LENGTH_LONG).show();
                }

            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = emailId.getText().toString();
                String pwd = pass.getText().toString();
                if (email.isEmpty()){
                    emailId.setError("Please Provide EmailId");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    pass.setError("Please Provide Password");
                    pass.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(Register.this,"Fields Are Empty!",Toast.LENGTH_LONG).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()){
                                Toast.makeText(Register.this,"Login Error, Please Try Again",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Intent intToHome = new Intent(Register.this,HomeScreen.class);
                                startActivity(intToHome);
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(Register.this,"Error Occurred!",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn1signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(Register.this,MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
