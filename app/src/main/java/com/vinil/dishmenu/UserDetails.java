package com.vinil.dishmenu;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetails extends AppCompatActivity {


    private TextView tvEmail,tvUID,tvName;


    private DatabaseReference database;
    private FirebaseUser user;

    private String mUserId;


    private static final String TAG="UserDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        tvName=(TextView)findViewById(R.id.textViewName);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        if (user == null) {
            // Not logged in, launch the Log In activity

        } else {
            mUserId = user.getUid();


            // Add items via the Button and EditText at the bottom of the view.
            final EditText FirstName = (EditText) findViewById(R.id.editTextFirstName);
            final EditText Surname = (EditText) findViewById(R.id.editTextSurname);
            final EditText Country = (EditText) findViewById(R.id.editTextCountry);
            final EditText DOB = (EditText) findViewById(R.id.editTextDOB);
            final EditText Phone = (EditText) findViewById(R.id.editTextPhone);
            final Button buttonSubmitDetails = (Button) findViewById(R.id.buttonsubmitDetails);
            buttonSubmitDetails.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    database.child("users").child(mUserId).child("Details").child("firstname").setValue(FirstName.getText().toString());
                    database.child("users").child(mUserId).child("Details").child("firstname").setValue(FirstName.getText().toString());
                    database.child("users").child(mUserId).child("Details").child("surname").setValue(Surname.getText().toString());
                    database.child("users").child(mUserId).child("Details").child("country").setValue(Country.getText().toString());
                    database.child("users").child(mUserId).child("Details").child("dob").setValue(DOB.getText().toString());
                    database.child("users").child(mUserId).child("Details").child("phone").setValue(Phone.getText().toString());

                    startActivity(new Intent(getApplicationContext(), Login.class));

                }

            });







        }
    }
}
