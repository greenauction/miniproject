package com.example.shravanram.greenauction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private ImageButton createPortal;
    private ImageButton viewPortal;


    private Button buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_p);

        buttonLogout = (Button) findViewById(R.id.ButtonLogout);
        textViewUserEmail=(TextView) findViewById(R.id.userText);
        buttonLogout.setOnClickListener(this);
      //  firebaseAuth=FirebaseAuth.getInstance();
        createPortal=(ImageButton) findViewById(R.id.createPortal);
        viewPortal=(ImageButton) findViewById(R.id.viewPortal);
        createPortal.setOnClickListener(this);
        viewPortal.setOnClickListener(this);
       /* if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));


        }*/
       //FirebaseUser user=firebaseAuth.getCurrentUser();
       //textViewUserEmail.setText("Welcome user" +user.getEmail());

    }

    @Override
    public void onClick(View view) {
        if(view==createPortal)
        {
            finish();
            startActivity(new Intent(this,PortalCreation.class));

        }
        if(view==viewPortal)
        {
            finish();
           startActivity(new Intent(this,portalView.class));
        }
        if(view==buttonLogout) {
            finish();
            firebaseAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
