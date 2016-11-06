package com.vinil.dishmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;

    private TextView tvEmail,tvFirstName,tvSurname,tvCountry,tvDOB,tvPhone;
    private Button bLogout,buttonup;

    private static final String TAG="Home";
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        database = FirebaseDatabase.getInstance().getReference();

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){

            finish();

            startActivity(new Intent(this, Login.class));
        }else{
            FirebaseUser user=firebaseAuth.getCurrentUser();

            tvEmail=(TextView)findViewById(R.id.textViewEmail);
            tvFirstName=(TextView)findViewById(R.id.textViewFirstName);
            tvSurname=(TextView)findViewById(R.id.textViewSurname);
            tvCountry=(TextView)findViewById(R.id.textViewCountry);
            tvDOB=(TextView)findViewById(R.id.textViewDOB);
            tvPhone=(TextView)findViewById(R.id.textViewPhone);
            bLogout=(Button)findViewById(R.id.buttonLogout);
            buttonup=(Button)findViewById(R.id.buttonup);

            tvEmail.setText(user.getEmail());

            mUserId = user.getUid();

            database.child("users").child(mUserId).child("Details").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

//                    User user = dataSnapshot.getValue(User.class);


                    String FirstNameS =(String)dataSnapshot.child("firstname").getValue();

                    if(dataSnapshot.child("firstname").getValue()==null){
                        Toast.makeText(Home.this, "Fill all details to continue!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), UserDetails.class));
                    }

                    String SurnameS =(String)dataSnapshot.child("surname").getValue();

                    if(dataSnapshot.child("surname").getValue()==null){
                        Toast.makeText(Home.this, "Fill all details to continue!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), UserDetails.class));
                    }

                    String CountryS =(String)dataSnapshot.child("country").getValue();

                    if(dataSnapshot.child("country").getValue()==null){
                        Toast.makeText(Home.this, "Fill all details to continue!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), UserDetails.class));
                    }

                    String DOBS =(String)dataSnapshot.child("dob").getValue();

                    if(dataSnapshot.child("dob").getValue()==null){
                        Toast.makeText(Home.this, "Fill all details to continue!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), UserDetails.class));
                    }

                    String PhoneS =(String)dataSnapshot.child("phone").getValue();

                    if(dataSnapshot.child("phone").getValue()==null){
                        Toast.makeText(Home.this, "Fill all details to continue!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), UserDetails.class));
                    }


                    tvFirstName.setText(FirstNameS);
                    tvSurname.setText(SurnameS);
                    tvCountry.setText(CountryS);
                    tvDOB.setText(DOBS);
                    tvPhone.setText(PhoneS);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });



            bLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    firebaseAuth.signOut();

                    finish();

                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });

            buttonup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    startActivity(new Intent(getApplicationContext(), UserDetails.class));
                }
            });
        }





    }
}
