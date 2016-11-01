package com.vinil.dishmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextView tvEmail;
    private Button bLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){

            finish();

            startActivity(new Intent(this, Login.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();

        tvEmail=(TextView)findViewById(R.id.textViewEmail);
        bLogout=(Button)findViewById(R.id.buttonLogout);

        tvEmail.setText(user.getEmail());

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();

                finish();

                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });



    }
}
