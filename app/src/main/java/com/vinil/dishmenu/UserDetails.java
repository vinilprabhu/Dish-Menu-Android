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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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




            database.child("users").child(mUserId).child("Details").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

//                    User user = dataSnapshot.getValue(User.class);

                    String FirstNameS =(String)dataSnapshot.child("firstname").getValue();
                    String SurnameS =(String)dataSnapshot.child("surname").getValue();
                    String CountryS =(String)dataSnapshot.child("country").getValue();
                    String DOBS =(String)dataSnapshot.child("dob").getValue();
                    String PhoneS =(String)dataSnapshot.child("phone").getValue();


                    FirstName.setText(FirstNameS);
                    Surname.setText(SurnameS);
                    Country.setText(CountryS);
                    DOB.setText(DOBS);
                    Phone.setText(PhoneS);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });






            buttonSubmitDetails.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    if(FirstName.getText().toString().length()==0)FirstName.setError("Name cannot be blank");
                    else if(FirstName.getText().toString().length()<3)FirstName.setError("Name cannot be less than 3 characters");

                    else if(Surname.getText().toString().length()==0)Surname.setError("Surame cannot be blank");
                    else if(Surname.getText().toString().length()<2)Surname.setError("Surame cannot be less than 2 characters");

                    else if(Country.getText().toString().length()==0)Country.setError("Country name cannot be blank");
                    else if(Country.getText().toString().length()<4)Country.setError("Country name cannot be less than 4 characters");

                    else if(!isValidDate(DOB.getText().toString()))DOB.setError("Invalid Date format");

                    else if(!isValidPhone(Phone.getText().toString()))Phone.setError("Invalid Phone no");

                    else {
                        database.child("users").child(mUserId).child("Details").child("firstname").setValue(FirstName.getText().toString());
                        database.child("users").child(mUserId).child("Details").child("surname").setValue(Surname.getText().toString());
                        database.child("users").child(mUserId).child("Details").child("country").setValue(Country.getText().toString());
                        database.child("users").child(mUserId).child("Details").child("dob").setValue(DOB.getText().toString());
                        database.child("users").child(mUserId).child("Details").child("phone").setValue(Phone.getText().toString());

                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }

                }

            });

        }
    }

    // validating date
    private boolean isValidDate(String date) {
        String DATE_PATTERN =  "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    // validating phone no
    private boolean isValidPhone(String phoneno) {
        String DATE_PATTERN =  "([7-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])";
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(phoneno);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Home.class));
    }
}
