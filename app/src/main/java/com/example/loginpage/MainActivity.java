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

public class MainActivity extends AppCompatActivity {
    EditText emailId,pass;
    Button btnSignUp;
    FirebaseAuth mFirebaseAuth;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailId);
        pass = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(MainActivity.this,"Fields Are Empty!",Toast.LENGTH_LONG).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_LONG).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this,HomeScreen.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this,"Error Occurred!",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Register.class);
                startActivity(i);

            }
        });
    }
}
