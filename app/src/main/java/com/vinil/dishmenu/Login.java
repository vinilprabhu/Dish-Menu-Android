package com.vinil.dishmenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {


    private Button buttonSignin,buttonSignup;
    private EditText editTextEmail,editTextPassword;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth =FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){

            finish();

            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);

        buttonSignin=(Button)findViewById(R.id.buttonSigninl);
        buttonSignup=(Button)findViewById(R.id.buttonSignupl);

        progressDialog = new ProgressDialog(this);

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String errorMsg = "Invalid Email",errorMsg2="Invalid Password";
                final String email = editTextEmail.getText().toString();
                final String pass = editTextPassword.getText().toString();

//                //validation
//                if (!isValidEmail(email))editTextEmail.setError(errorMsg);
//
//                else if(!checkEmailAvailability(email))editTextEmail.setError("Email not registered!\nSignUp now");
//
//                else if (!isValidPassword(pass))editTextPassword.setError(errorMsg2);
//
//                else userLogin();


                if(email.length()==0)
                    editTextEmail.setError("Email ID cannot be empty");
                else userLogin();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        if(password.length()==0)
            password="a";

        progressDialog.setMessage("Signing in Please Wait...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();

                if(task.isSuccessful()){

                    finish();

                    startActivity(new Intent(getApplicationContext(), Home.class));
                }else {
                    Toast.makeText(Login.this, "Registration Error", Toast.LENGTH_LONG).show();
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        // thrown if there already exists an account with the given email address
                        editTextEmail.setError("Email ID not registered!\nSignUP now");
                    } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                        // thrown if the password is not strong enough
                        editTextPassword.setError("password is not strong enough!");
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // thrown if the email address is malformed
                        editTextPassword.setError("password incorrect!");
                    }
                }
            }
        });

    }

    // validating email
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    int a =1;

    private boolean checkEmailAvailability(String email){
        firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if(task.isSuccessful()){
                    ///////// getProviders() will return size 1. if email ID is available.
                    if(task.getResult().getProviders().toString().length()!=1)a=0;
                }
            }
        });

        if(a==0)return false;
        else return true;
    }


}


