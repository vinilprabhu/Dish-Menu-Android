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

    private TextView tvEmail,tvUID,tvName;
    private Button bLogout,buttonup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){

            finish();

            startActivity(new Intent(this, Login.class));
        }else{
            FirebaseUser user=firebaseAuth.getCurrentUser();

            tvEmail=(TextView)findViewById(R.id.textViewEmail);
            tvUID=(TextView)findViewById(R.id.textViewUID);
            tvName=(TextView)findViewById(R.id.textViewName);
            bLogout=(Button)findViewById(R.id.buttonLogout);
            buttonup=(Button)findViewById(R.id.buttonup);

            tvEmail.setText(user.getEmail());
            String Uid=user.getUid();
            tvUID.setText(Uid);
            String name = user.getDisplayName();
            tvName.setText(name);

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
