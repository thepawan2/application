package com.example.newappwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    TextView textView;
    TextInputEditText Email,inputpassword,inputcompassword,Name,Phone;
    Button Singup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ImageView btngoogle;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        textView =findViewById(R.id.pglogin);
        Email = (TextInputEditText) findViewById(R.id.userEmail);
        inputpassword = findViewById(R.id.newPassword);
        inputcompassword = findViewById(R.id.coPassword);
        Name= findViewById(R.id.name1);
        Phone = findViewById(R.id.phoneno);
        Singup = findViewById(R.id.singup);
        btngoogle = findViewById(R.id.Google);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, Googlelogin.class);
                startActivity(intent);
            }
        });

        Singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfromAuth();
            }
        });

    }
    private void PerfromAuth() {
        String email=Email.getText().toString();
        String password=inputpassword.getText().toString();
        String compassword=inputcompassword.getText().toString();
        String name = Name.getText().toString();
        String phone = Phone.getText().toString();

        if(name.isEmpty()){
            Name.setError("Enter your name");
        } else if (phone.isEmpty() || phone.length()!=10) {
            Phone.setError("Enter your Phone no.");
        } else if (!email.matches(emailPattern)) {
            Email.setError("Enter Correct Email");
        } else if(password.isEmpty() || password.length()<6){
            inputpassword.setError("Enter the Password and more the 6 word");
        }else if(!compassword.matches(password)){
            inputcompassword.setError("Miss Match the password");
        }else {
            progressDialog.setMessage("Please wait while Register...");
            progressDialog.setTitle("Register");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUesrtonextactivity();
                        firebaseFirestore.collection("User")
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(new UserModel(name,email,phone,password));
                        Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Wrong Email and Password", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }

    private void sendUesrtonextactivity() {
        Intent intent = new Intent(RegisterActivity.this,MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}